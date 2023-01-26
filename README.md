# tax challenge

This project uses Quarkus, 

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

In order to demonstrate how application run as per code challenge, i.e. reading input file with basket items. please, run from the terminal:
```
./mvnw quarkus:test
```
where test/dev/prod are available quarkus execution profiles (supported by Eclipse microprofile as being a part of Quarkus microservices ecosystem).

Screenshot of successful run in a bash terminal is located under doc subfolder in a repo: RunningTestsBash_screen.png
Screenshot of successful run from inside Eclipse is located under doc subfolder in a repo: InvoicePrintOut_JUnit.png

A couple of diagrams can be found under doc: SalesTask_ER_23012023.png

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the jar file in the `/target` directory.

The application is now runnable using `java -jar target/<jarname>.jar`. Then Quarkus is supposed to be run in a server mode.
There was no intention to build it as a Native executable for the code challenge which is however one of the main concepts of Quarkus.
