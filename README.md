# spring-react-Application
This will be a dashboard application that uses spring boot with an Hsql imbedded database and and React for front-end. 

Technical documentation for the IPL Application. 
By Nuradin Ahmed

This is an ideal architecture that scales up by having multiple instance of the spring boot applications that reads and writes to a database and the spring boot app exposes the APIs the gets consumed by the react App - JavaScript front end. Typically this useful in a scenarios where you are reading and writing to a database otherwise you don’t need it and you can use an imbedded database in springbok app - HSQL that gets start up when the application opens up and the react for the front end.  
This will be the design chosed since this application takes downloaded data from kaggle and its read only and no writes. We will use the HSQLDB imbedded into spring boot. 
 
 

Dependencies need: 
1.	Spring Web
2.	Spring Dev Tools
3.	Spring Batch: 
a.	I am going to need spring batch to parse the CVS file(that I manually download from kaggle) and get the data and save it to the database. The hsql imbedded database. 
4.	HSQL – as my database and its only read-only no write –so this database will suffice. 
5.	Spring Data JPA: 
a.	Because I need to save it to the database. Persist data in SQL stores with java persistence API using spring data and hibernate. 
b.	
Steps building the application
(1-Creating the business class – per spring.io batch guide https://spring.io/guides/gs/batch-processing/) – creating an entity: creating the member variables from the csv data and their getters and setters methods. 
1-	manually load the cvs file from kaggle into the resources folder in the application.
a.	When we start the application- we want the application to parse the data (using the spring batch)` in the resources folder and load it into the embedded database – hsql. Then our web service APIs will serve it to us in the form of an API. 
i.	Write the code to implement above steps – 1. While using spring batch:
1.	How does spring batch work:
a.	There is a user job → then you’ve steps -> each step has three phases:reader, processor and writer.
2.	In our case, we only need one step to take the input, processes it and then write it to database.
a.	1.Create processor, then 2. Reader and then 3. Writer, then 4. User job with one step 5 – which is comprised of these things. 

 

(1-Creating the business class – per spring.io batch guide https://spring.io/guides/gs/batch-processing/)
A.	Creating member variables 
B.	Creating its getters and setters methods. 
(2-Creating an intermediate processer – per spring.io batch guide- a common paradigm in batch processing is to ingest data, transform it, and then pipe it out somewhere else- in our case into the hsql database. Here we are going to write simple transformer)


The API Design
 the API will beased on the UI - meaning if the UI changes the api will have to be considered for change too while the other approach is building API based on exposing the 
  data back-end more like complete REST API and would stay the same if the UI changes but would require more calling from UI.
  ![image](https://user-images.githubusercontent.com/8764914/115947335-a0545900-a4cf-11eb-9dcf-3da15a1822c0.png)
