Feature: Searching for existing bookmarks is possible
    

    Scenario: user can search by title and find an existing bookmark
        Given option "3" is selected
        When input "T" is entered
        And input "Cucumber tutorial" is entered
        And app is created
        Then system response will contain "ISBN:" before "12345-12345"


    Scenario: user can search by description and find an existing bookmark
        Given option "3" is selected
        When input "D" is entered
        And input "a tutorial for definition testing" is entered
        And app is created
        Then system response will contain "ISBN:" before "12345-12345"

    Scenario: user will only find bookmarks that match the search term
        Given option "3" is selected
        When input "T" is entered
        And input "nonExistentTitle" is entered
        And app is created
        Then system response will contain "No bookmarks found"
