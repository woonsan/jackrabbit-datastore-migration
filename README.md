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
    
    2016-09-08 22:33:53.729  INFO 2189 --- [           main] c.g.w.j.migration.datastore.Application  : Starting Application v0.0.1-SNAPSHOT on HAL195 with PID 2189 (/home/tester/jackrabbit-datastore-migration/target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar started by tester in /home/tester/jackrabbit-datastore-migration)
    2016-09-08 22:33:53.735  INFO 2189 --- [           main] c.g.w.j.migration.datastore.Application  : No active profile set, falling back to default profiles: default
    2016-09-08 22:33:54.692  WARN 2189 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-09-08 22:33:54.707  WARN 2189 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-09-08 22:33:55.158  INFO 2189 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.FileDataStore@7b49cea0
    2016-09-08 22:33:55.166  INFO 2189 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.FileDataStore@6e0e048a
    2016-09-08 22:33:57.664  INFO 2189 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0fcb420367c1415ac722065ecadb917d35758cd9' (7.1%ile)
    2016-09-08 22:33:57.664  INFO 2189 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '5fb221ab548ad86b1f324c2ce23973d05175b92b' (14.3%ile)
    2016-09-08 22:33:57.664  INFO 2189 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0fcea759ac5378704213ec85f4a67cf702a517f0' (21.4%ile)
    2016-09-08 22:33:57.666  INFO 2189 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'cd267bdcd9fa90ccebe1636d631e86b91c64937b' (28.6%ile)
    2016-09-08 22:33:57.667  INFO 2189 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '8cc9c05b3c147761f580c25741875631aeaff8f2' (35.7%ile)
    2016-09-08 22:33:57.668  INFO 2189 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '913c53f084b036a0f676a89e222bf7b8b29fdf48' (42.9%ile)
    2016-09-08 22:33:57.669  INFO 2189 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ec8c87f3cf4b5f31559b085a65dc746efe7135e7' (50.0%ile)
    2016-09-08 22:33:57.670  INFO 2189 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '62ce9355b4ddf28ba5061561c01d50c4b6262dba' (57.1%ile)
    2016-09-08 22:33:57.671  INFO 2189 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'db09d6bc05b45330f8fa159e5aa9e47ab86a7eb0' (64.3%ile)
    2016-09-08 22:33:57.672  INFO 2189 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'cfaaf8f224237672310f73a80ae907cb81997f29' (71.4%ile)
    2016-09-08 22:33:57.673  INFO 2189 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ba1b6df8c9a7a1243006f2b626c13c42d4e559e9' (78.6%ile)
    2016-09-08 22:33:57.674  INFO 2189 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff54ee9c0d51bd542dd6d4e2f5aa310d8e498857' (85.7%ile)
    2016-09-08 22:33:57.675  INFO 2189 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'f6d06fe2998122cb589a3f909eb3a6955ae217f4' (92.9%ile)
    2016-09-08 22:33:57.684  INFO 2189 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'e3990b222c710da3f962bdb63c6e5ffe608e7af0' (100.0%ile)
    2016-09-08 22:33:57.714  INFO 2189 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.SourceDataStoreConfiguration@702b8b12[homeDir=target/test-classes,className=org.apache.jackrabbit.core.data.FileDataStore,params={minRecordLength=1024, path=target/test-classes/repository/datastore}]
    2016-09-08 22:33:57.729  INFO 2189 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.TargetDataStoreConfiguration@22e357dc[homeDir=target/storage-fs,className=org.apache.jackrabbit.core.data.FileDataStore,params={minRecordLength=1024, path=target/storage-fs/repository/datastore}]
    2016-09-08 22:33:57.740  INFO 2189 --- [           main] .w.j.m.d.b.MigrationJobExecutionReporter : 
    ===============================================================================================================
    Execution Summary:
    ---------------------------------------------------------------------------------------------------------------
    Total: 14, Processed: 14, Read Success: 14, Read Fail: 0, Write Success: 14, Write Fail: 0, Duration: 181ms
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
    
    2016-09-08 22:33:57.748  INFO 2189 --- [           main] c.g.w.j.migration.datastore.Application  : Started Application in 4.766 seconds (JVM running for 5.368)
