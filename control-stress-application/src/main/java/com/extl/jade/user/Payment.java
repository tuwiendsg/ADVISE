
package com.extl.jade.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for payment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="payment">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREDIT_CARD"/>
 *     &lt;enumeration value="DEBIT_CARD"/>
 *     &lt;enumeration value="INVOICE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "payment")
@XmlEnum
public enum Payment {


    /**
     * Credit card payment is permitted
     * 
     */
    CREDIT_CARD,

    /**
     * Debit card payment is permitted
     * 
     */
    DEBIT_CARD,

    /**
     * Invoice payment is permitted
     * 
     */
    INVOICE;

    public String value() {
        return name();
    }

    public static Payment fromValue(String v) {
        return valueOf(v);
    }

}
