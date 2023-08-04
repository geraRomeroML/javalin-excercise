package com.example.rest.apiItems;

import com.example.extensions.mockito.MockitoTest;
import com.example.rest.MeliContextAwareRestClientTestBase;
import com.mercadolibre.json.JsonUtils;
import com.mercadolibre.restclient.MockResponse;
import com.mercadolibre.restclient.http.ContentType;
import com.mercadolibre.restclient.http.HttpMethod;
import com.mercadolibre.restclient.mock.RequestMockHolder;
import com.mercadolibre.routing.RoutingHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;

@MockitoTest
public class ApiItemsRestServiceTest extends MeliContextAwareRestClientTestBase {
    private final String baseUrl = "https://internal-api.mercadolibre.com";
    private final static String testId = "MLA12345";
    private final static Long testCampaignId = 12345L;
    private final static String testSiteId = "MLA";
    private final Long testUserId = 12345678L;
    private final String testStatus = "ACTIVE";
    private final String testTitle = "Piano";

    @InjectMocks
    private ApiItemsRestService apiItemsRestService;

    @BeforeEach
    public void setUp() {
        RoutingHelper.createAndSetNewRequestId();
        initMocks(this);
    }

    @AfterEach
    void resetMockRest() {
        RoutingHelper.clearRequestId();
        RequestMockHolder.clear();
    }

    @Test
    public void whenAdItemIsFoundThenOptionalAdItemShouldBeReturned() throws Exception {
        final AdItemDTO adItemDTOExpected = new AdItemDTOFixture()
                .withId(testId)
                .withCampaignId(testCampaignId)
                .withSiteId(testSiteId)
                .withUserId(testUserId)
                .withStatus(testStatus)
                .withTitle(testTitle)
                .build();

        final String adItemDTOJsonString = JsonUtils.INSTANCE.toJsonString(adItemDTOExpected);
        final String url = String.join("/", baseUrl, "items", testId);

        MockResponse r = MockResponse.builder()
                .withURL(url)
                .withMethod(HttpMethod.GET)
                .withStatusCode(HttpServletResponse.SC_OK)
                .withResponseBody(adItemDTOJsonString)
                .withResponseHeader(ContentType.HEADER_NAME, ContentType.APPLICATION_JSON.toString())
                .build();

        final Optional<AdItemDTO> response = apiItemsRestService.getItemById(testId);
        final AdItemDTO adItemDTOResult = response.orElse(null);

        assertNotNull(adItemDTOResult);
        assertEquals(testId, adItemDTOResult.getId());
        assertEquals(testCampaignId, adItemDTOResult.getCampaignId());
        assertEquals(testSiteId, adItemDTOResult.getSiteId());
        assertEquals(testUserId, adItemDTOResult.getUserId());
        assertEquals(testStatus, adItemDTOResult.getStatus());
        assertEquals(testTitle, adItemDTOResult.getTitle());
    }

    @Test
    public void whenAdItemNotFoundThenOptionalEmptyShouldBeReturned() throws Exception {
        final String responseString = String.format("{\"message\":\"Item with id %s not found\",\"error\":\"not_found\",\"status\":404,\"cause\":[]}", testId);
        final String url = String.join("/", baseUrl, "items", testId);

        MockResponse r = MockResponse.builder()
                .withURL(url)
                .withMethod(HttpMethod.GET)
                .withStatusCode(HttpServletResponse.SC_NOT_FOUND)
                .withResponseBody(responseString)
                .withRequestHeader("Content-Type","application/json")
                .build();

        final Optional<AdItemDTO> response = apiItemsRestService.getItemById(testId);

        assertNull(response.orElse(null));
    }

//    @Test
//    public void getResourceName() {
//        fail();
//    }
//
//    @Test
//    public void getItemById() {
//        fail();
//    }
}
