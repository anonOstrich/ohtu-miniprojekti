Feature: Searching for bookmarks by tag is possible
    
    Scenario: User can find correct bookmarks with an existing tag
        Given option "3" is selected
        When input "TA" is entered
        When input "podcast" is entered
        And app is created
        Then system response will contain "Antarctic weekly"
        And system response will contain "Cucumber Experience"

    Scenario: User searching with the tag option won't find tags with non existing tag names
        Given option "3" is selected
        When input "TA" is entered
        When input "videos" is entered
        And app is created
        Then system response will contain "No tags with that title were found."
        And system response will not contain "Cucumber Experience"
        And system response will not contain "Cucumber tutorial"
        And system response will not contain "DeleteCast"
        And system response will not contain "Antarctic weekly"

    Scenario: User searching with a tag will only find the bookmarks associated with that tag
        Given option "3" is selected
        When input "TA" is entered
        When input "cucumber" is entered
        And app is created
        Then system response will not contain "DeleteCast"
        And system response will not contain "Antarctic weekly"
        And system response will contain "Cucumber Experience"
        And system response will contain "Cucumber tutorial"

