//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.08.31 at 10:43:34 AM EDT
//


package org.slc.sli.test.edfi.entitiesR1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Course
 * 				This educational entity represents the
 * 				organization
 * 				of subject matter and related learning experiences
 * 				provided for the
 * 				instruction of students on a regular or systematic
 * 				basis.
 * 				If you are looking for a specific course instance, use section
 * 				instead.
 *
 *
 * <p>Java class for course complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="course">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="courseTitle" type="{http://slc-sli/ed-org/0.1}courseTitle"/>
 *         &lt;element name="numberOfParts">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="courseCode" type="{http://slc-sli/ed-org/0.1}courseCode" maxOccurs="unbounded"/>
 *         &lt;element name="courseLevel" type="{http://slc-sli/ed-org/0.1}courseLevelType" minOccurs="0"/>
 *         &lt;element name="courseLevelCharacteristics" type="{http://slc-sli/ed-org/0.1}courseLevelCharacteristicItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="gradesOffered" type="{http://slc-sli/ed-org/0.1}gradeLevelType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectArea" type="{http://slc-sli/ed-org/0.1}academicSubjectType" minOccurs="0"/>
 *         &lt;element name="courseDescription" type="{http://slc-sli/ed-org/0.1}description" minOccurs="0"/>
 *         &lt;element name="dateCourseAdopted" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="highSchoolCourseRequirement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="courseGPAApplicability" type="{http://slc-sli/ed-org/0.1}courseGPAApplicabilityType" minOccurs="0"/>
 *         &lt;element name="courseDefinedBy" type="{http://slc-sli/ed-org/0.1}courseDefinedByType" minOccurs="0"/>
 *         &lt;element name="minimumAvailableCredit" type="{http://slc-sli/ed-org/0.1}credits" minOccurs="0"/>
 *         &lt;element name="maximumAvailableCredit" type="{http://slc-sli/ed-org/0.1}credits" minOccurs="0"/>
 *         &lt;element name="careerPathway" type="{http://slc-sli/ed-org/0.1}careerPathwayType" minOccurs="0"/>
 *         &lt;element name="schoolId" type="{http://slc-sli/ed-org/0.1}reference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "course", propOrder = {
    "courseTitle",
    "numberOfParts",
    "courseCode",
    "courseLevel",
    "courseLevelCharacteristics",
    "gradesOffered",
    "subjectArea",
    "courseDescription",
    "dateCourseAdopted",
    "highSchoolCourseRequirement",
    "courseGPAApplicability",
    "courseDefinedBy",
    "minimumAvailableCredit",
    "maximumAvailableCredit",
    "careerPathway",
    "schoolId"
})
public class Course {

