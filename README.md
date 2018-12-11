Travis CI (build status): [![Build Status](https://travis-ci.org/anonOstrich/ohtu-miniprojekti.svg?branch=master)](https://travis-ci.org/anonOstrich/ohtu-miniprojekti)


Codecov (test coverage): [![codecov](https://codecov.io/gh/anonOstrich/ohtu-miniprojekti/branch/master/graph/badge.svg)](https://codecov.io/gh/anonOstrich/ohtu-miniprojekti)

# Bookmark Library (ohtu-miniprojekti)
Ohjelmistotuotantokurssin miniprojekti


[Product & sprint backlog](https://docs.google.com/spreadsheets/d/1JXfi_ZUgXKkfvnegcy7C4KUzVWvdBlr7t2WN6icuReA/edit#gid=0)


## Definition of done
- Feature is functioning
- User story's acceptance criteria is tested
- There is enough testing:
  - Tests test relevant things
  - There is 80% row coverage
  - Tests pass
- Code is reviewed at least by one other developer (pull request)
- Documentation is adequate (javadoc +  relevant diagrams)

## Installation

### For using the program

* What you will need: 
  * [Java](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
* Click 'releases' on this main page of the project (to the left of the green 'Clone or download' button)
* Download the .jar file of the latest release 
* Open the command prompt, navigate to the folder where you downloaded the .jar file, and execute the command `java -jar bookmark_libraryV3.jar` (replace the filename with the correct one if you have renamed it)
* The program will begin in your command prompt. If this is the first time you launch it, a few example bookmarks are inserted into the databse. 

### For using the program if you also want the ability to run the tests, modify source code etc.

* What you will need: 
  * [Java](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) for running the program
  * [Gradle](https://gradle.org/install/)

* Clone / download this repository by clicking on the green 'Clone or download' button in the upper right corner
* Navigate to the downloaded folder 
* In the command prompt / shell CLI execute the command `gradle shadowJar`
* An executable .jar file has been created in build/libs/
* To run the program, execute `java -jar build/libs/ohtu-miniprojekti-all.jar`

## User guide
**Fuction to perform:**

**1. Add a new bookmark**
  * First, choose which type of bookmark to add:
    * Book (B)
    * Blog (BG)
    * Other (O)
  * When a bookmark type is chosen, the application asks the relevant information concerning that type (eg. author, url,  description)
    * Tags are given as lists, where list items are separated by a comma(,)
  * Each bookmark is given unique ID that is later used to identify bookmarks for editing and removal
  
**2. List all existing bookmarks or tags**
  * List bookmarks in selected order. Options for ordering are by title or when the bookmark was added to the program. In the latter case you can also choose ascending or descending order.
  * List all the tags saved in the program. Number of tagged bookmarks is also listed.
  
**3. Search for bookmarks**
  * Search for bookmarks by giving a string of letters to be searched either from title, description or tags. 
  
**4. Edit a bookmark**
  * Choose a bookmark to be edited by giving its unique ID
  * Give the field to be edited
  * Give new value for the field 
  
**5. Deleting a bookmark**
  * Give the unique ID of the bookmark to be deleted
  
**0. Exit the application**
