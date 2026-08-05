package org.JamesWeb.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.JamesWeb.DTO.ErrorDto;
import org.springframework.stereotype.Component;

@Component
public class ErrorMapper {

    public ErrorDto toDto(
            int status,
            String errorCode,
            String message,
            String serviceName,
            HttpServletRequest request) {

        ErrorDto dto = new ErrorDto();
        dto.setStatus(status);
        dto.setErrorCode(errorCode);
        dto.setMessage(message);
        dto.setPath(request.getRequestURI());
        dto.setServiceName(serviceName);
        dto.setTraceId((String) request.getAttribute("traceId"));

        return dto;
    }
}
