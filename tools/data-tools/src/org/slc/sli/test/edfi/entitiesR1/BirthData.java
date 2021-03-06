//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.31 at 10:43:34 AM EDT 
//


package org.slc.sli.test.edfi.entitiesR1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The set of elements that capture relevant data
 *                 regarding a person's
 *                 birth, including birth date and place of birth.
 *             
 * 
 * <p>Java class for birthData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="birthData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="birthDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="cityOfBirth" type="{http://slc-sli/ed-org/0.1}city" minOccurs="0"/>
 *         &lt;element name="stateOfBirthAbbreviation" type="{http://slc-sli/ed-org/0.1}stateAbbreviationType" minOccurs="0"/>
 *         &lt;element name="countryOfBirthCode" type="{http://slc-sli/ed-org/0.1}countryType" minOccurs="0"/>
 *         &lt;element name="dateEnteredUS" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="multipleBirthStatus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "birthData", propOrder = {
    "birthDate",
    "cityOfBirth",
    "stateOfBirthAbbreviation",
    "countryOfBirthCode",
    "dateEnteredUS",
    "multipleBirthStatus"
})
public class BirthData {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "date")
    protected String birthDate;
    protected String cityOfBirth;
    protected StateAbbreviationType stateOfBirthAbbreviation;
    protected CountryType countryOfBirthCode;
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "date")
    protected String dateEnteredUS;
    protected Boolean multipleBirthStatus;

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthDate(String value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the cityOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityOfBirth() {
        return cityOfBirth;
    }

    /**
     * Sets the value of the cityOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityOfBirth(String value) {
        this.cityOfBirth = value;
    }

    /**
     * Gets the value of the stateOfBirthAbbreviation property.
     * 
     * @return
     *     possible object is
     *     {@link StateAbbreviationType }
     *     
     */
    public StateAbbreviationType getStateOfBirthAbbreviation() {
        return stateOfBirthAbbreviation;
    }

    /**
     * Sets the value of the stateOfBirthAbbreviation property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateAbbreviationType }
     *     
     */
    public void setStateOfBirthAbbreviation(StateAbbreviationType value) {
        this.stateOfBirthAbbreviation = value;
    }

    /**
     * Gets the value of the countryOfBirthCode property.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getCountryOfBirthCode() {
        return countryOfBirthCode;
    }

    /**
     * Sets the value of the countryOfBirthCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setCountryOfBirthCode(CountryType value) {
        this.countryOfBirthCode = value;
    }

    /**
     * Gets the value of the dateEnteredUS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateEnteredUS() {
        return dateEnteredUS;
    }

    /**
     * Sets the value of the dateEnteredUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateEnteredUS(String value) {
        this.dateEnteredUS = value;
    }

    /**
     * Gets the value of the multipleBirthStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMultipleBirthStatus() {
        return multipleBirthStatus;
    }

    /**
     * Sets the value of the multipleBirthStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMultipleBirthStatus(Boolean value) {
        this.multipleBirthStatus = value;
    }

}
