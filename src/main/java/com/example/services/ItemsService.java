package com.example.services;

import com.example.exceptions.ApiException;
import com.example.rest.apiItems.AdItemDTO;
import com.example.rest.apiItems.ApiItemsRestService;
import com.google.common.collect.Lists;
import com.mercadolibre.restclient.exception.ParseException;
import com.mercadolibre.threading.executor.MeliExecutors;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ItemsService {
    @Inject
    private ApiItemsRestService apiItemsRestService;

    public AdItemDTO getItem(String itemId) throws ParseException, ApiException {

        return apiItemsRestService.getItemById(itemId)
                .orElse(null);
    }

    public List<AdItemDTO> getItems(String[] itemIds) throws ExecutionException, InterruptedException, TimeoutException {
        final ExecutorService executor = MeliExecutors.newFixedThreadPool(5);

        List<CompletableFuture<AdItemDTO>> futures = new ArrayList<>();

        for (String itemId : itemIds) {
            CompletableFuture<AdItemDTO> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return getItem(itemId);
                } catch (ParseException | ApiException e) {
                    log.error(e.getMessage());
                    return null;
                }
            });
            futures.add(future);
        }

        List<AdItemDTO> results = futures.stream()
                .map(f -> {
                    try {
                        return f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.error(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return  results;
    }
}
