package com.example.services;

import com.example.extensions.mockito.MockitoTest;
import com.example.rest.apiItems.AdItemDTO;
import com.example.rest.apiItems.AdItemDTOFixture;
import com.example.rest.apiItems.ApiItemsRestService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
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

    @Test
    public void whenGetMultipleExistingItemIdsThenAListOfAdItemDTOShouldBeReturned() throws Exception {
        // given
        final String testItemId1 = "MLA123";
        final String testItemId2 = "MLA456";
        final String testItemId3 = "MLA789";
        String[] testItemIds = { testItemId1, testItemId2, testItemId3};

        Optional<AdItemDTO> adItemDTO1 = Optional.of(new AdItemDTOFixture().withId("MLA123").build());
        Optional<AdItemDTO> adItemDTO2 = Optional.of(new AdItemDTOFixture().withId("MLA456").build());
        Optional<AdItemDTO> adItemDTO3 = Optional.of(new AdItemDTOFixture().withId("MLA789").build());

        when(apiItemsRestService.getItemById(testItemId1)).thenReturn(adItemDTO1);
        when(apiItemsRestService.getItemById(testItemId2)).thenReturn(adItemDTO2);
        when(apiItemsRestService.getItemById(testItemId3)).thenReturn(adItemDTO3);

        // when
        List<AdItemDTO> adItemDTOResult = itemsService.getItems(testItemIds);

        // then
        assertNotNull(adItemDTOResult);
        assertEquals(3, adItemDTOResult.size());
        assertEquals(adItemDTO1.get().getId(), adItemDTOResult.get(0).getId());
        assertTrue(adItemDTOResult.contains(adItemDTO1.get()));
        assertTrue(adItemDTOResult.contains(adItemDTO2.get()));
        assertTrue(adItemDTOResult.contains(adItemDTO3.get()));
    }

}