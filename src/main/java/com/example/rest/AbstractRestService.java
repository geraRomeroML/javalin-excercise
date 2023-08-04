package com.example.rest;

import com.mercadolibre.restclient.MeliRestClient;
import com.mercadolibre.restclient.RESTPool;
import com.mercadolibre.restclient.retry.SimpleRetryStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
abstract public class AbstractRestService {

    protected final MeliRestClient restClient;

    protected AbstractRestService() throws IOException {
        try {
            final RESTPool pool = RESTPool.builder()
                    .withName(getResourceName())
                    .withSocketTimeout(20000)
                    .withConnectionTimeout(20000)
                    .withMaxTotal(50)
                    .withMaxPerRoute(50)
                    .withMaxPoolWait(100)
                    .withRetryStrategy(new SimpleRetryStrategy(3, 10))
//                    .withBaseURL("https://internal-api.mercadolibre.com/")
                    .build();

            restClient = MeliRestClient.builder()
                    .withPool(pool)
                    .disableDefault()
                    .build();
        } catch (final IOException e) {
            log.error("Unexpected exception building rest client pool", e);
            throw e;
        }
    }

    protected abstract String getResourceName();
}
