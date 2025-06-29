package com.algaworks.algasensors.device.management.api.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class RestClientFactory {

    private final RestClient.Builder builder;

    public RestClientFactory(RestClient.Builder builder) {
        this.builder = builder;
    }

    public RestClient temperatureMonitoringRestClient() {

        return builder
                .baseUrl("http://localhost:8082")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, ((request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                }))
                .build();

    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(5));

        return factory;

    }


}
