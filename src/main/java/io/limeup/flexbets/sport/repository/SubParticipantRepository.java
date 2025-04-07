package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.SubParticipant;

import java.util.List;

public interface SubParticipantRepository extends ExternalIdRepository<SubParticipant, Long> {

    List<SubParticipant> findByParticipantId(Long participantId);

}
