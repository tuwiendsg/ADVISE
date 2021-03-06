
package com.extl.jade.user;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>The Snapshot class is a virtual resource representing a snapshot of a disk or a server.</p>
 * 
 * <p>Java class for snapshot complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="snapshot">
 *   &lt;complexContent>
 *     &lt;extension base="{http://extility.flexiant.net}virtualResource">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://extility.flexiant.net}snapshotType" minOccurs="0"/>
 *         &lt;element name="parentUUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentType" type="{http://extility.flexiant.net}resourceType" minOccurs="0"/>
 *         &lt;element name="hypervisorSettings" type="{http://extility.flexiant.net}hypervisorSetting" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "snapshot", propOrder = {
    "type",
    "parentUUID",
    "parentName",
    "parentType",
    "hypervisorSettings"
})
public class Snapshot
    extends VirtualResource
{

    protected SnapshotType type;
    protected String parentUUID;
    protected String parentName;
    protected ResourceType parentType;
    @XmlElement(nillable = true)
    protected List<HypervisorSetting> hypervisorSettings;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SnapshotType }
     *     
     */
    public SnapshotType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SnapshotType }
     *     
     */
    public void setType(SnapshotType value) {
        this.type = value;
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
     * Gets the value of the parentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets the value of the parentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentName(String value) {
        this.parentName = value;
    }

    /**
     * Gets the value of the parentType property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceType }
     *     
     */
    public ResourceType getParentType() {
        return parentType;
    }

    /**
     * Sets the value of the parentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceType }
     *     
     */
    public void setParentType(ResourceType value) {
        this.parentType = value;
    }

    /**
     * Gets the value of the hypervisorSettings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hypervisorSettings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHypervisorSettings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HypervisorSetting }
     * 
     * 
     */
    public List<HypervisorSetting> getHypervisorSettings() {
        if (hypervisorSettings == null) {
            hypervisorSettings = new ArrayList<HypervisorSetting>();
        }
        return this.hypervisorSettings;
    }

}
