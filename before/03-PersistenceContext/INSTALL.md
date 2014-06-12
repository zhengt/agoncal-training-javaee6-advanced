# Java EE 6 Advanced Training - PersistenceContext

## Copy the Arquillian test that checks the persistence context is extended

Copy the `service` package to `cdbookstore/src/main/test/org/agoncal/training/javaee6adv`

## Change all the persistence context from extended to transactional

* New test should fail
* Diplaying and creating CDs from the UI should fail

## Get rid of lazy initialization exception by finding by id with relations

* Book and CD have relations with other entities
* Both BookBean.findById and CDBook.findById should use LEFT joins to get relations

## Deploy the application on JBoss application server


## Check the web application

http://localhost:8080/cdbookstore
