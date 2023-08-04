package com.example.rest.apiItems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdItemDTO {
    private String id;

    @JsonProperty("campaign_id")
    private Long campaignId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("site_id")
    private String siteId;
    private String status;
    private String title;
    private float price;

    @JsonProperty("currency_id")
    private String currencyId;
    private String permalink;
    private String thumbnail;

    @JsonProperty("picture_id")
    private String pictureId;

    @JsonProperty("date_created")
    private LocalDateTime dateCreated;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

//    public AdItemDTO(String id, Long campaignId, Long userId, String siteId, String status, String title, float price, String currencyId, String permalink, String thumbnail, String pictureId, LocalDateTime dateCreated, LocalDateTime lastUpdated) {
//        this.id = id;
//        this.campaignId = campaignId;
//        this.userId = userId;
//        this.siteId = siteId;
//        this.status = status;
//        this.title = title;
//        this.price = price;
//        this.currencyId = currencyId;
//        this.permalink = permalink;
//        this.thumbnail = thumbnail;
//        this.pictureId = pictureId;
//        this.dateCreated = dateCreated;
//        this.lastUpdated = lastUpdated;
//    }


    @Override
    public String toString() {
        return "AdItemDTO{" +
                "id='" + id + '\'' +
                ", campaignId=" + campaignId +
                ", userId=" + userId +
                ", siteId='" + siteId + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currencyId='" + currencyId + '\'' +
                ", permalink='" + permalink + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", pictureId='" + pictureId + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
