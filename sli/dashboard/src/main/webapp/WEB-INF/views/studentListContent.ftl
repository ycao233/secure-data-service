<#-- The body of List of Student view -->
<#-- Used by: StudentListContentController.studentListContent() -->
<#-- required objects in the model map: 
     viewConfig: the view config for the list of students. Should be ViewConfig object
     assessments: contains assessment information for the list of students. Should be AssessmentResolver object
     students: contains the list of students to be displayed. Should be StudentResolver object. 
     constants: the Constants util class
  -->

<table id="studentList"> 

<#-- draw header -->
<#-- TODO: Handle programatically -->
<tr class="listHeader">
<#list viewConfig.getDisplaySet() as displaySet>
  <th colspan=${displaySet.getField()?size}}>${displaySet.getDisplayName()}</th>
</#list>
</tr>
<tr class="listHeader">
<#list viewConfig.getDisplaySet() as displaySet>
  <#list displaySet.getField() as field>
    <th>${field.getDisplayName()}</th>
  </#list>
</#list>
</tr>

<#-- draw body --> 
<#list students.list() as student>

<tr class="listRow">
<#list viewConfig.getDisplaySet() as displaySet>
  <#list displaySet.getField() as field>
    <td class="${field.getValue()}">
      
      <#-- student info -->
      <#if field.getType() = constants.FIELD_TYPE_STUDENT_INFO>
        ${students.get(field, student)}
        
      <#-- assessment results -->
      <#elseif field.getType() = constants.FIELD_TYPE_ASSESSMENT>
        <#if field.getVisual()?? && (field.getVisual()?length > 0)>
          <#include "widget/" + field.getVisual() + ".ftl">
        <#else>
          ${assessments.get(field, student)}
        </#if>
        
      <#else>
        <#-- No resolver found. Report an error. -->
        Cannot resolve this field. Check your view config xml.
      </#if>
    </td>
  </#list>
</#list>
</tr>

</#list>

</table>


