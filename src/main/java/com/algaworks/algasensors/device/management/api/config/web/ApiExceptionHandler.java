package com.algaworks.algasensors.device.management.api.config.web;

import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBadGatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

//    {
//        "timestamp": "2025-06-29T21:33:58.622+00:00",
//        "status": 500,
//        "error": "Internal Server Error",
//        "path": "/api/sensors/0M33RJP70AGZZ/enable"
//    }

    @ExceptionHandler({
            SocketTimeoutException.class,
            ConnectException.class,
            ClosedChannelException.class,
    })
    public ProblemDetail problemDetail(IOException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problemDetail.setTitle("Gateway Timeout");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;

    }

    @ExceptionHandler(SensorMonitoringClientBadGatewayException.class)
    public ProblemDetail problemDetail(SensorMonitoringClientBadGatewayException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setTitle("Bad Gateway");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;

    }

}