```

### From a DbDataStore (22383 entries) to a VFSDataStore

```
    $ java -Dloader.path="lib/" -jar target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar --spring.config.location=config/example-db-to-vfs-visitmt.yaml
    
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v1.4.0.RELEASE)
    
    2016-09-08 22:38:10.735  INFO 2196 --- [           main] c.g.w.j.migration.datastore.Application  : Starting Application v0.0.1-SNAPSHOT on HAL195 with PID 2196 (/home/tester/jackrabbit-datastore-migration/target/jackrabbit-datastore-migration-0.0.1-SNAPSHOT.jar started by tester in /home/tester/jackrabbit-datastore-migration)
    2016-09-08 22:38:10.739  INFO 2196 --- [           main] c.g.w.j.migration.datastore.Application  : No active profile set, falling back to default profiles: default
    2016-09-08 22:38:11.760  WARN 2196 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-09-08 22:38:11.775  WARN 2196 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
    2016-09-08 22:38:12.269  INFO 2196 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.core.data.db.DbDataStore@1f554b06
    2016-09-08 22:38:12.555  INFO 2196 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : dataStore initialized: org.apache.jackrabbit.vfs.ext.ds.VFSDataStore@646d64ab
    2016-09-08 22:38:16.298  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '000082f676bf6ed3a39debd6b656287efa6687b6' (0.0%ile)
    2016-09-08 22:38:16.300  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '00076b2f5e4e43245928accbcc90fcf738121652' (0.0%ile)
    2016-09-08 22:38:16.333  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0009d177d67c92a56dcca2a467524ef3561084c6' (0.0%ile)
    2016-09-08 22:38:16.339  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '002f593de8b0fc326ac86db2adb10364550d29a0' (0.0%ile)
    2016-09-08 22:38:16.345  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '00030e05db40739611fcd06d1af26cc7a6afd5b0' (0.0%ile)
    2016-09-08 22:38:16.348  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '001012ce928686c48e7fea33b660e76d21367462' (0.0%ile)
    2016-09-08 22:38:16.362  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0033269b0a26ae5d93ed1469038b94d2d67653e7' (0.0%ile)
    2016-09-08 22:38:16.364  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0021efa8391bcea7c824dbb4155d4fa821ce04ee' (0.0%ile)
    2016-09-08 22:38:16.365  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0010fd2eb5dff6cd54084c91d42aef2ba2b42264' (0.0%ile)
    2016-09-08 22:38:16.377  INFO 2196 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '000891e2099f6bcb88084535b1add06bf97feed5' (0.0%ile)
    2016-09-08 22:38:16.383  INFO 2196 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0026c0e0318cc770345f212d708581bd02b10f27' (0.0%ile)
    2016-09-08 22:38:16.385  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0033aa051d1275a17cf860e132f493d046554837' (0.1%ile)
    2016-09-08 22:38:16.393  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '001163381e05e453229b28531ea8d44ea0f956d5' (0.1%ile)
    2016-09-08 22:38:16.399  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0011b1d2e654bbf4539824513a6f097e9837f978' (0.1%ile)
    2016-09-08 22:38:16.403  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '003bbf82ecd019beb56d5552d0eca549fa283fbe' (0.1%ile)
    2016-09-08 22:38:16.404  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0039ba3a47d7719b5a70fe1f82b7855561a9bc82' (0.1%ile)
    2016-09-08 22:38:16.415  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0040af52b010fc5d93eeb6288f6357255b2f63dc' (0.1%ile)
    2016-09-08 22:38:16.417  INFO 2196 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '0030de903bbb0a5f2b651415251187fcc7b0c79a' (0.1%ile)
    2016-09-08 22:38:16.431  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '003e970e5553cb4bc8190f42eebbab2778fa793a' (0.1%ile)
    2016-09-08 22:38:16.438  INFO 2196 --- [ taskExecutor-4] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: '003823ccea8b9e4abe2ed5884e9c58f9df891f72' (0.1%ile)
    ...
    2016-09-08 22:45:26.277  INFO 2196 --- [ taskExecutor-5] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffcd7dd6a35b841366cd8abe6222a135628d99fc' (99.9%ile)
    2016-09-08 22:45:26.282  INFO 2196 --- [ taskExecutor-8] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffb2a9948e92cdb566d983641b8080b026002e6c' (99.9%ile)
    2016-09-08 22:45:26.287  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffecbcbf96bbb9f8922b212fcc6851d08efab9bc' (99.9%ile)
    2016-09-08 22:45:26.297  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff943de3d6649162ba7d34f290fed84e4e8a4cda' (99.9%ile)
    2016-09-08 22:45:26.333  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff94493c6c36319913b97af296a9f81d28d5ab4a' (99.9%ile)
    2016-09-08 22:45:26.375  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff9a91aeee1b8dc1b68dac0ce110f7db3ebf8405' (99.9%ile)
    2016-09-08 22:45:26.378  INFO 2196 --- [ taskExecutor-1] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ff9bb593b9c96773e97efc52d392882b20bafe4e' (99.9%ile)
    2016-09-08 22:45:26.388  INFO 2196 --- [ taskExecutor-8] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffb64e4b207e32fb2e29a9f88d1ffe3761ca390b' (99.9%ile)
    2016-09-08 22:45:26.396  INFO 2196 --- [ taskExecutor-8] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffb9d65eac4cbfaf15817199fdc0e24cf5f30790' (99.9%ile)
    2016-09-08 22:45:26.405  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff7e180f51341e207b4e66e2e700a0c9ce187bd' (99.9%ile)
    2016-09-08 22:45:26.422  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffef93494c59c5f714ade55203d168a12748dff9' (99.9%ile)
    2016-09-08 22:45:26.427  INFO 2196 --- [ taskExecutor-8] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffba67bfd3fceb9ef76551112da2c03b7805e4ad' (100.0%ile)
    2016-09-08 22:45:26.433  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff8b902c59f0c310ff53952d86b17d383805355' (100.0%ile)
    2016-09-08 22:45:26.442  INFO 2196 --- [ taskExecutor-5] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffcfa3ecd2f828a2f4882d63431dc68e30a919fd' (100.0%ile)
    2016-09-08 22:45:26.455  INFO 2196 --- [ taskExecutor-8] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffbde94e71826ce7ee05766e05e7681d08641735' (100.0%ile)
    2016-09-08 22:45:26.459  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff039500ade1477f141477b6d2cc58cf3d42286' (100.0%ile)
    2016-09-08 22:45:26.464  INFO 2196 --- [ taskExecutor-8] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffc04af8ff17dd7f232fd0f2b563723c745cc284' (100.0%ile)
    2016-09-08 22:45:26.466  INFO 2196 --- [ taskExecutor-5] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffd3f39dd1dc87f2835bc904cad49b7ceba6beee' (100.0%ile)
    2016-09-08 22:45:26.471  INFO 2196 --- [ taskExecutor-3] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fffc167ef45efdc9b3bea7ed3953fea8ccdb294f' (100.0%ile)
    2016-09-08 22:45:26.488  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff1382586eaed1d04b5d0b99c21f4370935bbab' (100.0%ile)
    2016-09-08 22:45:26.491  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff27664418122d7c2514b2cb7ee6716dbe358c1' (100.0%ile)
    2016-09-08 22:45:26.508  INFO 2196 --- [ taskExecutor-2] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'fff46cc1879fed7e50ed48cb70147dfe2cc31764' (100.0%ile)
    2016-09-08 22:45:26.542  INFO 2196 --- [ taskExecutor-5] c.g.w.j.m.d.batch.DataRecordWriter       : Record migrated: 'ffd51a9289fd2a8595414a68c903b15e955b7d89' (100.0%ile)
    2016-09-08 22:45:26.564  INFO 2196 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.SourceDataStoreConfiguration@28f2a10f[homeDir=target/storage-db,className=org.apache.jackrabbit.core.data.db.DbDataStore,params={schemaObjectPrefix=, tablePrefix=, url=jdbc:mysql://localhost:3306/hippodb?autoReconnect=true&characterEncoding=utf8, schemaCheckEnabled=false, maxConnections=10, databaseType=mysql, minRecordLength=1024, user=mtotadm, password=mtotadm, driver=com.mysql.jdbc.Driver, copyWhenReading=true}]
    2016-09-08 22:45:26.594  INFO 2196 --- [           main] c.g.w.j.m.d.batch.DataStoreFactory       : DataStore closed: com.github.woonsan.jackrabbit.migration.datastore.batch.TargetDataStoreConfiguration@f736069[homeDir=/home/tester/jackrabbit-datastore-migration/target/storage-vfs-visitmt,className=org.apache.jackrabbit.vfs.ext.ds.VFSDataStore,params={minRecordLength=1024, baseFolderUri=file:///home/tester/jackrabbit-datastore-migration/target/storage-vfs-visitmt/repository/datastore}]
    2016-09-08 22:45:26.684  INFO 2196 --- [           main] .w.j.m.d.b.MigrationJobExecutionReporter : 
    ===============================================================================================================
    Execution Summary:
    ---------------------------------------------------------------------------------------------------------------
    Total: 22383, Processed: 22383, Read Success: 22383, Read Fail: 0, Write Success: 22383, Write Fail: 0, Duration: 431394ms
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
    22372,ffd97833d975586cc932129a5ffc3c434fb0bd94,true,true,
    22373,ffda9d7987fb11130380163d2a043415a51bf476,true,true,
    22374,ffe85703d367039a17aa0d14aeeb4b5ba4638c4e,true,true,
    22375,ffecbcbf96bbb9f8922b212fcc6851d08efab9bc,true,true,
    22376,ffef93494c59c5f714ade55203d168a12748dff9,true,true,
    22377,fff039500ade1477f141477b6d2cc58cf3d42286,true,true,
    22378,fff1382586eaed1d04b5d0b99c21f4370935bbab,true,true,
    22379,fff27664418122d7c2514b2cb7ee6716dbe358c1,true,true,
    22380,fff46cc1879fed7e50ed48cb70147dfe2cc31764,true,true,
    22381,fff7e180f51341e207b4e66e2e700a0c9ce187bd,true,true,
    22382,fff8b902c59f0c310ff53952d86b17d383805355,true,true,
    22383,fffc167ef45efdc9b3bea7ed3953fea8ccdb294f,true,true,
    ===============================================================================================================
    
    2016-09-08 22:45:26.751  INFO 2196 --- [           main] c.g.w.j.migration.datastore.Application  : Started Application in 436.676 seconds (JVM running for 437.377)
```

