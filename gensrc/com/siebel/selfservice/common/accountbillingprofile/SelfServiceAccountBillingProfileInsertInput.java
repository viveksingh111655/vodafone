
package com.siebel.selfservice.common.accountbillingprofile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.siebel.selfservice.common.accountbillingprofile.data.ListOfSsAccountbillingprofileIoData;


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
 *         &lt;element ref="{http://www.siebel.com/SelfService/Common/AccountBillingProfile/Data}ListOfSs_Accountbillingprofile_Io"/>
 *         &lt;element name="ExecutionMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LOVLanguageMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ViewMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "executionMode",
    "lovLanguageMode",
    "viewMode"
})
@XmlRootElement(name = "SelfServiceAccountBillingProfileInsert_Input")
public class SelfServiceAccountBillingProfileInsertInput {

    @XmlElement(name = "ListOfSs_Accountbillingprofile_Io", namespace = "http://www.siebel.com/SelfService/Common/AccountBillingProfile/Data", required = true)
    protected ListOfSsAccountbillingprofileIoData listOfSsAccountbillingprofileIo;
    @XmlElement(name = "ExecutionMode")
    protected String executionMode;
    @XmlElement(name = "LOVLanguageMode", required = true)
    protected String lovLanguageMode;
    @XmlElement(name = "ViewMode")
    protected String viewMode;

    /**
     * Gets the value of the listOfSsAccountbillingprofileIo property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfSsAccountbillingprofileIoData }
     *     
     */
    public ListOfSsAccountbillingprofileIoData getListOfSsAccountbillingprofileIo() {
        return listOfSsAccountbillingprofileIo;
    }

    /**
     * Sets the value of the listOfSsAccountbillingprofileIo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfSsAccountbillingprofileIoData }
     *     
     */
    public void setListOfSsAccountbillingprofileIo(ListOfSsAccountbillingprofileIoData value) {
        this.listOfSsAccountbillingprofileIo = value;
    }

    /**
     * Gets the value of the executionMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecutionMode() {
        return executionMode;
    }

    /**
     * Sets the value of the executionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecutionMode(String value) {
        this.executionMode = value;
    }

    /**
     * Gets the value of the lovLanguageMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOVLanguageMode() {
        return lovLanguageMode;
    }

    /**
     * Sets the value of the lovLanguageMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOVLanguageMode(String value) {
        this.lovLanguageMode = value;
    }

    /**
     * Gets the value of the viewMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewMode() {
        return viewMode;
    }

    /**
     * Sets the value of the viewMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewMode(String value) {
        this.viewMode = value;
    }

}
