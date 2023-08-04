package com.example.rest.apiItems;

import com.example.exceptions.ApiException;
import com.example.rest.AbstractRestService;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.exception.ParseException;
import com.mercadolibre.restclient.exception.RestException;
import com.mercadolibre.restclient.util.MeliContext;
import com.mercadolibre.restclient.util.MeliContextBuilder;
import com.mercadolibre.routing.RoutingHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ApiItemsRestService extends AbstractRestService {

    ApiItemsRestService() throws IOException {
        super();
    }

    @Override
    protected String getResourceName() {
        return "advertising";
    }

    public Optional<AdItemDTO> getItemById(String itemId) throws ParseException, ApiException {

        final MeliContext meliContext = MeliContextBuilder.buildFlowStarterContext();
        RoutingHelper.setMeliContext(meliContext);

        try {
            final Response response = getItemResponse(itemId, meliContext);

            return switch (response.getStatus()) {
                case HttpStatus.SC_OK -> Optional.of(response.getData(AdItemDTO.class));
                case HttpStatus.SC_NOT_FOUND -> Optional.empty();
                default -> throw
                        new ApiException("invalid_status_code", String.format("Invalid status code for [itemId:%s]", itemId), response.getStatus());
            };
        } catch (RestException e) {
            log.error("[ApiItemsRestService.getItemById] An api call exception was thrown for [itemId:{}], e:{}", itemId, e.getCause().toString());

            throw new ApiException(String.format("An exception occurred trying at get items [itemId:%s]", itemId), e.getCause().toString());
        }
    }

    private Response getItemResponse(String itemId, MeliContext meliContext) throws RestException {
        String endpoint = "items";
        String finalUrl = String.join("/", "https://internal-api.mercadolibre.com", endpoint, itemId);

        return restClient
                .withPool(getResourceName())
                .withHeader("X-Caller-Scope", "admin")
                .get(finalUrl, meliContext);
    }
}
