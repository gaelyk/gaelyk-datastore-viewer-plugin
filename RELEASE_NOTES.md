### Version 0.4 (November 19, 2011)

* Improved UI look & feel by using [Twitter Boostrap](http://twitter.github.com/bootstrap/) - [Issue 2](https://github.com/bmuschko/gaelyk-datastore-viewer-plugin/issues#issue/2).
* Using GaelykBindings annotation in service. *Note:* The annotation was introduced with Gaelyk 1.0. Your projects require to run Gaelyk >= 1.0.
* Datatype Enum (`DatastorePropertyType.groovy`) became Java class. GAE had an issue with curried closures. Also [GROOVY-4641](http://jira.codehaus.org/browse/GROOVY-4641) was really annoying.

### Version 0.3 (May 18, 2011)

* Datastore metadata query category got moved into Gaelyk core with 0.7. Depending on Gaelyk core for metadata queries.
* Upgraded depencency to Gaelyk 0.7, Groovy 1.8.0 and App Engine 1.5.0.
* _Note:_ This release requires you to run your Gaelyk application with Gaelyk >= 0.7.

### Version 0.2 (February 21, 2011)

* Added more documentation.
* Upgraded depencency to Gaelyk 0.6.1.
* UI now displays links for viewing entity details if data type is a datastore key.
* Provide insert/update functionality - [Issue 1](https://github.com/bmuschko/gaelyk-datastore-viewer-plugin/issues#issue/1).
* Entity operations are now based on the selected namespace - [Issue 4](https://github.com/bmuschko/gaelyk-datastore-viewer-plugin/issues#issue/4).

### Version 0.1 (January 11, 2011)

* Initial release.