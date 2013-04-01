@RALLY_US5180
Feature: Safe Deletion and Cascading Deletion

Background: I have a landing zone route configured
Given I am using local data store

Scenario: Delete Attendance Event with cascade
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
    And a query on attendance of for studentId "908404e876dd56458385667fa383509035cd4312_id", schoolYear "2001-2002" and date "2001-09-13" on the "Midgar" tenant has a count of "1"
	And I should see child entities of entityType "attendanceEvent" with id "94de66549a6b58f96463ff0d59b34817aa1fead6_id" in the "Midgar" database	
    
    And I post "BroadAttendanceEventDelete.zip" file as the payload of the ingestion job
	When zip file is scp to ingestion landing zone
    And a batch job for file "BroadAttendanceEventDelete.zip" is completed in database
    And a batch job log has been created
	And I should see "Processed 1 records." in the resulting batch job file
    And I should not see an error log file created
	And I should not see a warning log file created
	And I should not see "94de66549a6b58f96463ff0d59b34817aa1fead6_id" in the "Midgar" database
	And a query on attendance of for studentId "908404e876dd56458385667fa383509035cd4312_id", schoolYear "2001-2002" and date "2001-09-13" on the "Midgar" tenant has a count of "0"
	And I should not see any entity mandatorily referring to "94de66549a6b58f96463ff0d59b34817aa1fead6_id" in the "Midgar" database
	And I should see entities optionally referring to "94de66549a6b58f96463ff0d59b34817aa1fead6_id" be updated in the "Midgar" database