package com.algaworks.algasensors.device.management.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SensorUpdateInput {
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @NotBlank(message = "IP é obrigatório")
    private String ip;
    @NotBlank(message = "Localização é obrigatória")
    private String location;
    @NotBlank(message = "Protocolo é obrigatório")
    private String protocol;
    @NotBlank(message = "Modelo é obrigatório")
    private String model;
}
