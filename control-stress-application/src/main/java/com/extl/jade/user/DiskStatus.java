
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for diskStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="diskStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NOT_ATTACHED_TO_SERVER"/>
 *     &lt;enumeration value="ATTACHED_TO_SERVER"/>
 *     &lt;enumeration value="CANNOT_BE_ATTACHED_TO_SERVER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "diskStatus")
@XmlEnum
public enum DiskStatus {


    /**
     * Not attached to a server
     * 
     */
    NOT_ATTACHED_TO_SERVER,

    /**
     * Attached to a server
     * 
     */
    ATTACHED_TO_SERVER,

    /**
     * Cannot be attached to a server (for instance during creation or deletion)
     * 
     */
    CANNOT_BE_ATTACHED_TO_SERVER;

    public String value() {
        return name();
    }

    public static DiskStatus fromValue(String v) {
        return valueOf(v);
    }

}
