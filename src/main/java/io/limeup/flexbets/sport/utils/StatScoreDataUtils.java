package io.limeup.flexbets.sport.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatScoreDataUtils {

    private StatScoreDataUtils() {

    }

    public static <D, E> void mergeAndSaveDTOs(
            List<D> fetchedDTOs,
            Function<D, Integer> dtoExternalIdExtractor,
            Function<Collection<Integer>, List<E>> findExistingByIds,
            BiFunction<D, E, E> updateMapper,
            Function<D, E> createMapper,
            Function<E, Integer> entityExternalIdExtractor,
            Consumer<List<E>> saveAll
    ) {
        if (fetchedDTOs.isEmpty()) return;

        Map<Integer, E> existingMap = findExistingByIds.apply(
                fetchedDTOs.stream()
                        .map(dtoExternalIdExtractor)
                        .collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(
                entityExternalIdExtractor,
                Function.identity()
        ));

        List<E> toSave = new ArrayList<>();

        for (D dto : fetchedDTOs) {
            Integer id = dtoExternalIdExtractor.apply(dto);
            E existing = existingMap.get(id);

            if (existing != null) {
                toSave.add(updateMapper.apply(dto, existing));
            } else {
                E newEntity = createMapper.apply(dto);
                toSave.add(newEntity);
            }
        }

        saveAll.accept(toSave);
    }

}
