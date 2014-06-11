
package com.siebel.ordermanagement.promotion.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfAgreementItemConditionalCharge complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfAgreementItemConditionalCharge">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgreementItemConditionalCharge" type="{http://siebel.com/OrderManagement/Promotion/Data}AgreementItemConditionalCharge" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfAgreementItemConditionalCharge", propOrder = {
    "agreementItemConditionalCharge"
})
public class ListOfAgreementItemConditionalCharge {

    @XmlElement(name = "AgreementItemConditionalCharge")
    protected List<AgreementItemConditionalCharge> agreementItemConditionalCharge;

    /**
     * Gets the value of the agreementItemConditionalCharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agreementItemConditionalCharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgreementItemConditionalCharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgreementItemConditionalCharge }
     * 
     * 
     */
    public List<AgreementItemConditionalCharge> getAgreementItemConditionalCharge() {
        if (agreementItemConditionalCharge == null) {
            agreementItemConditionalCharge = new ArrayList<AgreementItemConditionalCharge>();
        }
        return this.agreementItemConditionalCharge;
    }

}
