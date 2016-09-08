# jackrabbit-datastore-migration

Jackrabbit DataStore migration tool, implemented with Spring Batch / Spring Boot.

## How to Build

        $ mvn clean package

## How to Run

### FileDataStore to FileDataStore example

        $ java -jar target/jackrabbit-datastore-migration-x.x.x.jar --spring.config.location=config/example-fs-to-fs.yaml

### DbDataStore to VFSDataStore example

        $ java -jar target/jackrabbit-datastore-migration-x.x.x.jar --spring.config.location=config/example-db-to-vfs.yaml

