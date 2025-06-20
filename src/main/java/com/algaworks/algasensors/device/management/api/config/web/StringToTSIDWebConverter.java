package com.algaworks.algasensors.device.management.api.config.web;

import io.hypersistence.tsid.TSID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTSIDWebConverter implements Converter<String, TSID> {

    public TSID convert(String source) {
        return TSID.from(source);
    }

}
