package com.example.rest;

import com.mercadolibre.restclient.RestClientTestBase;
import com.mercadolibre.restclient.util.MeliContextBuilder;
import com.mercadolibre.routing.RoutingHelper;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class MeliContextAwareRestClientTestBase extends RestClientTestBase {

    @BeforeEach
    void beforeEach() {
        Map<String,String> headers = new HashMap<>();
        headers.put("X-Request-Id","2610cf36-c2d8-4de0-a5e9-e0dc25fee587");
        RoutingHelper.setMeliContext(MeliContextBuilder.build(headers));
    }
}
