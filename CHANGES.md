jackrabbit-datastore-migration Changelog
====================

## jackrabbit-datastore-migration 0.1.5

Fixed Issues:

* Optimize catch-up migration when most data already exists at the target. Thanks to [Peter Centgraf](https://github.com/pcentgraf) for the PR (#4)!

## jackrabbit-datastore-migration 0.1.4

Fixed Issues:

* Use the public method, `CachingDataStore#getBackend()`.

## jackrabbit-datastore-migration 0.1.3

Fixed Issues:

* Support an option to use `Backend` and source `DataIdentifier` directly.

## jackrabbit-datastore-migration 0.1.2

Fixed Issues:

* Allow to pass system properties; documentation about `ds.digest.algorithm` system property.

## jackrabbit-datastore-migration 0.1.1

Fixed Issues:

* Upgraded Jackrabbit Dependency to 2.16.3 and added com.jcraft:jsch dependency for SFTP use cases.
* Adding and improving VFS/SFTP and S3 DataStore configuration examples.

## jackrabbit-datastore-migration 0.1.0

Fixed Issues:

* Thread pool worker min/max counts are configurable.

## jackrabbit-datastore-migration 0.0.1

The initial release.
