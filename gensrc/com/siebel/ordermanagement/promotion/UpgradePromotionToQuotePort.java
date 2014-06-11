
package com.siebel.ordermanagement.promotion;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "UpgradePromotionToQuotePort", targetNamespace = "http://siebel.com/OrderManagement/Promotion")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.siebel.ordermanagement.promotion.ObjectFactory.class,
    com.siebel.ordermanagement.quote.data.ObjectFactory.class,
    com.siebel.ordermanagement.catalog.data.product.ObjectFactory.class,
    com.siebel.ordermanagement.catalog.data.catalogcontext.ObjectFactory.class,
    com.siebel.ordermanagement.order.data.ObjectFactory.class,
    com.siebel.ordermanagement.promotion.data.ObjectFactory.class
})
public interface UpgradePromotionToQuotePort {


    /**
     * 
     * @param upgradePromotionToQuoteInput
     * @return
     *     returns com.siebel.ordermanagement.promotion.UpgradePromotionToQuoteOutput
     */
    @WebMethod(operationName = "UpgradePromotionToQuote", action = "document/http://siebel.com/OrderManagement/Promotion:UpgradePromotionToQuote")
    @WebResult(name = "UpgradePromotionToQuote_Output", targetNamespace = "http://siebel.com/OrderManagement/Promotion", partName = "UpgradePromotionToQuote_Output")
    public UpgradePromotionToQuoteOutput upgradePromotionToQuote(
        @WebParam(name = "UpgradePromotionToQuote_Input", targetNamespace = "http://siebel.com/OrderManagement/Promotion", partName = "UpgradePromotionToQuote_Input")
        UpgradePromotionToQuoteInput upgradePromotionToQuoteInput);

}