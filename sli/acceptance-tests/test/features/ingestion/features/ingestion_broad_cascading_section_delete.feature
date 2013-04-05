@RALLY_US5180
Feature: Safe Deletion and Cascading Deletion

Background: I have a landing zone route configured
Given I am using local data store

Scenario: Delete Program with cascade
    Given I am using preconfigured Ingestion Landing Zone for "Midgar-Daybreak"
    And the "Midgar" tenant db is empty
    When the data from "test/features/ingestion/test_data/delete_fixture_data/" is imported
	Then there exist "1" "section" records like below in "Midgar" tenant. And I save this query as "section"
	|field                                                           |value                                                |
	|_id                                                             |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id          |
	Then there exist "1" "student" records like below in "Midgar" tenant. And I save this query as "student"
	|field                                                           |value                                                |
	|section._id                                                  |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id             |
	Then there exist "24" "studentGradebookEntry" records like below in "Midgar" tenant. And I save this query as "studentGradebookEntry"
	|field                                                           |value                                                |
	|body.sectionId                                                  |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id          |
	Then there exist "1" "section" records like below in "Midgar" tenant. And I save this query as "gradebookEntry"
	|field                                                           |value                                                |
	|gradebookEntry.body.sectionId                                   |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id          |
	Then there exist "1" "yearlyTranscript" records like below in "Midgar" tenant. And I save this query as "grade"
	|field                                                           |value                                                |
	|grade.body.sectionId                                            |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id          |
	Then there exist "1" "section" records like below in "Midgar" tenant. And I save this query as "teacherSectionAssociation"
	|field                                                           |value                                                |
	|teacherSectionAssociation.body.sectionId                        |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id          |	
	Then there exist "1" "section" records like below in "Midgar" tenant. And I save this query as "studentSectionAssociation"
	|field                                                           |value                                                |
	|studentSectionAssociation.body.sectionId                        |48fcd5a76d5c21262d625718ee26aca9ec0c058f_id          |	
	And I save the collection counts in "Midgar" tenant
    And I post "BroadSectionDelete.zip" file as the payload of the ingestion job
   When zip file is scp to ingestion landing zone
    And a batch job for file "BroadSectionDelete.zip" is completed in database
	And I should see "records considered for processing: 1" in the resulting batch job file
	And I should see "records ingested successfully: 0" in the resulting batch job file
	And I should see "records deleted successfully: 1" in the resulting batch job file
	And I should see "records failed processing: 0" in the resulting batch job file
	And I should see "records not considered for processing: 0" in the resulting batch job file
	And I should see "All records processed successfully." in the resulting batch job file
	And I should see "Processed 1 records." in the resulting batch job file
    And I should not see an error log file created
	And I should not see a warning log file created
    And I re-execute saved query "section" to get "0" records
    And I re-execute saved query "student" to get "0" records
    And I re-execute saved query "studentGradebookEntry" to get "0" records
    And I re-execute saved query "gradebookEntry" to get "0" records
    And I re-execute saved query "grade" to get "0" records
    And I re-execute saved query "teacherSectionAssociation" to get "0" records
    And I re-execute saved query "studentSectionAssociation" to get "0" records
    And I see that collections counts have changed as follows in tenant "Midgar"
        | collection |delta|
        |section     |   -1|
        |studentGradebookEntry| -24|
        |gradebookEntry| -24|
        |grade         | -1|
        |teacherSectionAssociation| -1|
        |studentSectionAssociation| -1|
        |studentCompetency |   -5|
	And I should not see "48fcd5a76d5c21262d625718ee26aca9ec0c058f_id" in the "Midgar" database  