package com.algaworks.algasensors.device.management.api.dto;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorOutput {
    private TSID id;
    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
    private Boolean enabled;
}