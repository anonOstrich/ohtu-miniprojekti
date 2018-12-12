Feature: Editing an existing bookmark is possible
    
    Scenario: user can edit the author of an existing bookmark
        Given option "4" is selected
        When input number 2 is entered
        And input "A" is entered
        And input "Charles Garfield" is entered
        And input "3" is entered
        And input "T" is entered
        And input "Cucumber tutorial" is entered
        And app is created
        Then system response will contain "Author:" -> "Charles Garfield"

    Scenario: user can edit the title of an existing bookmark
        Given option "4" is selected
        When input number 1 is entered
        And input "T" is entered
        And input "Testercast" is entered
        And input "3" is entered
        And input "T" is entered
        And input "Testercast" is entered
        And app is created
        Then system response will contain "Title:" -> "Testercast"
