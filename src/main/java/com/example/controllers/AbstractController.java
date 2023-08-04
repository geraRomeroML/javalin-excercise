package com.example.controllers;

import com.google.common.net.MediaType;
import com.mercadolibre.json.JsonUtils;
import com.mercadolibre.json.exception.JsonException;
import io.javalin.http.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractController {

    /**
     * @param status HTTP Status Code
     * @param result Object to be parsed as a JSON Response
     * @return JSON parsed Object with specified status code
     */
    @SuppressWarnings("UnstableApiUsage")
    String respond(Integer status, Object result, Context context) throws JsonException {
        log.debug("Processing url {}", context.url());
        context.status(status);
        context.header("Content-Type", MediaType.JSON_UTF_8.toString());
        return JsonUtils.INSTANCE.toJsonString(result);
    }

    /**
     * @param status HTTP Status Code
     * @return Response with Status Code but without body
     */
    @SuppressWarnings("UnstableApiUsage")
    String respond(Integer status, Context context) {
        log.debug("Processing url {}", context.url());
        context.status(status);
        context.header("Content-Type", MediaType.JSON_UTF_8.toString());
        return "";
    }
}
