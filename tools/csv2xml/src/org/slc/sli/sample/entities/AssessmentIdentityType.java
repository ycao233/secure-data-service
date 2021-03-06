/*
 * Copyright 2012-2013 inBloom, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.04.20 at 03:09:04 PM EDT 
//


package org.slc.sli.sample.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Encapsulates the possible attributes that can be used to lookup the identity of assessments.
 * 
 * <p>Java class for AssessmentIdentityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssessmentIdentityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="AssessmentIdentificationCode" type="{http://ed-fi.org/0100}AssessmentIdentificationCode" maxOccurs="unbounded"/>
 *         &lt;sequence>
 *           &lt;element name="AssessmentFamilyTitle" type="{http://ed-fi.org/0100}AssessmentTitle" minOccurs="0"/>
 *           &lt;element name="AssessmentTitle" type="{http://ed-fi.org/0100}AssessmentTitle" minOccurs="0"/>
 *           &lt;element name="AssessmentCategory" type="{http://ed-fi.org/0100}AssessmentCategoryType" minOccurs="0"/>
 *           &lt;element name="AcademicSubject" type="{http://ed-fi.org/0100}AcademicSubjectType" minOccurs="0"/>
 *           &lt;element name="GradeLevelAssessed" type="{http://ed-fi.org/0100}GradeLevelType" minOccurs="0"/>
 *           &lt;element name="Version" type="{http://ed-fi.org/0100}Version" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssessmentIdentityType", propOrder = {
    "assessmentIdentificationCode",
    "assessmentFamilyTitle",
    "assessmentTitle",
    "assessmentCategory",
    "academicSubject",
    "gradeLevelAssessed",
    "version"
})
public class AssessmentIdentityType {

    @XmlElement(name = "AssessmentIdentificationCode")
    protected List<AssessmentIdentificationCode> assessmentIdentificationCode;
    @XmlElement(name = "AssessmentFamilyTitle")
    protected String assessmentFamilyTitle;
    @XmlElement(name = "AssessmentTitle")
    protected String assessmentTitle;
    @XmlElement(name = "AssessmentCategory")
    protected AssessmentCategoryType assessmentCategory;
    @XmlElement(name = "AcademicSubject")
    protected AcademicSubjectType academicSubject;
    @XmlElement(name = "GradeLevelAssessed")
    protected GradeLevelType gradeLevelAssessed;
    @XmlElement(name = "Version")
    protected Integer version;

    /**
     * Gets the value of the assessmentIdentificationCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assessmentIdentificationCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssessmentIdentificationCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssessmentIdentificationCode }
     * 
     * 
     */
    public List<AssessmentIdentificationCode> getAssessmentIdentificationCode() {
        if (assessmentIdentificationCode == null) {
            assessmentIdentificationCode = new ArrayList<AssessmentIdentificationCode>();
        }
        return this.assessmentIdentificationCode;
    }

    /**
     * Gets the value of the assessmentFamilyTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssessmentFamilyTitle() {
        return assessmentFamilyTitle;
    }

    /**
     * Sets the value of the assessmentFamilyTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssessmentFamilyTitle(String value) {
        this.assessmentFamilyTitle = value;
    }

    /**
     * Gets the value of the assessmentTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    /**
     * Sets the value of the assessmentTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssessmentTitle(String value) {
        this.assessmentTitle = value;
    }

    /**
     * Gets the value of the assessmentCategory property.
     * 
     * @return
     *     possible object is
     *     {@link AssessmentCategoryType }
     *     
     */
    public AssessmentCategoryType getAssessmentCategory() {
        return assessmentCategory;
    }

    /**
     * Sets the value of the assessmentCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssessmentCategoryType }
     *     
     */
    public void setAssessmentCategory(AssessmentCategoryType value) {
        this.assessmentCategory = value;
    }

    /**
     * Gets the value of the academicSubject property.
     * 
     * @return
     *     possible object is
     *     {@link AcademicSubjectType }
     *     
     */
    public AcademicSubjectType getAcademicSubject() {
        return academicSubject;
    }

    /**
     * Sets the value of the academicSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcademicSubjectType }
     *     
     */
    public void setAcademicSubject(AcademicSubjectType value) {
        this.academicSubject = value;
    }

    /**
     * Gets the value of the gradeLevelAssessed property.
     * 
     * @return
     *     possible object is
     *     {@link GradeLevelType }
     *     
     */
    public GradeLevelType getGradeLevelAssessed() {
        return gradeLevelAssessed;
    }

    /**
     * Sets the value of the gradeLevelAssessed property.
     * 
     * @param value
     *     allowed object is
     *     {@link GradeLevelType }
     *     
     */
    public void setGradeLevelAssessed(GradeLevelType value) {
        this.gradeLevelAssessed = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVersion(Integer value) {
        this.version = value;
    }

}
