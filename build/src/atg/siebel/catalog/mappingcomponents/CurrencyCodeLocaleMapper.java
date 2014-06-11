package atg.siebel.catalog.mappingcomponents;

import java.util.Map;

import atg.siebel.catalog.mapper.MappingConstants;
import atg.siebel.catalog.mapper.MappingException;

public class CurrencyCodeLocaleMapper extends BaseMapper {

  @Override
  public Object mapSiebelToATG(Object pSiebelFields, Map<String, Object> pAttributes) throws MappingException {
    
    mSiebelFields = pSiebelFields;
    mAttributes = pAttributes;

    String currencyCode = (String) getSiebelValue();
    if (currencyCode == null) {
      // At this stage default to locale US
      return MappingConstants.LOCALE_US;      
    }
    if (currencyCode.equalsIgnoreCase(MappingConstants.CURRENCY_CODE_US)) {
      return MappingConstants.LOCALE_US;
    }
    if (currencyCode.equalsIgnoreCase(MappingConstants.CURRENCY_CODE_EURO)) {
      return MappingConstants.LOCALE_DE;
    }
    if (currencyCode.equalsIgnoreCase(MappingConstants.CURRENCY_CODE_YEN)) {
      return MappingConstants.LOCALE_JP;
    }
    
    // At this stage default to locale US
    return MappingConstants.LOCALE_US;
  }

}
