
package com.amazonaws.fps.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecipientVerificationStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RecipientVerificationStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VerificationComplete"/>
 *     &lt;enumeration value="VerificationPending"/>
 *     &lt;enumeration value="VerificationCompleteNoLimits"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecipientVerificationStatus")
@XmlEnum
public enum RecipientVerificationStatus {

    @XmlEnumValue("VerificationComplete")
    VERIFICATION_COMPLETE("VerificationComplete"),
    @XmlEnumValue("VerificationPending")
    VERIFICATION_PENDING("VerificationPending"),
    @XmlEnumValue("VerificationCompleteNoLimits")
    VERIFICATION_COMPLETE_NO_LIMITS("VerificationCompleteNoLimits");
    private final String value;

    RecipientVerificationStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RecipientVerificationStatus fromValue(String v) {
        for (RecipientVerificationStatus c: RecipientVerificationStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
