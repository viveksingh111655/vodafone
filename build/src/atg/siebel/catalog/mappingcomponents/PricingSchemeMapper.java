package atg.siebel.catalog.mappingcomponents;

import java.util.List;
import java.util.Map;

import atg.siebel.catalog.mapper.ATGData;
import atg.siebel.catalog.mapper.MappingConstants;
import atg.siebel.catalog.mapper.MappingException;

/*
 * This Mapper assumes that all the VolumeDiscount Siebel elements have 
 * been processed before the PriceListItem elements. We can assume this because
 * the VolumeDiscount elements are top level which get processed first. The 
 * PriceListItem is a nested element and nested elements get processed
 * after all top level elements are processed.
 * 
 * Obviously this assumption makes this Mapper a potential source of bugs if the
 * order that Siebel elements are processed was to change.
 * 
 */
public class PricingSchemeMapper extends BaseMapper {

  // Siebel element name
  private static final String VOLUME_DISCOUNT_ID = "VolumeDiscountId";
  
  // ATG item-descriptor name
  private static final String COMPLEX_PRICE_ITEM_DESC = "complexPrice";
  
  // ATG item-descriptor property name
  private static final String ID = "id";
  private static final String DISCOUNT_METHOD = "discountMethod";
  
  @Override
  public Object mapSiebelToATG(Object pSiebelFields, Map<String, Object> pAttributes) 
        throws MappingException {
    mAttributes = pAttributes;

    // check to see if there is a volume discount associated with this PriceListItem
    Object obj = getSiebelValue(VOLUME_DISCOUNT_ID);
    // if not the pricingScheme accordingly
    if (obj == null) {
      return MappingConstants.PRICING_SCHEME_LIST;
    }
    
    String volumeDiscountId = (String) obj;
        
    // loop through all items that have already been mapped
    List<ATGData> mappedItems = getMappingService().getMappedItems();
    for (ATGData atgData : mappedItems) {
      // looked for all complexPrice items (what Siebel VolumeDiscount maps to)
      if (!atgData.getName().equalsIgnoreCase(COMPLEX_PRICE_ITEM_DESC)) {
        continue;
      }
      // attempt to find the complexPrice with id equal to VolumeDiscountId from the
      // PriceListItem
      String complexPriceId = (String)atgData.getProperties().get(ID);
      if (!complexPriceId.equalsIgnoreCase(volumeDiscountId)) {
        continue;
      }
    
      // now check the discount method property
      String discountMethod = (String)atgData.getProperties().get(DISCOUNT_METHOD);
      if (discountMethod.equalsIgnoreCase(MappingConstants.DISCOUNT_METHOD_SIMPLE)) {
        return MappingConstants.PRICING_SCHEME_BULK;
      }
      if (discountMethod.equalsIgnoreCase(MappingConstants.DISCOUNT_METHOD_TIERED)) {
        return MappingConstants.PRICING_SCHEME_TIERED;
      }
    }
    
    return MappingConstants.PRICING_SCHEME_LIST;
  }

}
