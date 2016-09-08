# jackrabbit-datastore-migration

Jackrabbit DataStore migration tool, implemented with Spring Batch / Spring Boot.

## How to Build

        $ mvn clean package

## How to Run

### FileDataStore to FileDataStore example

        $ java -jar target/jackrabbit-datastore-migration-x.x.x.jar \
                --spring.config.location=config/example-fs-to-fs.yaml

        Or you can run `mvn spring-boot:run` from source folder instead like the following:

        & mvn spring-boot:run \
                -Drun.jvmArguments="-Dspring.config.location=config/example-fs-to-fs.yaml"

### DbDataStore to VFSDataStore example

Assuming you have JDBC Driver jar file in ```lib/``` directory,

        $ java -Dloader.path="lib/" \
                -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
                --spring.config.location=config/example-db-to-vfs.yaml

Or you can point to your local Maven repository directory (containing the jar) path as well:

        $ java -Dloader.path=/home/tester/.m2/repository/mysql/mysql-connector-java/5.1.38/ \
                -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
                --spring.config.location=config/example-db-to-vfs.yaml

