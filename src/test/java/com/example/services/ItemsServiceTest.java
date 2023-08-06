package com.example.services;

import com.example.extensions.mockito.MockitoTest;
import com.example.rest.apiItems.AdItemDTO;
import com.example.rest.apiItems.AdItemDTOFixture;
import com.example.rest.apiItems.ApiItemsRestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@MockitoTest
class ItemsServiceTest {
    @Mock
    private ApiItemsRestService apiItemsRestService;
    @InjectMocks
    private ItemsService itemsService;
    private final String testItemId = "MLA12345";

    @Test
    public void whenGetAnExistingItemIdThenAnAdItemDTOShouldBeReturned() throws Exception {
        // given
        AdItemDTO adItemDTOExpected = new AdItemDTOFixture().withId(testItemId).build();
        Optional<AdItemDTO> optionalAdItem = Optional.of(adItemDTOExpected);
        when(apiItemsRestService.getItemById(eq(testItemId))).thenReturn(optionalAdItem);

        // when
        AdItemDTO adItemDTOResult = itemsService.getItem(testItemId);

        // then
        assertNotNull(adItemDTOResult);
        assertEquals(adItemDTOExpected.getId(), adItemDTOResult.getId());
    }

    @Test
    public void whenGetAnNotExistingItemIdThenANullObjectShouldBeReturned() throws Exception {
        // given
        Optional<AdItemDTO> optionalAdItem = Optional.empty();
        when(apiItemsRestService.getItemById(eq(testItemId))).thenReturn(optionalAdItem);

        // when
        AdItemDTO adItemDTOResult = itemsService.getItem(testItemId);

        // then
        assertNull(adItemDTOResult);
    }

}