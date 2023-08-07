package com.example.controllers;

import com.example.exceptions.ApiException;
import com.example.extensions.mockito.MockitoTest;
import com.example.rest.apiItems.AdItemDTO;
import com.example.rest.apiItems.AdItemDTOFixture;
import com.example.services.ItemsService;
import com.mercadolibre.restclient.exception.ParseException;
import io.javalin.http.Context;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoTest
public class ItemControllerTest {
    @Mock
    private Context ctx;
    @Mock
    private ItemsService itemsService;
    @InjectMocks
    private ItemController itemController;

    private final String testItemId = "MLA123";

    @Test
    public void givenGetItemWhenItemIdExistsThenReturnAdItemDTO() throws Exception {
        // given
        AdItemDTO adItemDTO = new AdItemDTO();

        when(ctx.pathParam("itemId")).thenReturn(testItemId);
        when(itemsService.getItem(testItemId)).thenReturn(adItemDTO);

        // when
        Object result = itemController.getItem(ctx);

        // then
        assertNotNull(result);
        verify(ctx).status(HttpStatus.SC_OK);
//        assertEquals(HttpStatus.SC_OK, ctx.status());
        verify(ctx).json(adItemDTO);
    }

    @Test
    public void givenGetItemWhenItemIdNotExistsThenReturnNotFound() throws ParseException, ApiException {
        // given
        when(ctx.pathParam("itemId")).thenReturn(anyString());
        when(itemsService.getItem(anyString())).thenReturn(null);

        // when
        itemController.getItem(ctx);

        // then
        assertEquals(HttpStatus.SC_NOT_FOUND, ctx.status());
        assertEquals( String.format("Item with id: %s not found", testItemId), ctx.body());
    }

    @Test
    public void givenGetItemsWhenItemsExistsThenReturnAdItemDTOList() throws ParseException, ApiException, ExecutionException, InterruptedException, TimeoutException {
        // given
        final String testItemId1 = "MLA123";
        final String testItemId2 = "MLA456";

        String testItemIdsParam = String.join("", testItemId1, testItemId2);
        String[] testItemIds = { testItemId1, testItemId2 };

        AdItemDTO adItemDTO1 = new AdItemDTOFixture().withId("MLA123").build();
        AdItemDTO adItemDTO2 = new AdItemDTOFixture().withId("MLA456").build();

        List<AdItemDTO> adItemDTOS = new ArrayList<>();
        adItemDTOS.add(adItemDTO1);
        adItemDTOS.add(adItemDTO2);

        when(ctx.pathParam("item_ids")).thenReturn(testItemIdsParam);
        when(itemsService.getItems(testItemIds)).thenReturn(adItemDTOS);

        // when
        itemController.getItems(ctx);

        // then
        assertEquals(HttpStatus.SC_OK, ctx.status());
        verify(ctx).json(adItemDTOS);
    }
}
