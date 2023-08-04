package com.example.rest.apiItems;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AdItemDTOFixture {

    private String id = "MLA12345";
    private Long campaignId = 12345L;
    private Long userId = 12345678L;
    private String siteId = "MLA";
    private String status = "ACTIVE";
    private String title = "Piano";
    private float price = 500000f;
    private String currencyId = "ARS";
    private String permalink = "https://articulo.mercadolibre.com.ar/MLA-912131456-pianito";
    private String thumbnail = "http://http2.mlstatic.com/D_752612-MLA45229720613_032021-I.jpg";
    private String pictureId = null;
    private LocalDateTime dateCreated = null;
    private LocalDateTime lastUpdated = null;

    public static AdItemDTO withDefaults() {
        return new AdItemDTOFixture().build();
    }

    public AdItemDTOFixture withId(final String id) {
        this.id = id;
        return this;
    }

    public AdItemDTOFixture withCampaignId(final Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public AdItemDTOFixture withUserId(final Long userId) {
        this.userId = userId;
        return this;
    }

    public AdItemDTOFixture withSiteId(final String siteId) {
        this.siteId = siteId;
        return this;
    }

    public AdItemDTOFixture withStatus(final String status) {
        this.status = status;
        return this;
    }

    public AdItemDTOFixture withTitle(final String title) {
        this.title = title;
        return this;
    }

    public AdItemDTOFixture withPrice(final float price) {
        this.price = price;
        return this;
    }

    public AdItemDTOFixture withCurrencyId(final String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public AdItemDTOFixture withPermalink(final String permalink) {
        this.permalink = permalink;
        return this;
    }

    public AdItemDTOFixture withThumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public AdItemDTOFixture withPictureId(final String pictureId) {
        this.pictureId = pictureId;
        return this;
    }

    public AdItemDTOFixture withDataCreated(final LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public AdItemDTOFixture withLastUpdated(final LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public AdItemDTO build() {
        return new AdItemDTO(id, campaignId, userId, siteId, status, title, price, currencyId, permalink, thumbnail, pictureId, dateCreated, lastUpdated);
    }
}
