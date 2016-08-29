package fk.clients.request_factory;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
@Data
public class FaListingDetailsFactory {

  @JsonProperty("warehouse_id")
  private String warehouseId;

  @JsonProperty("consignment_id")
  private String consignmentId;

  private List<String> listings = new ArrayList<String>();

  public FaListingDetailsFactory getFaListingDetails(String warehouseId, String consignmentId, List<String> listings) {

    this.warehouseId = warehouseId;
    this.consignmentId = consignmentId;
    this.listings = listings;

    return this;
  }



}
