
package com.amazonaws.fps.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TokenUsageLimit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TokenUsageLimit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Amount" type="{http://fps.amazonaws.com/doc/2010-08-28/}Amount" minOccurs="0"/>
 *         &lt;element name="LastResetCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LastResetAmount" type="{http://fps.amazonaws.com/doc/2010-08-28/}Amount" minOccurs="0"/>
 *         &lt;element name="LastResetTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * Generated by AWS Code Generator
 * <p/>
 * Mon May 30 08:10:15 GMT+00:00 2011
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TokenUsageLimit", propOrder = {
    "count",
    "amount",
    "lastResetCount",
    "lastResetAmount",
    "lastResetTimestamp"
})
public class TokenUsageLimit {

    @XmlElement(name = "Count")
    protected Integer count;
    @XmlElement(name = "Amount")
    protected Amount amount;
    @XmlElement(name = "LastResetCount")
    protected Integer lastResetCount;
    @XmlElement(name = "LastResetAmount")
    protected Amount lastResetAmount;
    @XmlElement(name = "LastResetTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastResetTimestamp;

    /**
     * Default constructor
     * 
     */
    public TokenUsageLimit() {
        super();
    }

    /**
     * Value constructor
     * 
     */
    public TokenUsageLimit(final Integer count, final Amount amount, final Integer lastResetCount, final Amount lastResetAmount, final XMLGregorianCalendar lastResetTimestamp) {
        this.count = count;
        this.amount = amount;
        this.lastResetCount = lastResetCount;
        this.lastResetAmount = lastResetAmount;
        this.lastResetTimestamp = lastResetTimestamp;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    public boolean isSetCount() {
        return (this.count!= null);
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Amount }
     *
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     *
     * @param value
     *     allowed object is
     *     {@link Amount }
     *
     */
    public void setAmount(Amount value) {
        this.amount = value;
    }

    public boolean isSetAmount() {
        return (this.amount!= null);
    }

    /**
     * Gets the value of the lastResetCount property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getLastResetCount() {
        return lastResetCount;
    }

    /**
     * Sets the value of the lastResetCount property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setLastResetCount(Integer value) {
        this.lastResetCount = value;
    }

    public boolean isSetLastResetCount() {
        return (this.lastResetCount!= null);
    }

    /**
     * Gets the value of the lastResetAmount property.
     *
     * @return
     *     possible object is
     *     {@link Amount }
     *
     */
    public Amount getLastResetAmount() {
        return lastResetAmount;
    }

    /**
     * Sets the value of the lastResetAmount property.
     *
     * @param value
     *     allowed object is
     *     {@link Amount }
     *
     */
    public void setLastResetAmount(Amount value) {
        this.lastResetAmount = value;
    }

    public boolean isSetLastResetAmount() {
        return (this.lastResetAmount!= null);
    }

    /**
     * Gets the value of the lastResetTimestamp property.
     *
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getLastResetTimestamp() {
        return lastResetTimestamp;
    }

    /**
     * Sets the value of the lastResetTimestamp property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *     
     */
    public void setLastResetTimestamp(XMLGregorianCalendar value) {
        this.lastResetTimestamp = value;
    }

    public boolean isSetLastResetTimestamp() {
        return (this.lastResetTimestamp!= null);
    }

    /**
     * Sets the value of the Count property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public TokenUsageLimit withCount(Integer value) {
        setCount(value);
        return this;
    }

    /**
     * Sets the value of the Amount property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public TokenUsageLimit withAmount(Amount value) {
        setAmount(value);
        return this;
    }

    /**
     * Sets the value of the LastResetCount property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public TokenUsageLimit withLastResetCount(Integer value) {
        setLastResetCount(value);
        return this;
    }

    /**
     * Sets the value of the LastResetAmount property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public TokenUsageLimit withLastResetAmount(Amount value) {
        setLastResetAmount(value);
        return this;
    }

    /**
     * Sets the value of the LastResetTimestamp property.
     * 
     * @param value
     * @return
     *     this instance
     */
    public TokenUsageLimit withLastResetTimestamp(XMLGregorianCalendar value) {
        setLastResetTimestamp(value);
        return this;
    }
    

    /**
     * 
     * XML fragment representation of this object
     * 
     * @return XML fragment for this object. Name for outer
     * tag expected to be set by calling method. This fragment
     * returns inner properties representation only
     */
    protected String toXMLFragment() {
        StringBuffer xml = new StringBuffer();
        if (isSetCount()) {
            xml.append("<Count>");
            xml.append(getCount() + "");
            xml.append("</Count>");
        }
        if (isSetAmount()) {
            Amount amount = getAmount();
            xml.append("<Amount>");
            xml.append(amount.toXMLFragment());
            xml.append("</Amount>");
        } 
        if (isSetLastResetCount()) {
            xml.append("<LastResetCount>");
            xml.append(getLastResetCount() + "");
            xml.append("</LastResetCount>");
        }
        if (isSetLastResetAmount()) {
            Amount lastResetAmount = getLastResetAmount();
            xml.append("<LastResetAmount>");
            xml.append(lastResetAmount.toXMLFragment());
            xml.append("</LastResetAmount>");
        } 
        if (isSetLastResetTimestamp()) {
            xml.append("<LastResetTimestamp>");
            xml.append(getLastResetTimestamp() + "");
            xml.append("</LastResetTimestamp>");
        }
        return xml.toString();
    }

    /**
     * 
     * Escape XML special characters
     */
    private String escapeXML(String string) {
        StringBuffer sb = new StringBuffer();
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&#039;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }



    /**
     *
     * JSON fragment representation of this object
     *
     * @return JSON fragment for this object. Name for outer
     * object expected to be set by calling method. This fragment
     * returns inner properties representation only
     *
     */
    protected String toJSONFragment() {
        StringBuffer json = new StringBuffer();
        boolean first = true;
        if (isSetCount()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("Count"));
            json.append(" : ");
            json.append(quoteJSON(getCount() + ""));
            first = false;
        }
        if (isSetAmount()) {
            if (!first) json.append(", ");
            json.append("\"Amount\" : {");
            Amount amount = getAmount();


            json.append(amount.toJSONFragment());
            json.append("}");
            first = false;
        }
        if (isSetLastResetCount()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("LastResetCount"));
            json.append(" : ");
            json.append(quoteJSON(getLastResetCount() + ""));
            first = false;
        }
        if (isSetLastResetAmount()) {
            if (!first) json.append(", ");
            json.append("\"LastResetAmount\" : {");
            Amount lastResetAmount = getLastResetAmount();


            json.append(lastResetAmount.toJSONFragment());
            json.append("}");
            first = false;
        }
        if (isSetLastResetTimestamp()) {
            if (!first) json.append(", ");
            json.append(quoteJSON("LastResetTimestamp"));
            json.append(" : ");
            json.append(quoteJSON(getLastResetTimestamp() + ""));
            first = false;
        }
        return json.toString();
    }

    /**
     *
     * Quote JSON string
     */
    private String quoteJSON(String string) {
        StringBuffer sb = new StringBuffer();
        sb.append("\"");
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '/':
                sb.append("\\/");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                if (c <  ' ') {
                    sb.append("\\u" + String.format("%03x", Integer.valueOf(c)));
                } else {
                sb.append(c);
            }
        }
        }
        sb.append("\"");
        return sb.toString();
    }


}
