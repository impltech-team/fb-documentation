package io.limeup.flexbets.sport.service.trade360;

import io.limeup.flexbets.sport.config.FlexBetsSportConfiguration;
import io.limeup.flexbets.sport.dto.trade360.Trade360ApiRequestBodyParams;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Trade360PrematchClientService {

    private final RestTemplate restTemplate;
    private final FlexBetsSportConfiguration flexBetsSportConfiguration;

    private final String BASE_URL = "https://stm-snapshot.lsports.eu/PreMatch";

    public Trade360PrematchClientService(RestTemplate restTemplate, FlexBetsSportConfiguration flexBetsSportConfiguration) {
        this.restTemplate = restTemplate;
        this.flexBetsSportConfiguration = flexBetsSportConfiguration;
    }

    public String post(String endpoint, Trade360ApiRequestBodyParams requestBody) {
        String url = BASE_URL + endpoint;
        requestBody.setUserName(flexBetsSportConfiguration.getTrade360Username());
        requestBody.setPassword(flexBetsSportConfiguration.getTrade360Password());
        requestBody.setPackageId(Integer.valueOf(flexBetsSportConfiguration.getTrade360PrematchPackageId()));
        return restTemplate.postForObject(url, requestBody, String.class);
    }
}
