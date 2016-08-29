package fk.clients.request_factory;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by rajesh.kalloor on 07/05/16.
 */
@Data
public class UpdateTaxFactory {
  private List<TaxRules> rules;

  @Data
  public static class TaxRules {

    private double rate;
    private String listing_id;
    private String type = "VAT";

  }

  public UpdateTaxFactory updateTaxRequest(double rate, String listing) {

    this.rules = new ArrayList<TaxRules>();
    TaxRules taxRules = new TaxRules();
    taxRules.rate = rate;
    taxRules.listing_id = listing;

    this.rules.add(taxRules);
    return this;
  }

}
