
package com.siebel.ordermanagement.promotion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.siebel.ordermanagement.order.data.ListOfOrder;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActiveDocumentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Error_spcCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Error_spcMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://siebel.com/OrderManagement/Order/Data}ListOfOrder"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "activeDocumentId",
    "errorSpcCode",
    "errorSpcMessage",
    "listOfOrder"
})
@XmlRootElement(name = "UpgradePromotionToOrder_Output")
public class UpgradePromotionToOrderOutput {

    @XmlElement(name = "ActiveDocumentId", required = true)
    protected String activeDocumentId;
    @XmlElement(name = "Error_spcCode", required = true)
    protected String errorSpcCode;
    @XmlElement(name = "Error_spcMessage", required = true)
    protected String errorSpcMessage;
    @XmlElement(name = "ListOfOrder", namespace = "http://siebel.com/OrderManagement/Order/Data", required = true)
    protected ListOfOrder listOfOrder;

    /**
     * Gets the value of the activeDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveDocumentId() {
        return activeDocumentId;
    }

    /**
     * Sets the value of the activeDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveDocumentId(String value) {
        this.activeDocumentId = value;
    }

    /**
     * Gets the value of the errorSpcCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSpcCode() {
        return errorSpcCode;
    }

    /**
     * Sets the value of the errorSpcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSpcCode(String value) {
        this.errorSpcCode = value;
    }

    /**
     * Gets the value of the errorSpcMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSpcMessage() {
        return errorSpcMessage;
    }

    /**
     * Sets the value of the errorSpcMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSpcMessage(String value) {
        this.errorSpcMessage = value;
    }

    /**
     * Gets the value of the listOfOrder property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfOrder }
     *     
     */
    public ListOfOrder getListOfOrder() {
        return listOfOrder;
    }

    /**
     * Sets the value of the listOfOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfOrder }
     *     
     */
    public void setListOfOrder(ListOfOrder value) {
        this.listOfOrder = value;
    }

}
