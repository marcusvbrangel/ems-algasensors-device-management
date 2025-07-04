package com.algaworks.algasensors.device.management.api.client.impl;

import com.algaworks.algasensors.device.management.api.client.RestClientFactory;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.dto.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

//@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    // NOTA: Esta classe nao sera mais utilizada...
    // foi substituida pela RestClientConfig e SensorMonitoringClient...

    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClientFactory restClientFactory) {

        this.restClient = restClientFactory.temperatureMonitoringRestClient();

    }

    @Override
    public void enableMonitoring(TSID sensorId) {

        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public void disableMonitoring(TSID sensorId) {

        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public SensorMonitoringOutput getDetail(TSID sensorId) {

        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringOutput.class);

    }

}













