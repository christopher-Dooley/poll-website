# poll-website

## Build
The command to build a new jar is:
- .\gradlew.bat clean bootJar if you're on windows
- ./gradlew clean bootJar if you're on a sensible OS


## Run
You can run it with `gradle bootRun` from this directory or `java -jar [path to pollwebsite.jar]`.
Or you can download and use the release that can be found in the github repo.


### API Documentation
When you run the app, api documentation can be found at:
- http://localhost:3000/v3/api-docs in json format
- http://localhost:3000/swagger-ui/index.html with a swagger UI

From the swagger UI you can submit polls and votes. 

You will need to create a poll before the voting UI on localhost:3000 will fully work. 