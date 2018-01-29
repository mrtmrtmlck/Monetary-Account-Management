# Monetary-Account-Management
Simple Monetary Account Management with Spring Framework

This is a Java Spring Boot web application. The application uses MySQL database. It is developed with Spring Framework.

There are 5 packages under src/main/java. These are:

com.ingenico.assessment.accountmanagement

This package has AccountManagementApplication class which is bootstrapping class of the application.

com.ingenico.assessment.accountmanagement.controller

This package has TransferController which is a RestController. This class contains API code.

There are 2 methods:

createAccount —> PostMapping method. It simply creates a new account with name and balance.

transferMoney —> PostMapping and Transactional method. It is also fully isolated to avoid dirty reads. Because this method does more than one commit - update two accounts - it is designed transactional. In case any error occurrence it rolls back.

com.ingenico.assessment.accountmanagement.model

This package has Account and Transfer classes.

Account is a JPA/Hibernate class. This class defines and represents Account table in the database. There are some constraints such as “balance” field value cannot be less than 0.

Transfer is a pure java class which is used for transferMoney method implementation by TransferController class. It has attributes for transferring money from one account to another. “senderId”, “receiverId”, “amount”.

com.ingenico.assessment.accountmanagement.repository

This package has AccountRepository interface which extends JpaRepository. Thus, it inherits JpaRepository methods such as save, findOne.

com.ingenico.assessment.accountmanagement.service

This package has TransferService interface and TransferServiceImpl class. TransferService interface has method signatures and TransferServiceImpl class implements them by using AccountRepository class. This service is used by TransferController.

There are 2 methods: 

saveAccount —> saving an account with name and balance.

getAccountById —> finding an existing account with id value. This method is used by transferMoney method in TransferController.


There is 1 package under src/test/java. This is:

com.ingenico.assessment.accountmanagement

This package has AccountManagementApplicationTests class. It is a test class as its name suggests.

It tests core functions and constraints. 

src/main/resources contains application.properties file. It is a spring properties file and it has some configurations such as dataSource username and password.

