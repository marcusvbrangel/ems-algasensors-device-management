package com.algaworks.algasensors.device.management.api.controller;

import com.algaworks.algasensors.device.management.api.dto.SensorInput;
import com.algaworks.algasensors.device.management.api.dto.SensorOutput;
import com.algaworks.algasensors.device.management.api.dto.SensorUpdateInput;
import com.algaworks.algasensors.device.management.common.IdGenerator;
import com.algaworks.algasensors.device.management.domain.model.Sensor;
import com.algaworks.algasensors.device.management.domain.model.SensorId;
import com.algaworks.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;

    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput create(@RequestBody final SensorInput input) {

        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();

        sensor = sensorRepository.saveAndFlush(sensor);

        return convertToSensorOutput(sensor);
    }

    @GetMapping("/{sensorId}")
    public SensorOutput getOne(@PathVariable TSID sensorId) {

        Sensor sensor = getSensorOrThrowNotFound(sensorId);

        return convertToSensorOutput(sensor);

    }

    @GetMapping
    public Page<SensorOutput> search(@PageableDefault Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(sensor -> convertToSensorOutput(sensor));
    }

    @PutMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput update(@PathVariable TSID sensorId, @RequestBody SensorUpdateInput input) {

        Sensor sensorUpdate = getSensorOrThrowNotFound(sensorId);

        sensorUpdate.setName(input.getName());
        sensorUpdate.setIp(input.getIp());
        sensorUpdate.setLocation(input.getLocation());
        sensorUpdate.setProtocol(input.getProtocol());
        sensorUpdate.setModel(input.getModel());

        sensorUpdate = sensorRepository.saveAndFlush(sensorUpdate);

        return convertToSensorOutput(sensorUpdate);

    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {

        sensorRepository.findById(new SensorId(sensorId))
                .ifPresentOrElse(
                        sensorRepository::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );

    }

    private Sensor getSensorOrThrowNotFound(TSID sensorId) {
        return sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SensorOutput convertToSensorOutput(final Sensor sensor) {
        return SensorOutput.builder()
                .id(sensor.getId().getValue())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }

}