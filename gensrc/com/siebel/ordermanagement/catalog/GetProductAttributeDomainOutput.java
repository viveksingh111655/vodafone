
package com.siebel.ordermanagement.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.siebel.ordermanagement.catalog.data.productattributedomain.ListOfProductAttributeDomain;


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
 *         &lt;element ref="{http://siebel.com/OrderManagement/Catalog/Data/ProductAttributeDomain}ListOfProductAttributeDomain" minOccurs="0"/>
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
    "listOfProductAttributeDomain"
})
@XmlRootElement(name = "GetProductAttributeDomain_Output")
public class GetProductAttributeDomainOutput {

    @XmlElement(name = "ListOfProductAttributeDomain", namespace = "http://siebel.com/OrderManagement/Catalog/Data/ProductAttributeDomain")
    protected ListOfProductAttributeDomain listOfProductAttributeDomain;

    /**
     * Gets the value of the listOfProductAttributeDomain property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfProductAttributeDomain }
     *     
     */
    public ListOfProductAttributeDomain getListOfProductAttributeDomain() {
        return listOfProductAttributeDomain;
    }

    /**
     * Sets the value of the listOfProductAttributeDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfProductAttributeDomain }
     *     
     */
    public void setListOfProductAttributeDomain(ListOfProductAttributeDomain value) {
        this.listOfProductAttributeDomain = value;
    }

}
