
package com.siebel.selfservice.common.accountbillingprofile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.siebel.com/SelfService/Common/AccountBillingProfile}ListOfSs_Accountbillingprofile_Io"/>
 *         &lt;element name="StatusObject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "listOfSsAccountbillingprofileIo",
    "statusObject"
})
@XmlRootElement(name = "SelfServiceAccountBillingProfileInit_Input")
public class SelfServiceAccountBillingProfileInitInput {

    @XmlElement(name = "ListOfSs_Accountbillingprofile_Io", required = true)
    protected ListOfSsAccountbillingprofileIo listOfSsAccountbillingprofileIo;
    @XmlElement(name = "StatusObject")
    protected String statusObject;

    /**
     * Gets the value of the listOfSsAccountbillingprofileIo property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfSsAccountbillingprofileIo }
     *     
     */
    public ListOfSsAccountbillingprofileIo getListOfSsAccountbillingprofileIo() {
        return listOfSsAccountbillingprofileIo;
    }

    /**
     * Sets the value of the listOfSsAccountbillingprofileIo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfSsAccountbillingprofileIo }
     *     
     */
    public void setListOfSsAccountbillingprofileIo(ListOfSsAccountbillingprofileIo value) {
        this.listOfSsAccountbillingprofileIo = value;
    }

    /**
     * Gets the value of the statusObject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusObject() {
        return statusObject;
    }

    /**
     * Sets the value of the statusObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusObject(String value) {
        this.statusObject = value;
    }

}
