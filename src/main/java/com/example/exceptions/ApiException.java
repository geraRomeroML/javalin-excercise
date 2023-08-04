package com.example.exceptions;

import com.mercadolibre.json.JsonUtils;
import org.apache.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiException extends Exception{

    private static final long serialVersionUID = 1L;

    private String code;
    private String description;
    private Integer statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public ApiException(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public ApiException(final String code, final String description, final Integer statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public ApiException(final String code, final String description, final Integer statusCode, final Throwable cause) {
        super(cause);
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public ApiException(final String code, final String description, final Throwable cause) {
        super(cause);
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String toJson() {
        final Map<String, Object> exceptionMap = new LinkedHashMap<>();

        exceptionMap.put("error", code);
        exceptionMap.put("message", description);
        exceptionMap.put("status", statusCode);

        try {
            return JsonUtils.INSTANCE.toJsonString(exceptionMap);
        } catch (final Exception exception) {
            return "{" +
                    "\"error\": " +
                    "\"" + code + "\", " +
                    "\"message\": " +
                    "\"" + description + "\", " +
                    "\"status\": " +
                    statusCode +
                    "}";
        }
    }
}
