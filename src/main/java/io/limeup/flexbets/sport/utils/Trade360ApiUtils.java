package io.limeup.flexbets.sport.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.trade360.Trade360SnapshotResponseDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Trade360ApiUtils {

    private final ObjectMapper objectMapper;

    public List<Trade360SnapshotResponseDTO> getDataFromTrade360SnapshotResponseJson(String responseJson) throws IOException {
        JsonNode root = objectMapper.readTree(responseJson);
        JsonNode body = root.path("Body");
        return objectMapper.readerForListOf(Trade360SnapshotResponseDTO.class).readValue(body);
    }
}
