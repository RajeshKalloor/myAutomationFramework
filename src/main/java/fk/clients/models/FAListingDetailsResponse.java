package fk.clients.models;

import lombok.Data;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
@Data
public class FAListingDetailsResponse {

  private ListingFaDetails listing_fadetails;

  @Data
  public static class ListingFaDetails {

    private  LSTMOBCZYHG4DCPVYGXAYSTPD  LSTMOBCZYHG4DCPVYGXAYSTPD;

    @Data
    public static class LSTMOBCZYHG4DCPVYGXAYSTPD {

      private String listing_id;

      private Integer min_depth;

      private String listing_consignment_detail;

      private boolean wh_support;


    }

  }


}
