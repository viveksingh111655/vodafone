package atg.siebel.pricing;

import atg.commerce.pricing.OrderPriceInfo;

public class SiebelOrderPriceInfo extends OrderPriceInfo
{
  //----------------------------------------------------------------------------------
  // Class version string
  //----------------------------------------------------------------------------------

  public static String CLASS_VERSION = "$Id: //product/Siebel/version/0.6.0.0.0/src/atg/siebel/pricing/SiebelOrderPriceInfo.java#1 $$Change: 842106 $";
  
  //----------------------------------------------------------------------------------
  // property: nonRecurringPrice

  private double mNonRecurringPrice = 0.0;

  /**
   * Returns the nonRecurringPrice
   *
   * @return the nonRecurringPrice
   */

  public double getNonRecurringPrice()
  {
    return mNonRecurringPrice;
  }

  /**
   * Sets the nonRecurringPrice
   *
   * @param pNonRecurringPrice the nonRecurringPrice to set
   */

  public void setNonRecurringPrice (double pNonRecurringPrice)
  {
    mNonRecurringPrice = pNonRecurringPrice;
  }

  //----------------------------------------------------------------------------------
  // property: monthlyRecurringPrice

  private double mMonthlyRecurringPrice = 0.0;

  /**
   * Returns the monthlyRecurringPrice
   *
   * @return the monthlyRecurringPrice
   */

  public double getMonthlyRecurringPrice()
  {
    return mMonthlyRecurringPrice;
  }

  /**
   * Sets the monthlyRecurringPrice
   *
   * @param pMonthlyRecurringPrice the monthlyRecurringPrice to set
   */

  public void setMonthlyRecurringPrice (double pMonthlyRecurringPrice)
  {
    mMonthlyRecurringPrice = pMonthlyRecurringPrice;
  }

}