    @XmlElement(required = true)
    protected String courseTitle;
    protected int numberOfParts;
    @XmlElement(required = true)
    protected List<CourseCode> courseCode;
    protected CourseLevelType courseLevel;
    protected List<CourseLevelCharacteristicItemType> courseLevelCharacteristics;
    protected List<GradeLevelType> gradesOffered;
    protected AcademicSubjectType subjectArea;
    protected String courseDescription;
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "date")
    protected String dateCourseAdopted;
    protected Boolean highSchoolCourseRequirement;
    protected CourseGPAApplicabilityType courseGPAApplicability;
    protected CourseDefinedByType courseDefinedBy;
    protected Credits minimumAvailableCredit;
    protected Credits maximumAvailableCredit;
    protected CareerPathwayType careerPathway;
    @XmlElement(required = true)
    protected String schoolId;

    /**
     * Gets the value of the courseTitle property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Sets the value of the courseTitle property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCourseTitle(String value) {
        this.courseTitle = value;
    }

    /**
     * Gets the value of the numberOfParts property.
     *
     */
    public int getNumberOfParts() {
        return numberOfParts;
    }

    /**
     * Sets the value of the numberOfParts property.
     *
     */
    public void setNumberOfParts(int value) {
        this.numberOfParts = value;
    }

    /**
     * Gets the value of the courseCode property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the courseCode property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCourseCode().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseCode }
     *
     *
     */
    public List<CourseCode> getCourseCode() {
        if (courseCode == null) {
            courseCode = new ArrayList<CourseCode>();
        }
        return this.courseCode;
    }

    /**
     * Gets the value of the courseLevel property.
     *
     * @return
     *     possible object is
     *     {@link CourseLevelType }
     *
     */
    public CourseLevelType getCourseLevel() {
        return courseLevel;
    }

    /**
     * Sets the value of the courseLevel property.
     *
     * @param value
     *     allowed object is
     *     {@link CourseLevelType }
     *
     */
    public void setCourseLevel(CourseLevelType value) {
        this.courseLevel = value;
    }

    /**
     * Gets the value of the courseLevelCharacteristics property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the courseLevelCharacteristics property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCourseLevelCharacteristics().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CourseLevelCharacteristicItemType }
     *
     *
     */
    public List<CourseLevelCharacteristicItemType> getCourseLevelCharacteristics() {
        if (courseLevelCharacteristics == null) {
            courseLevelCharacteristics = new ArrayList<CourseLevelCharacteristicItemType>();
        }
        return this.courseLevelCharacteristics;
    }

    /**
     * Gets the value of the gradesOffered property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gradesOffered property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGradesOffered().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GradeLevelType }
     *
     *
     */
    public List<GradeLevelType> getGradesOffered() {
        if (gradesOffered == null) {
            gradesOffered = new ArrayList<GradeLevelType>();
        }
        return this.gradesOffered;
    }

    /**
     * Gets the value of the subjectArea property.
     *
     * @return
     *     possible object is
     *     {@link AcademicSubjectType }
     *
     */
    public AcademicSubjectType getSubjectArea() {
        return subjectArea;
    }

    /**
     * Sets the value of the subjectArea property.
     *
     * @param value
     *     allowed object is
     *     {@link AcademicSubjectType }
     *
     */
    public void setSubjectArea(AcademicSubjectType value) {
        this.subjectArea = value;
    }

    /**
     * Gets the value of the courseDescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCourseDescription() {
        return courseDescription;
    }

    /**
     * Sets the value of the courseDescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCourseDescription(String value) {
        this.courseDescription = value;
    }

    /**
     * Gets the value of the dateCourseAdopted property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateCourseAdopted() {
        return dateCourseAdopted;
    }

    /**
     * Sets the value of the dateCourseAdopted property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateCourseAdopted(String value) {
        this.dateCourseAdopted = value;
    }

    /**
     * Gets the value of the highSchoolCourseRequirement property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isHighSchoolCourseRequirement() {
        return highSchoolCourseRequirement;
    }

    /**
     * Sets the value of the highSchoolCourseRequirement property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setHighSchoolCourseRequirement(Boolean value) {
        this.highSchoolCourseRequirement = value;
    }

    /**
     * Gets the value of the courseGPAApplicability property.
     *
     * @return
     *     possible object is
     *     {@link CourseGPAApplicabilityType }
     *
     */
    public CourseGPAApplicabilityType getCourseGPAApplicability() {
        return courseGPAApplicability;
    }

    /**
     * Sets the value of the courseGPAApplicability property.
     *
     * @param value
     *     allowed object is
     *     {@link CourseGPAApplicabilityType }
     *
     */
    public void setCourseGPAApplicability(CourseGPAApplicabilityType value) {
        this.courseGPAApplicability = value;
    }

    /**
     * Gets the value of the courseDefinedBy property.
     *
     * @return
     *     possible object is
     *     {@link CourseDefinedByType }
     *
     */
    public CourseDefinedByType getCourseDefinedBy() {
        return courseDefinedBy;
    }

    /**
     * Sets the value of the courseDefinedBy property.
     *
     * @param value
     *     allowed object is
     *     {@link CourseDefinedByType }
     *
     */
    public void setCourseDefinedBy(CourseDefinedByType value) {
        this.courseDefinedBy = value;
    }

    /**
     * Gets the value of the minimumAvailableCredit property.
     *
     * @return
     *     possible object is
     *     {@link Credits }
     *
     */
    public Credits getMinimumAvailableCredit() {
        return minimumAvailableCredit;
    }

    /**
     * Sets the value of the minimumAvailableCredit property.
     *
     * @param value
     *     allowed object is
     *     {@link Credits }
     *
     */
    public void setMinimumAvailableCredit(Credits value) {
        this.minimumAvailableCredit = value;
    }

    /**
     * Gets the value of the maximumAvailableCredit property.
     *
     * @return
     *     possible object is
     *     {@link Credits }
     *
     */
    public Credits getMaximumAvailableCredit() {
        return maximumAvailableCredit;
    }

    /**
     * Sets the value of the maximumAvailableCredit property.
     *
     * @param value
     *     allowed object is
     *     {@link Credits }
     *
     */
    public void setMaximumAvailableCredit(Credits value) {
        this.maximumAvailableCredit = value;
    }

    /**
     * Gets the value of the careerPathway property.
     *
     * @return
     *     possible object is
     *     {@link CareerPathwayType }
     *
     */
    public CareerPathwayType getCareerPathway() {
        return careerPathway;
    }

    /**
     * Sets the value of the careerPathway property.
     *
     * @param value
     *     allowed object is
     *     {@link CareerPathwayType }
     *
     */
    public void setCareerPathway(CareerPathwayType value) {
        this.careerPathway = value;
    }

    /**
     * Gets the value of the schoolId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * Sets the value of the schoolId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSchoolId(String value) {
        this.schoolId = value;
    }

}
