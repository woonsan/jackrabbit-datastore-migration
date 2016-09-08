# jackrabbit-datastore-migration

[![Build Status](https://travis-ci.org/woonsan/jackrabbit-datastore-migration.svg?branch=develop)](https://travis-ci.org/woonsan/jackrabbit-datastore-migration)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/woonsan/jackrabbit-datastore-migration/develop/LICENSE)

```DataStore``` migration tool for Apache Jackrabbit.
Apache Jackrabbit supports various ```DataStore``` components such as ```FileDataStore```, ```DbDataStore```,
```S3DataStore``` and ```VFSDataStore```.
See http://woonsanko.blogspot.com/2016/08/cant-we-store-huge-amount-of-binary.html for detail.

This migration tool lets you to migrate one ```DataStore``` to another ```DataStore```. e.g, from ```DbDataStore``` to ```VFSDataStore```

This tool has been implemented with **Spring Boot** and **Spring Batch**.
So, it follows most of the standard conventions established by those projects.

## How to Build

        $ mvn clean package

## How to Run

### Example: FileDataStore to FileDataStore

        $ java -jar target/jackrabbit-datastore-migration-x.x.x.jar \
                --spring.config.location=config/example-fs-to-fs.yaml

You can also run `mvn spring-boot:run` from the source folder instead like the following:

        & mvn spring-boot:run \
                -Drun.jvmArguments="-Dspring.config.location=config/example-fs-to-fs.yaml"

### Example: DbDataStore to VFSDataStore

Assuming you have JDBC Driver jar file in ```lib/``` directory,

        $ java -Dloader.path="lib/" \
                -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
                --spring.config.location=config/example-db-to-vfs.yaml

So, in the preceding, you should copy a proper JDBC driver jar file to the folder path
specified by ```-Dloader.path```.

You can also specify ```-Dloader.path``` to somewhere else like the following example:

        $ java -Dloader.path=/home/tester/.m2/repository/mysql/mysql-connector-java/5.1.38/ \
                -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
                --spring.config.location=config/example-db-to-vfs.yaml

## Configurations

You can specify **source** ```DataStore``` and **target** ```DataStore``` and others (such as logging) in a
YAML configuration file specified by ```--spring.config.location``` command line argument.

Some example configuration files are located in [config/](config/) folder:
- ```FileDataStore``` to ```FileDataStore``` example: [config/example-fs-to-fs.yaml](config/example-fs-to-fs.yaml)
- ```DbDataStore``` to ```VFSDataStore``` example: [config/example-db-to-vfs.yaml](config/example-db-to-vfs.yaml)

## Example Outputs

### From a FileDataStore (14 entries only) to another FileDataStore

```
    $ java -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar --spring.config.location=config/example-fs-to-fs.yaml
    
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v1.4.0.RELEASE)
    
    2016-09-08 18:36:57.993  INFO 1722 --- [           main] c.g.w.j.migration.datastore.Application  : Starting Application v0.0.1-SNAPSHOT on HAL195 with PID 1722 (/home/tester/jackrabbit-datastore-migration/target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar started by tester in /home/tester/jackrabbit-datastore-migration)
    2016-09-08 18:36:57.998  INFO 1722 --- [           main] c.g.w.j.migration.datastore.Application  : No active profile set, falling back to default profiles: default
    2016-09-08 18:36:58.982  WARN 1722 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-09-08 18:36:58.995  WARN 1722 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-09-08 18:36:59.417  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.FileDataStore@7b49cea0
    2016-09-08 18:36:59.423  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.FileDataStore@6e0e048a
    2016-09-08 18:37:01.865  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0fcb420367c1415ac722065ecadb917d35758cd9' (7.1%)
    2016-09-08 18:37:01.868  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0fcea759ac5378704213ec85f4a67cf702a517f0' (14.3%)
    2016-09-08 18:37:01.869  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '5fb221ab548ad86b1f324c2ce23973d05175b92b' (21.4%)
    2016-09-08 18:37:01.873  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '62ce9355b4ddf28ba5061561c01d50c4b6262dba' (28.6%)
    2016-09-08 18:37:01.875  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '8cc9c05b3c147761f580c25741875631aeaff8f2' (35.7%)
    2016-09-08 18:37:01.876  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '913c53f084b036a0f676a89e222bf7b8b29fdf48' (42.9%)
    2016-09-08 18:37:01.878  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ba1b6df8c9a7a1243006f2b626c13c42d4e559e9' (50.0%)
    2016-09-08 18:37:01.879  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'cd267bdcd9fa90ccebe1636d631e86b91c64937b' (57.1%)
    2016-09-08 18:37:01.882  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'cfaaf8f224237672310f73a80ae907cb81997f29' (64.3%)
    2016-09-08 18:37:01.884  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'db09d6bc05b45330f8fa159e5aa9e47ab86a7eb0' (71.4%)
    2016-09-08 18:37:01.896  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'e3990b222c710da3f962bdb63c6e5ffe608e7af0' (78.6%)
    2016-09-08 18:37:01.897  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ec8c87f3cf4b5f31559b085a65dc746efe7135e7' (85.7%)
    2016-09-08 18:37:01.899  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'f6d06fe2998122cb589a3f909eb3a6955ae217f4' (92.9%)
    2016-09-08 18:37:01.901  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff54ee9c0d51bd542dd6d4e2f5aa310d8e498857' (100.0%)
    2016-09-08 18:37:01.920  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.SourceDataStoreConfiguration@15aab8c6[homeDir=target/test-classes,className=org.apache.jackrabbit.core.data.FileDataStore,params={minRecordLength=1024, path=target/test-classes/repository/datastore}]
    2016-09-08 18:37:01.936  INFO 1722 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.TargetDataStoreConfiguration@33990a0c[homeDir=target/storage-fs,className=org.apache.jackrabbit.core.data.FileDataStore,params={minRecordLength=1024, path=target/storage-fs/repository/datastore}]
    2016-09-08 18:37:01.944  INFO 1722 --- [           main] .w.j.m.d.b.MigrationJobExecutionReporter : 
    ===============================================================================================================
    Execution Summary:
    ---------------------------------------------------------------------------------------------------------------
    Total: 14, Processed: 14, Read Success: 14, Read Fail: 0, Write Success: 14, Write Fail: 0, Duration: 175ms
    ---------------------------------------------------------------------------------------------------------------
    Details (in CSV format):
    ---------------------------------------------------------------------------------------------------------------
    SEQ,ID,READ,WRITE,ERROR
    1,0fcb420367c1415ac722065ecadb917d35758cd9,true,true,
    2,0fcea759ac5378704213ec85f4a67cf702a517f0,true,true,
    3,5fb221ab548ad86b1f324c2ce23973d05175b92b,true,true,
    4,62ce9355b4ddf28ba5061561c01d50c4b6262dba,true,true,
    5,8cc9c05b3c147761f580c25741875631aeaff8f2,true,true,
    6,913c53f084b036a0f676a89e222bf7b8b29fdf48,true,true,
    7,ba1b6df8c9a7a1243006f2b626c13c42d4e559e9,true,true,
    8,cd267bdcd9fa90ccebe1636d631e86b91c64937b,true,true,
    9,cfaaf8f224237672310f73a80ae907cb81997f29,true,true,
    10,db09d6bc05b45330f8fa159e5aa9e47ab86a7eb0,true,true,
    11,e3990b222c710da3f962bdb63c6e5ffe608e7af0,true,true,
    12,ec8c87f3cf4b5f31559b085a65dc746efe7135e7,true,true,
    13,f6d06fe2998122cb589a3f909eb3a6955ae217f4,true,true,
    14,ff54ee9c0d51bd542dd6d4e2f5aa310d8e498857,true,true,
    ===============================================================================================================
    
    2016-09-08 18:37:01.949  INFO 1722 --- [           main] c.g.w.j.migration.datastore.Application  : Started Application in 4.65 seconds (JVM running for 5.243)
```

### From a DbDataStore (22383 entries) to a VFSDataStore

```
    $ java -Dloader.path="lib/" -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar --spring.config.location=config/example-db-to-vfs.yaml
    
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v1.4.0.RELEASE)
    ...
    2016-09-08 19:06:58.505  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '000082f676bf6ed3a39debd6b656287efa6687b6' (0.0%)
    2016-09-08 19:06:58.537  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '00030e05db40739611fcd06d1af26cc7a6afd5b0' (0.0%)
    2016-09-08 19:06:58.542  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '00076b2f5e4e43245928accbcc90fcf738121652' (0.0%)
    2016-09-08 19:06:58.604  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '000891e2099f6bcb88084535b1add06bf97feed5' (0.0%)
    2016-09-08 19:06:58.625  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0009d177d67c92a56dcca2a467524ef3561084c6' (0.0%)
    2016-09-08 19:06:58.633  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '001012ce928686c48e7fea33b660e76d21367462' (0.0%)
    2016-09-08 19:06:58.641  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0010fd2eb5dff6cd54084c91d42aef2ba2b42264' (0.0%)
    2016-09-08 19:06:58.661  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '001163381e05e453229b28531ea8d44ea0f956d5' (0.0%)
    2016-09-08 19:06:58.665  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0011b1d2e654bbf4539824513a6f097e9837f978' (0.0%)
    2016-09-08 19:06:58.687  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '001b0ae11d3dbdd5938f77aa0bb669c95c933c19' (0.0%)
    ...
    2016-09-08 19:16:08.275  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffd85ff7b2e037026218850b383b06976944c285' (99.9%)
    2016-09-08 19:16:08.278  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffd97833d975586cc932129a5ffc3c434fb0bd94' (100.0%)
    2016-09-08 19:16:08.285  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffda9d7987fb11130380163d2a043415a51bf476' (100.0%)
    2016-09-08 19:16:08.319  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffe85703d367039a17aa0d14aeeb4b5ba4638c4e' (100.0%)
    2016-09-08 19:16:08.330  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffecbcbf96bbb9f8922b212fcc6851d08efab9bc' (100.0%)
    2016-09-08 19:16:08.429  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffef93494c59c5f714ade55203d168a12748dff9' (100.0%)
    2016-09-08 19:16:08.482  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff039500ade1477f141477b6d2cc58cf3d42286' (100.0%)
    2016-09-08 19:16:08.502  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff1382586eaed1d04b5d0b99c21f4370935bbab' (100.0%)
    2016-09-08 19:16:08.505  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff27664418122d7c2514b2cb7ee6716dbe358c1' (100.0%)
    2016-09-08 19:16:08.537  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff46cc1879fed7e50ed48cb70147dfe2cc31764' (100.0%)
    2016-09-08 19:16:08.543  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff7e180f51341e207b4e66e2e700a0c9ce187bd' (100.0%)
    2016-09-08 19:16:08.556  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff8b902c59f0c310ff53952d86b17d383805355' (100.0%)
    2016-09-08 19:16:08.575  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fffc167ef45efdc9b3bea7ed3953fea8ccdb294f' (100.0%)
    2016-09-08 19:16:08.593  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.TargetDataStoreConfiguration@131276c2[homeDir=/home/tester/jackrabbit-datastore-migration/target/storage-vfs-visitmt,className=org.apache.jackrabbit.vfs.ext.ds.VFSDataStore,params={minRecordLength=1024, baseFolderUri=file:///home/tester/jackrabbit-datastore-migration/target/storage-vfs-visitmt/repository/datastore}]
    2016-09-08 19:16:08.594  INFO 1961 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.SourceDataStoreConfiguration@72b6cbcc[homeDir=target/storage-db,className=org.apache.jackrabbit.core.data.db.DbDataStore,params={schemaObjectPrefix=, tablePrefix=, url=jdbc:mysql://localhost:3306/hippodb?autoReconnect=true&characterEncoding=utf8, schemaCheckEnabled=false, maxConnections=5, databaseType=mysql, minRecordLength=1024, user=mtotadm, password=mtotadm, driver=com.mysql.jdbc.Driver, copyWhenReading=true}]
    2016-09-08 19:16:08.715  INFO 1961 --- [           main] .w.j.m.d.b.MigrationJobExecutionReporter : 
    ===============================================================================================================
    Execution Summary:
    ---------------------------------------------------------------------------------------------------------------
    Total: 22383, Processed: 22383, Read Success: 22383, Read Fail: 0, Write Success: 22383, Write Fail: 0, Duration: 551273ms
    ---------------------------------------------------------------------------------------------------------------
    Details (in CSV format):
    ---------------------------------------------------------------------------------------------------------------
    SEQ,ID,READ,WRITE,ERROR
    1,000082f676bf6ed3a39debd6b656287efa6687b6,true,true,
    2,00030e05db40739611fcd06d1af26cc7a6afd5b0,true,true,
    3,00076b2f5e4e43245928accbcc90fcf738121652,true,true,
    4,000891e2099f6bcb88084535b1add06bf97feed5,true,true,
    5,0009d177d67c92a56dcca2a467524ef3561084c6,true,true,
    6,001012ce928686c48e7fea33b660e76d21367462,true,true,
    7,0010fd2eb5dff6cd54084c91d42aef2ba2b42264,true,true,
    8,001163381e05e453229b28531ea8d44ea0f956d5,true,true,
    9,0011b1d2e654bbf4539824513a6f097e9837f978,true,true,
    10,001b0ae11d3dbdd5938f77aa0bb669c95c933c19,true,true,
    ...
    22377,fff039500ade1477f141477b6d2cc58cf3d42286,true,true,
    22378,fff1382586eaed1d04b5d0b99c21f4370935bbab,true,true,
    22379,fff27664418122d7c2514b2cb7ee6716dbe358c1,true,true,
    22380,fff46cc1879fed7e50ed48cb70147dfe2cc31764,true,true,
    22381,fff7e180f51341e207b4e66e2e700a0c9ce187bd,true,true,
    22382,fff8b902c59f0c310ff53952d86b17d383805355,true,true,
    22383,fffc167ef45efdc9b3bea7ed3953fea8ccdb294f,true,true,
    ===============================================================================================================
    
    2016-09-08 19:16:08.776  INFO 1961 --- [           main] c.g.w.j.migration.datastore.Application  : Started Application in 556.455 seconds (JVM running for 557.158)
```

