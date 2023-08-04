package com.example.router;

import com.example.DependencyInjectionModule;
import com.example.controllers.ItemController;
import com.google.common.base.Stopwatch;
import com.google.inject.Injector;
import io.javalin.apibuilder.EndpointGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static io.javalin.apibuilder.ApiBuilder.get;

@Slf4j
public class Router implements EndpointGroup {
    private ItemController itemController;

    @Override
    public void addEndpoints() {
        Injector injector = DependencyInjectionModule.getInstance();
        Stopwatch stopwatch = Stopwatch.createStarted();

        itemController = injector.getInstance(ItemController.class);

        get("/items/{itemId}", itemController::getItem);
        get("/advertising/product_ads/ads", itemController::getItems);

        long timeTakenToExecuteRouterInit = stopwatch.elapsed(TimeUnit.SECONDS);
        log.info("[init] Time taken to execute Router.init method: {}s", Optional.of(timeTakenToExecuteRouterInit));

    }
}
