package com.algaworks.algasensors.device.management;

import io.hypersistence.tsid.TSID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class TSIDTest {

    @Test
    void shouldGenerateTSID() {

        TSID tsid = IdGenerator.generateTSID();

//        TSID tsid = TSID.fast();
//        TSID tsid = TSID.Factory.getTsid();

        System.out.println(tsid);
        System.out.println(tsid.toLong());
        System.out.println(tsid.getInstant());

        Assertions.assertThat(tsid.getInstant())
                .isCloseTo(Instant.now(), Assertions.within(1, ChronoUnit.MINUTES));

    }

}
