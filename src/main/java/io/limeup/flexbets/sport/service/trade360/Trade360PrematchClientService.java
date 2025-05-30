package io.limeup.flexbets.sport.service.trade360;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.config.FlexBetsSportConfiguration;
import io.limeup.flexbets.sport.dto.trade360.Trade360ApiRequestBodyParams;
import io.limeup.flexbets.sport.dto.trade360.Trade360SnapshotResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class Trade360PrematchClientService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FlexBetsSportConfiguration flexBetsSportConfiguration;

    private final String BASE_URL = "https://stm-snapshot.lsports.eu/PreMatch";

    public Trade360PrematchClientService(RestTemplate restTemplate, ObjectMapper objectMapper, FlexBetsSportConfiguration flexBetsSportConfiguration) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.flexBetsSportConfiguration = flexBetsSportConfiguration;
    }

    public List<Trade360SnapshotResponseDTO> getEventList(Trade360ApiRequestBodyParams requestBodyParams){
        String jsonResponse = post("/GetEvents", requestBodyParams);
        List<Trade360SnapshotResponseDTO> data = null;
        try {
            data = dataWrapper(jsonResponse);
        } catch (IOException ex) {
            log.error("Error in data parsing from Trade360 response API : {}", ex.getMessage());
        }
        return data;
    }

    private String post(String endpoint, Trade360ApiRequestBodyParams requestBody) {
        String url = BASE_URL + endpoint;
        requestBody.setUserName(flexBetsSportConfiguration.getTrade360Username());
        requestBody.setPassword(flexBetsSportConfiguration.getTrade360Password());
        requestBody.setPackageId(Integer.valueOf(flexBetsSportConfiguration.getTrade360PrematchPackageId()));
        return restTemplate.postForObject(url, requestBody, String.class);
    }

    private List<Trade360SnapshotResponseDTO> dataWrapper(String responseJson) throws IOException {
        JsonNode root = objectMapper.readTree(responseJson);
        JsonNode body = root.path("Body");
        return objectMapper.readerForListOf(Trade360SnapshotResponseDTO.class).readValue(body);
    }
}
