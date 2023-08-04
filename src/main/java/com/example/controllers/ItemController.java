package com.example.controllers;

import com.example.exceptions.ApiException;
import com.example.rest.apiItems.AdItemDTO;
import com.example.services.ItemsService;
import com.mercadolibre.restclient.exception.ParseException;
import io.javalin.http.Context;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ItemController extends AbstractController{
    @Inject
    private ItemsService itemsService;

    public Object getItem(Context context) throws ParseException, ApiException {
        String itemId = context.pathParam("itemId");

        AdItemDTO adItemDTO = itemsService.getItem(itemId);

        if (Objects.nonNull(adItemDTO)) {
            context.status(HttpStatus.SC_OK);
            context.json(adItemDTO);

            return context;
        }

        context.result(String.format("Item with id: %s not found", itemId));
        context.status(HttpStatus.SC_NOT_FOUND);

        return context;
    }

    public Object getItems(Context context) throws ParseException, ApiException, ExecutionException, InterruptedException, TimeoutException {
        String[] itemIds = Objects.requireNonNull(context.queryParam("item_ids")).split(",");

        List<AdItemDTO> adItemDTOS = itemsService.getItems(itemIds);

        if (Objects.nonNull(adItemDTOS)) {
            context.status(HttpStatus.SC_OK);
            context.json(adItemDTOS);

            return context;
        }

        context.status(HttpStatus.SC_NOT_FOUND);

        return context;
    }
}
