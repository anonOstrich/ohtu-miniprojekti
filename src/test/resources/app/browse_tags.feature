Feature: Browsing for tags is possible
    
    Scenario: user can browse existing bookmarks
        Given option "2" is selected
        When input "LT" is entered
        And app is created
        Then system response will contain "cucumber"
        And system response will contain "tutorial"
        And system response will contain "podcast"
        And system response will contain "deleted"
        And system response will contain "antarctic"
