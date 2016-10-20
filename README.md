# jackrabbit-datastore-migration

[![Build Status](https://travis-ci.org/woonsan/jackrabbit-datastore-migration.svg?branch=develop)](https://travis-ci.org/woonsan/jackrabbit-datastore-migration)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/woonsan/jackrabbit-datastore-migration/develop/LICENSE)

```DataStore``` migration tool for Apache Jackrabbit.
Apache Jackrabbit supports various ```DataStore``` components such as ```FileDataStore```, ```DbDataStore```,
```S3DataStore``` and ```VFSDataStore```.
See http://woonsanko.blogspot.com/2016/08/cant-we-store-huge-amount-of-binary.html for detail.

This migration tool lets you to migrate one ```DataStore``` to another ```DataStore```. e.g, from ```DbDataStore``` to ```VFSDataStore```.
Also my experience with this tool is shared in http://woonsanko.blogspot.com/2016/10/playing-with-apache-jackrabbit.html.

This tool has been implemented with **Spring Boot** and **Spring Batch**.
So, it follows most of the standard conventions established by those projects.

## How to Build

First, download the latest source release form https://github.com/woonsan/jackrabbit-datastore-migration/releases.
And, extract the compressed file and build the project in the uncompressed folder.

```sh
$ mvn clean package
```

The build will generate single jar artifict under the ```target/``` folder. e.g., ```target/jackrabbit-datastore-migration-x.x.x.jar```. You can also copy this jar to somewhere else to run the tool there. This single jar file contains everything to run the Spring Batch/Boot-based migration tool application that can be started using ```java -jar``` command.


## How to Run

### Example: FileDataStore to FileDataStore

```sh
$ java -jar target/jackrabbit-datastore-migration-x.x.x.jar \
    --spring.config.location=config/example-fs-to-fs.yaml
```

You can also run `mvn spring-boot:run` from the source folder instead like the following:

```sh
$ mvn spring-boot:run \
    -Drun.jvmArguments="-Dspring.config.location=config/example-fs-to-fs.yaml"
```

### Example: DbDataStore to VFSDataStore

Assuming you have JDBC Driver jar file in ```lib/``` directory,

```sh
$ java -Dloader.path="lib/" \
    -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
    --spring.config.location=config/example-db-to-vfs.yaml
```

So, in the preceding, you should copy a proper JDBC driver jar file to the folder path
specified by ```-Dloader.path```.

You can also specify ```-Dloader.path``` to somewhere else like the following example:

```sh
$ java -Dloader.path=/home/tester/.m2/repository/mysql/mysql-connector-java/5.1.38/ \
    -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
    --spring.config.location=config/example-db-to-vfs.yaml
```

## Configurations

You can specify **source** ```DataStore``` and **target** ```DataStore``` and others (such as logging) in a
YAML configuration file specified by ```--spring.config.location``` command line argument.

Some example configuration files are located in [config/](config/) folder:
- ```FileDataStore``` to ```FileDataStore``` example: [config/example-fs-to-fs.yaml](config/example-fs-to-fs.yaml)
- ```DbDataStore``` to ```VFSDataStore``` example: [config/example-db-to-vfs.yaml](config/example-db-to-vfs.yaml)

*Note*: The file/folder layout of the date record files of ```VFSDataStore``` is actually the same as ```FileDataStore```.
Therefore, you might want to use ```FileDataStore``` as target first and move the file/folder data to external SFTP or WebDAV system afterward if possible.

Here's a simplistic example configuration to migrate from ```DbDataStore``` to ```VFSDataStore```:

```yaml
source:
    dataStore:
        homeDir: 'target/storage-db'
        className: 'org.apache.jackrabbit.core.data.db.DbDataStore'
        params:
            url: 'jdbc:mysql://localhost:3306/repodb?autoReconnect=true&characterEncoding=utf8'
            user: 'repouser'
            password: 'repouserpass'
            driver: 'com.mysql.jdbc.Driver'
            databaseType: 'mysql'
            minRecordLength: '1024'
            maxConnections: '10'
            copyWhenReading: 'true'
            tablePrefix: ''
            schemaObjectPrefix: ''
            schemaCheckEnabled: 'false'

target:
    dataStore:
        homeDir: '/home/tester/jackrabbit-datastore-migration/target/storage-vfs-visitmt'
        className: 'org.apache.jackrabbit.vfs.ext.ds.VFSDataStore'
        params:
            asyncUploadLimit: '0'
            baseFolderUri: 'file://${target.dataStore.homeDir}/vfsds'
            minRecordLength: '1024'
```

Basically, each ```DataStore``` configuration (for ```source``` and ```target``` in the migration job) is equivalent
to the configuration in the ```repository.xml```.
Therefore, for the details, please refer to the Apache Jackrabbit DataStore configurations.

## Example Outputs

### From a FileDataStore (14 entries only) to another FileDataStore

```sh
$ java -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar --spring.config.location=config/example-fs-to-fs.yaml

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.4.0.RELEASE)

2016-10-17 23:35:13.239  INFO 5158 --- [           main] c.g.w.j.migration.datastore.Application  : Starting Application v0.0.1-SNAPSHOT on HAL195 with PID 5158 (/home/tester/workspace/jackrabbit-datastore-migration/target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar started by woonsanko in /home/tester/workspace/jackrabbit-datastore-migration)
2016-10-17 23:35:13.244  INFO 5158 --- [           main] c.g.w.j.migration.datastore.Application  : No active profile set, falling back to default profiles: default
2016-10-17 23:35:14.399  WARN 5158 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-17 23:35:14.415  WARN 5158 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-17 23:35:14.906  INFO 5158 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.FileDataStore@7b49cea0
2016-10-17 23:35:14.915  INFO 5158 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.FileDataStore@6e0e048a
2016-10-17 23:35:17.865  INFO 5158 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0fcea759ac5378704213ec85f4a67cf702a517f0' (14.3%ile)
2016-10-17 23:35:17.865  INFO 5158 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0fcb420367c1415ac722065ecadb917d35758cd9' (7.1%ile)
2016-10-17 23:35:17.865  INFO 5158 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '5fb221ab548ad86b1f324c2ce23973d05175b92b' (21.4%ile)
2016-10-17 23:35:17.868  INFO 5158 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ba1b6df8c9a7a1243006f2b626c13c42d4e559e9' (28.6%ile)
2016-10-17 23:35:17.868  INFO 5158 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'cd267bdcd9fa90ccebe1636d631e86b91c64937b' (35.7%ile)
2016-10-17 23:35:17.869  INFO 5158 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '913c53f084b036a0f676a89e222bf7b8b29fdf48' (42.9%ile)
2016-10-17 23:35:17.870  INFO 5158 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '62ce9355b4ddf28ba5061561c01d50c4b6262dba' (50.0%ile)
2016-10-17 23:35:17.871  INFO 5158 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'db09d6bc05b45330f8fa159e5aa9e47ab86a7eb0' (57.1%ile)
2016-10-17 23:35:17.872  INFO 5158 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ec8c87f3cf4b5f31559b085a65dc746efe7135e7' (64.3%ile)
2016-10-17 23:35:17.873  INFO 5158 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '8cc9c05b3c147761f580c25741875631aeaff8f2' (71.4%ile)
2016-10-17 23:35:17.874  INFO 5158 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff54ee9c0d51bd542dd6d4e2f5aa310d8e498857' (78.6%ile)
2016-10-17 23:35:17.878  INFO 5158 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'cfaaf8f224237672310f73a80ae907cb81997f29' (85.7%ile)
2016-10-17 23:35:17.883  INFO 5158 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'f6d06fe2998122cb589a3f909eb3a6955ae217f4' (92.9%ile)
2016-10-17 23:35:17.884  INFO 5158 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'e3990b222c710da3f962bdb63c6e5ffe608e7af0' (100.0%ile)
2016-10-17 23:35:17.911  INFO 5158 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.SourceDataStoreConfiguration@702b8b12[homeDir=target/test-classes,className=org.apache.jackrabbit.core.data.FileDataStore,params={minRecordLength=1024, path=target/test-classes/repository/datastore}]
2016-10-17 23:35:17.926  INFO 5158 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.TargetDataStoreConfiguration@22e357dc[homeDir=target/storage-fs,className=org.apache.jackrabbit.core.data.FileDataStore,params={minRecordLength=1024, path=target/storage-fs/fsds}]
2016-10-17 23:35:17.934  INFO 5158 --- [           main] .w.j.m.d.b.MigrationJobExecutionReporter : 
===============================================================================================================
Execution Summary:
---------------------------------------------------------------------------------------------------------------
Total: 14, Processed: 14, Read Success: 14, Read Fail: 0, Write Success: 14, Write Fail: 0, Duration: 163ms
---------------------------------------------------------------------------------------------------------------
Details (in CSV format):
---------------------------------------------------------------------------------------------------------------
SEQ,ID,READ,WRITE,SIZE,ERROR
1,0fcb420367c1415ac722065ecadb917d35758cd9,true,true,3133,
2,0fcea759ac5378704213ec85f4a67cf702a517f0,true,true,4846,
3,5fb221ab548ad86b1f324c2ce23973d05175b92b,true,true,4849,
4,62ce9355b4ddf28ba5061561c01d50c4b6262dba,true,true,75187,
5,8cc9c05b3c147761f580c25741875631aeaff8f2,true,true,7001,
6,913c53f084b036a0f676a89e222bf7b8b29fdf48,true,true,2964,
7,ba1b6df8c9a7a1243006f2b626c13c42d4e559e9,true,true,2546,
8,cd267bdcd9fa90ccebe1636d631e86b91c64937b,true,true,5911,
9,cfaaf8f224237672310f73a80ae907cb81997f29,true,true,27034,
10,db09d6bc05b45330f8fa159e5aa9e47ab86a7eb0,true,true,9425,
11,e3990b222c710da3f962bdb63c6e5ffe608e7af0,true,true,219962,
12,ec8c87f3cf4b5f31559b085a65dc746efe7135e7,true,true,5332,
13,f6d06fe2998122cb589a3f909eb3a6955ae217f4,true,true,24743,
14,ff54ee9c0d51bd542dd6d4e2f5aa310d8e498857,true,true,3616,
===============================================================================================================

2016-10-17 23:35:17.940  INFO 5158 --- [           main] c.g.w.j.migration.datastore.Application  : Started Application in 5.607 seconds (JVM running for 6.388)
```

### From a DbDataStore (22383 entries) to a VFSDataStore

```sh
$ java -Dloader.path=/home/tester/.m2/repository/mysql/mysql-connector-java/5.1.38/ \
    -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar \
    --spring.config.location=config/example-db-to-vfs-vmt.yaml

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.4.0.RELEASE)

2016-10-17 22:17:28.023  INFO 5003 --- [           main] c.g.w.j.migration.datastore.Application  : Starting Application v0.0.1-SNAPSHOT on HAL195 with PID 5003 (/home/tester/workspace/jackrabbit-datastore-migration/target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar started by woonsanko in /home/tester/workspace/jackrabbit-datastore-migration)
2016-10-17 22:17:28.026  INFO 5003 --- [           main] c.g.w.j.migration.datastore.Application  : No active profile set, falling back to default profiles: default
2016-10-17 22:17:29.493  WARN 5003 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-17 22:17:29.516  WARN 5003 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
2016-10-17 22:17:30.037  INFO 5003 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.db.DbDataStore@1c3a4799
2016-10-17 22:17:30.350  INFO 5003 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.vfs.ext.ds.VFSDataStore@59e5ddf
2016-10-17 22:17:34.886  INFO 5003 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '000082f676bf6ed3a39debd6b656287efa6687b6' (0.0%ile)
2016-10-17 22:17:34.886  INFO 5003 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '00076b2f5e4e43245928accbcc90fcf738121652' (0.0%ile)
2016-10-17 22:17:34.897  INFO 5003 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0026c0e0318cc770345f212d708581bd02b10f27' (0.0%ile)
2016-10-17 22:17:34.926  INFO 5003 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0009d177d67c92a56dcca2a467524ef3561084c6' (0.0%ile)
2016-10-17 22:17:34.950  INFO 5003 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0030de903bbb0a5f2b651415251187fcc7b0c79a' (0.0%ile)
2016-10-17 22:17:34.957  INFO 5003 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '00030e05db40739611fcd06d1af26cc7a6afd5b0' (0.0%ile)
2016-10-17 22:17:34.960  INFO 5003 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '001012ce928686c48e7fea33b660e76d21367462' (0.0%ile)
2016-10-17 22:17:34.972  INFO 5003 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '003823ccea8b9e4abe2ed5884e9c58f9df891f72' (0.0%ile)
2016-10-17 22:17:34.976  INFO 5003 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0010fd2eb5dff6cd54084c91d42aef2ba2b42264' (0.0%ile)
2016-10-17 22:17:35.026  INFO 5003 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '000891e2099f6bcb88084535b1add06bf97feed5' (0.0%ile)
2016-10-17 22:17:35.036  INFO 5003 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '003c93df9055fd3fadfe98df3d9e86ae2d5449b5' (0.0%ile)
2016-10-17 22:17:35.040  INFO 5003 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '002f593de8b0fc326ac86db2adb10364550d29a0' (0.1%ile)
2016-10-17 22:17:35.049  INFO 5003 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0040af52b010fc5d93eeb6288f6357255b2f63dc' (0.1%ile)
2016-10-17 22:17:35.054  INFO 5003 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0021efa8391bcea7c824dbb4155d4fa821ce04ee' (0.1%ile)
2016-10-17 22:17:35.060  INFO 5003 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0033269b0a26ae5d93ed1469038b94d2d67653e7' (0.1%ile)
...
2016-10-17 23:14:38.603  INFO 5071 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff943de3d6649162ba7d34f290fed84e4e8a4cda' (99.9%ile)
2016-10-17 23:14:38.716  INFO 5071 --- [ taskExecutor-9] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffba67bfd3fceb9ef76551112da2c03b7805e4ad' (99.9%ile)
2016-10-17 23:14:39.349  INFO 5071 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff94493c6c36319913b97af296a9f81d28d5ab4a' (99.9%ile)
2016-10-17 23:14:39.920  INFO 5071 --- [ taskExecutor-9] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffbde94e71826ce7ee05766e05e7681d08641735' (99.9%ile)
2016-10-17 23:14:43.990  INFO 5071 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffcfa3ecd2f828a2f4882d63431dc68e30a919fd' (99.9%ile)
2016-10-17 23:14:44.014  INFO 5071 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff9a91aeee1b8dc1b68dac0ce110f7db3ebf8405' (99.9%ile)
2016-10-17 23:14:44.100  INFO 5071 --- [ taskExecutor-6] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffef93494c59c5f714ade55203d168a12748dff9' (100.0%ile)
2016-10-17 23:14:44.102  INFO 5071 --- [ taskExecutor-9] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffc04af8ff17dd7f232fd0f2b563723c745cc284' (100.0%ile)
2016-10-17 23:14:44.126  INFO 5071 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffd3f39dd1dc87f2835bc904cad49b7ceba6beee' (100.0%ile)
2016-10-17 23:14:44.128  INFO 5071 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff9bb593b9c96773e97efc52d392882b20bafe4e' (100.0%ile)
2016-10-17 23:14:44.164  INFO 5071 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff7e180f51341e207b4e66e2e700a0c9ce187bd' (100.0%ile)
2016-10-17 23:14:44.225  INFO 5071 --- [ taskExecutor-6] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff039500ade1477f141477b6d2cc58cf3d42286' (100.0%ile)
2016-10-17 23:14:44.324  INFO 5071 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff8b902c59f0c310ff53952d86b17d383805355' (100.0%ile)
2016-10-17 23:14:44.432  INFO 5071 --- [ taskExecutor-6] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff1382586eaed1d04b5d0b99c21f4370935bbab' (100.0%ile)
2016-10-17 23:14:44.483  INFO 5071 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fffc167ef45efdc9b3bea7ed3953fea8ccdb294f' (100.0%ile)
2016-10-17 23:14:44.484  INFO 5071 --- [ taskExecutor-6] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff27664418122d7c2514b2cb7ee6716dbe358c1' (100.0%ile)
2016-10-17 23:14:44.570  INFO 5071 --- [ taskExecutor-6] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff46cc1879fed7e50ed48cb70147dfe2cc31764' (100.0%ile)
2016-10-17 23:14:44.646  INFO 5071 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffd51a9289fd2a8595414a68c903b15e955b7d89' (100.0%ile)
2016-10-17 23:14:44.659  INFO 5071 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.SourceDataStoreConfiguration@73e22a3d[homeDir=target/storage-db,className=org.apache.jackrabbit.core.data.db.DbDataStore,params={schemaObjectPrefix=, tablePrefix=, url=jdbc:mysql://localhost:3306/hippodb?autoReconnect=true&characterEncoding=utf8, schemaCheckEnabled=false, maxConnections=10, databaseType=mysql, minRecordLength=1024, user=mtotadm, password=mtotadm, driver=com.mysql.jdbc.Driver, copyWhenReading=true}]
2016-10-17 23:14:44.684  INFO 5071 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.TargetDataStoreConfiguration@47faa49c[homeDir=/home/tester/workspace/jackrabbit-datastore-migration/target/storage-vfs-visitmt,className=org.apache.jackrabbit.vfs.ext.ds.VFSDataStore,params={minRecordLength=1024, baseFolderUri=file:///home/tester/workspace/hippo-davstore-demo/wsgidav/davshare/vfsds, asyncUploadLimit=0}]
2016-10-17 23:14:44.785  INFO 5071 --- [           main] .w.j.m.d.b.MigrationJobExecutionReporter : 
===============================================================================================================
Execution Summary:
---------------------------------------------------------------------------------------------------------------
Total: 22383, Processed: 22383, Read Success: 22383, Read Fail: 0, Write Success: 22383, Write Fail: 0, Duration: 1887607ms
---------------------------------------------------------------------------------------------------------------
Details (in CSV format):
---------------------------------------------------------------------------------------------------------------
SEQ,ID,READ,WRITE,SIZE,ERROR
1,000082f676bf6ed3a39debd6b656287efa6687b6,true,true,54395,
2,00030e05db40739611fcd06d1af26cc7a6afd5b0,true,true,626789,
3,00076b2f5e4e43245928accbcc90fcf738121652,true,true,4097,
4,000891e2099f6bcb88084535b1add06bf97feed5,true,true,1296577,
5,0009d177d67c92a56dcca2a467524ef3561084c6,true,true,409725,
6,001012ce928686c48e7fea33b660e76d21367462,true,true,89381,
7,0010fd2eb5dff6cd54084c91d42aef2ba2b42264,true,true,72165,
8,001163381e05e453229b28531ea8d44ea0f956d5,true,true,369261,
9,0011b1d2e654bbf4539824513a6f097e9837f978,true,true,2443,
10,001b0ae11d3dbdd5938f77aa0bb669c95c933c19,true,true,547448,
...
22372,ffd97833d975586cc932129a5ffc3c434fb0bd94,true,true,4323,
22373,ffda9d7987fb11130380163d2a043415a51bf476,true,true,72053,
22374,ffe85703d367039a17aa0d14aeeb4b5ba4638c4e,true,true,1181076,
22375,ffecbcbf96bbb9f8922b212fcc6851d08efab9bc,true,true,213411,
22376,ffef93494c59c5f714ade55203d168a12748dff9,true,true,2234107,
22377,fff039500ade1477f141477b6d2cc58cf3d42286,true,true,780131,
22378,fff1382586eaed1d04b5d0b99c21f4370935bbab,true,true,649037,
22379,fff27664418122d7c2514b2cb7ee6716dbe358c1,true,true,1290,
22380,fff46cc1879fed7e50ed48cb70147dfe2cc31764,true,true,482463,
22381,fff7e180f51341e207b4e66e2e700a0c9ce187bd,true,true,32558,
22382,fff8b902c59f0c310ff53952d86b17d383805355,true,true,258272,
22383,fffc167ef45efdc9b3bea7ed3953fea8ccdb294f,true,true,518903,
===============================================================================================================

2016-10-17 23:14:44.820  INFO 5071 --- [           main] c.g.w.j.migration.datastore.Application  : Started Application in 1892.767 seconds (JVM running for 1893.449)
```

