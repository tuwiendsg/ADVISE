
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for billingPeriod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="billingPeriod">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ONE_OFF"/>
 *     &lt;enumeration value="HOURLY"/>
 *     &lt;enumeration value="DAILY"/>
 *     &lt;enumeration value="MONTHLY"/>
 *     &lt;enumeration value="ANNUALLY"/>
 *     &lt;enumeration value="WEEKLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "billingPeriod")
@XmlEnum
public enum BillingPeriod {


    /**
     * One off charge
     * 
     */
    ONE_OFF,

    /**
     * Hourly charge
     * 
     */
    HOURLY,

    /**
     * Daily charge
     * 
     */
    DAILY,

    /**
     * Monthly charge
     * 
     */
    MONTHLY,

    /**
     * Annual charge
     * 
     */
    ANNUALLY,

    /**
     * Weekly charge
     * 
     */
    WEEKLY;

    public String value() {
        return name();
    }

    public static BillingPeriod fromValue(String v) {
        return valueOf(v);
    }

}
