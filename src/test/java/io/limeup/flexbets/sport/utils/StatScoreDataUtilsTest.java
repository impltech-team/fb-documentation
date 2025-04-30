package io.limeup.flexbets.sport.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;

class StatScoreDataUtilsTest {

    @Test
    void mergeAndSaveDTOsShouldUpdateExistingAndCreateNewEntities() {
        DummyDTO dto1 = new DummyDTO(1, "NewName1");
        DummyDTO dto2 = new DummyDTO(2, "UpdatedName2");
        DummyDTO dto3 = new DummyDTO(3, "NewName3");
        List<DummyDTO> fetchedDTOs = List.of(dto1, dto2, dto3);

        DummyEntity existingEntity2 = new DummyEntity(2, "OldName2");

        Function<DummyDTO, Integer> dtoExternalIdExtractor = DummyDTO::getId;
        Function<Collection<Integer>, List<DummyEntity>> findExistingByIds = ids -> List.of(existingEntity2);
        BiFunction<DummyDTO, DummyEntity, DummyEntity> updateMapper = (dto, entity) -> {
            entity.setName(dto.getName());
            return entity;
        };
        Function<DummyDTO, DummyEntity> createMapper = dto -> new DummyEntity(dto.getId(), dto.getName());
        Function<DummyEntity, Integer> entityExternalIdExtractor = DummyEntity::getId;
        List<DummyEntity> savedEntities = new ArrayList<>();
        Consumer<List<DummyEntity>> saveAll = savedEntities::addAll;

        StatScoreDataUtils.mergeAndSaveDTOs(
                fetchedDTOs,
                dtoExternalIdExtractor,
                findExistingByIds,
                updateMapper,
                createMapper,
                entityExternalIdExtractor,
                saveAll
        );

        assertThat(savedEntities).hasSize(3);

        assertThat(savedEntities).extracting(DummyEntity::getId)
                .containsExactlyInAnyOrder(1, 2, 3);

        assertThat(savedEntities).extracting(DummyEntity::getName)
                .containsExactlyInAnyOrder("NewName1", "UpdatedName2", "NewName3");
    }

    @Test
    void mergeAndSaveDTOsShouldDoNothingWhenEmptyFetchedList() {
        List<DummyDTO> fetchedDTOs = Collections.emptyList();
        Consumer<List<DummyEntity>> saveAll = mock(Consumer.class);

        StatScoreDataUtils.mergeAndSaveDTOs(
                fetchedDTOs,
                DummyDTO::getId,
                ids -> List.of(),
                (dto, entity) -> entity,
                dto -> new DummyEntity(dto.getId(), dto.getName()),
                DummyEntity::getId,
                saveAll
        );

        verify(saveAll, never()).accept(anyList());
    }

    static class DummyDTO {
        private final Integer id;
        private final String name;

        DummyDTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        Integer getId() {
            return id;
        }

        String getName() {
            return name;
        }
    }

    static class DummyEntity {
        private Integer id;
        private String name;

        DummyEntity(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        Integer getId() {
            return id;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }
    }
}
