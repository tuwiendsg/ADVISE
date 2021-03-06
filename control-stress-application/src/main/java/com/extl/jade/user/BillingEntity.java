
package com.extl.jade.user;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>The BillingEntity class is a virtual resource holding data for each Billing Entity</p>
 * 
 * <p>Java class for billingEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="billingEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://extility.flexiant.net}resource">
 *       &lt;sequence>
 *         &lt;element name="billingType" type="{http://extility.flexiant.net}billingType" minOccurs="0"/>
 *         &lt;element name="systemCapabilities" type="{http://extility.flexiant.net}systemCapabilitySet" minOccurs="0"/>
 *         &lt;element name="permittedPOResourceTypes" type="{http://extility.flexiant.net}resourceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="parentUUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descendants" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billingEntity", propOrder = {
    "billingType",
    "systemCapabilities",
    "permittedPOResourceTypes",
    "parentUUID",
    "descendants"
})
@XmlSeeAlso({
    BillingEntityDetails.class
})
public class BillingEntity
    extends Resource
{

    protected BillingType billingType;
    protected SystemCapabilitySet systemCapabilities;
    @XmlElement(nillable = true)
    protected List<ResourceType> permittedPOResourceTypes;
    protected String parentUUID;
    @XmlElement(nillable = true)
    protected List<String> descendants;

    /**
     * Gets the value of the billingType property.
     * 
     * @return
     *     possible object is
     *     {@link BillingType }
     *     
     */
    public BillingType getBillingType() {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingType }
     *     
     */
    public void setBillingType(BillingType value) {
        this.billingType = value;
    }

    /**
     * Gets the value of the systemCapabilities property.
     * 
     * @return
     *     possible object is
     *     {@link SystemCapabilitySet }
     *     
     */
    public SystemCapabilitySet getSystemCapabilities() {
        return systemCapabilities;
    }

    /**
     * Sets the value of the systemCapabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemCapabilitySet }
     *     
     */
    public void setSystemCapabilities(SystemCapabilitySet value) {
        this.systemCapabilities = value;
    }

    /**
     * Gets the value of the permittedPOResourceTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permittedPOResourceTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermittedPOResourceTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceType }
     * 
     * 
     */
    public List<ResourceType> getPermittedPOResourceTypes() {
        if (permittedPOResourceTypes == null) {
            permittedPOResourceTypes = new ArrayList<ResourceType>();
        }
        return this.permittedPOResourceTypes;
    }

    /**
     * Gets the value of the parentUUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentUUID() {
        return parentUUID;
    }

    /**
     * Sets the value of the parentUUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentUUID(String value) {
        this.parentUUID = value;
    }

    /**
     * Gets the value of the descendants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descendants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescendants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDescendants() {
        if (descendants == null) {
            descendants = new ArrayList<String>();
        }
        return this.descendants;
    }

}
