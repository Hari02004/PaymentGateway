package org.JamesWeb.DTO;

import lombok.Data;

@Data
public class ErrorDto {
    int status;
    String errorCode;
    String message;
    String path;
    String serviceName;
    String traceId;
}
