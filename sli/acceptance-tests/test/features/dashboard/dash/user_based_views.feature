Feature: User based view selection

As a SEA/LEA user, I want to be able to select different views in my dashboard
application, that will change the subset of information that is displayed.

Background:
  Given I have an open web browser
Given that dashboard has been authorized for all ed orgs

@wip
Scenario: Check user has multiple views available
  When I navigate to the Dashboard home page
  When I select "Sunset School District 4526" and click go
 And I was redirected to the "Simple" IDP Login page
 When I submit the credentials "cgray" "cgray1234" for the "Simple" login page
  When I select <edOrg> "Daybreak School District 4529"
    And I select <school> "Daybreak Central High"
    And I select <course> "American Literature"
    And I select <section> "Sec 145"
  Then I should have a dropdown selector named "viewSelect"
    And I should have a selectable view named "Middle School ELA View"
    And I should have a selectable view named "College Ready ELA View"

@wip
Scenario: Views are filtered based on student grades
  When I navigate to the Dashboard home page
  When I select "Sunset School District 4526" and click go
 And I was redirected to the "Simple" IDP Login page
 When I submit the credentials "cgray" "cgray1234" for the "Simple" login page
  When I select <edOrg> "Daybreak School District 4529"
    And I select <school> "Daybreak Central High"
    And I select <course> "Writing about Government"
    And I select <section> "Sec 923"
  Then I should only see one view named "Middle School ELA View"

@wip
Scenario: Check changing view changes table headings
  When I navigate to the Dashboard home page
  When I select "Sunset School District 4526" and click go
 And I was redirected to the "Simple" IDP Login page
 When I submit the credentials "cgray" "cgray1234" for the "Simple" login page
  When I select <edOrg> "Daybreak School District 4529"
    And I select <school> "Daybreak Central High"
    And I select <course> "American Literature"
    And I select <section> "Sec 145"
  When I select view "Middle School ELA View"
  Then I should see a table heading "StateTest Reading"
    And I should see a table heading "StateTest Writing (most recent)"
  When I select view "College Ready ELA View"
  Then I should see a table heading "Reading Test Scores (Highest)"
    And I should see a table heading "Writing Test Scores (Highest)"
    And I should see a table heading "AP Eng. Exam Scores"

 @RALLY_US200
Scenario: Different users have different views defined
  When I navigate to the Dashboard home page
  When I select "Illinois Daybreak School District 4529" and click go
 And I was redirected to the "Simple" IDP Login page
 When I submit the credentials "rbraverman" "rbraverman1234" for the "Simple" login page
  When I select <edOrg> "Daybreak School District 4529"
    And I select <school> "South Daybreak Elementary"
    And I select <course> "1st Grade Homeroom"
    And I select <section> "Mrs. Braverman's Homeroom #38"
  Then I should only see one view named "Default View"