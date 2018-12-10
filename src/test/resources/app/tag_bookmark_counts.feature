Feature: User can see the amount of bookmarks tagged with a particular tag
    
    Scenario: user can see the amount of bookmarks tagged with each existing tag.
        Given option "2" is selected
        When input "LT" is entered
        And app is created
        Then system response will contain "Total amount of bookmarks tagged: 2"
        And system response will contain "Total amount of bookmarks tagged: 1"
