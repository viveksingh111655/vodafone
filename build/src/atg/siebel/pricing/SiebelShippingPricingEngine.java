package atg.siebel.pricing;

import java.util.Map;

import atg.commerce.pricing.ShippingPricingEngineImpl;

public class SiebelShippingPricingEngine extends ShippingPricingEngineImpl {

	
	String mDefaultShippingCarrier = null;

	public String getDefaultShippingCarrier() {
  	return mDefaultShippingCarrier;
  }

	public void setDefaultShippingCarrier(
      String pDefaultShippingCarrier) {
		mDefaultShippingCarrier = pDefaultShippingCarrier;
  }
	
	
	Map mShippingMethodShippingCarrierMap;

	public Map getShippingMethodShippingCarrierMap() {
  	return mShippingMethodShippingCarrierMap;
  }

	public void setShippingMethodShippingCarrierMap(
      Map pShippingNameShippingCarrierMap) {
		mShippingMethodShippingCarrierMap = pShippingNameShippingCarrierMap;
  }
	
	public String getShippingCarrier(String pShippingMethod) {
		if (mShippingMethodShippingCarrierMap == null
				|| mShippingMethodShippingCarrierMap.get(pShippingMethod) == null) {
			return getDefaultShippingCarrier();
		}
		return (String) mShippingMethodShippingCarrierMap.get(pShippingMethod);
	}
	
}
