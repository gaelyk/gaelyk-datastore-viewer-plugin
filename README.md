# Gaelyk datastore viewer plugin

The plugin implements a reusable administration interface to browse the entities that have been created by the application
leveraging [metadata queries](http://code.google.com/appengine/docs/java/datastore/metadataqueries.html) introduced with
the Google App Engine SDK 1.4.0. This plugin is intended for local development. If you want to deploy the plugin with your
application to the cloud make sure you secure the access to the plugin! You can have a look at the datastore viewer at
[http://gaelyk-datastore-viewer.appspot.com/](http://gaelyk-datastore-viewer.appspot.com/).

## Installation
### Since 2.0 (Gaelyk 2.0 SNAPSHOT only)

Add following line your `build.gradle` file `dependencies` configuration closure.

```
    compile 'org.gaelyk:gaelyk-datastore-viewer:2.0-SNAPSHOT'
```

### Before version 2.0

To use the plugin in your Gaelyk application extract the distribution into your project directory. You should end up
with multiple extra files in your `war` directory as well as its subdirectories and a plugin descriptor named `datastoreViewerPlugin.groovy` under `war/WEB-INF/plugins`.

If you haven't created the file `war/WEB-INF/plugins.groovy` yet please create it and add the following line to it:

    install datastoreViewerPlugin

## Usage

After installing the plugin you will be able to access the datastore viewer via the URL [http://localhost:8080/_ah/gaelyk-datastore-viewer/browse](http://localhost:8080/_ah/gaelyk-datastore-viewer/browse).

### Lazy Variables

The plugin binds several lazy variables that are accessible in your views and controllers. First of all you can get information
about the plugin itself:

* `plugins.datastoreviewer.version`: the plugin version

### Metadata Queries

Querying for metadata looks similar to querying the datastore for entities using specific query types. Gaelyk 0.7 provides
support to [retrieve metadata](http://gaelyk.appspot.com/tutorial/app-engine-shortcuts#metadata) via the `GaelykCategory`.
You can query the datastore for namespaces, entity kinds, its properties. The category directly applies to the `DatastoreService` object.
By default the result list always returns all entities. However, you can restrict your result set by defining `FetchOptions` as parameter. Moreover, you can confine
your result set by applying filters to your query. The following code snippets show the API by example.

#### Namespaces

App Engine provides support for [multi-tenancy](http://code.google.com/appengine/docs/java/multitenancy/multitenancy.html).
Each tenant belongs to a namespace that you can query for.

    // Get all namespaces
    List<Entity> allNamespaces = datastore.getNamespaces()

    // Get limited namespaces
    List<Entity> limitedNamespaces = datastore.getNamespaces(FetchOptions.Builder.withLimit(2))

    // Get filtered namespaces
    List<Entity> filteredNamespaces = datastore.getNamespaces() { query ->
                                         query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                         makeNamespaceKey("tenant2"))
                                      }

    // Get limited and filtered namespaces
    List<Entity> limitedAndFilteredNamespaces = datastore.getNamespaces(FetchOptions.Builder.withLimit(3)) { query ->
                                                   query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                                   makeNamespaceKey("tenant2"))
                                                }

#### Kinds

Each of your persisted entities has a specific kind. To be able to query for a kind at least one entity of that kind
has to exist in the datastore.

    // Get all kinds
    List<Entity> allKinds = datastore.getKinds()

    // Get limited kinds
    List<Entity> limitedKinds = datastore.getKinds(FetchOptions.Builder.withLimit(2))

    // Get filtered kinds
    List<Entity> filteredKinds = datastore.getKinds() { query ->
                                    query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                    makeKindKey("G"))
                                 }

    // Get limited and filtered kinds
    List<Entity> limitedAndFilteredKinds = datastore.getKinds(FetchOptions.Builder.withLimit(1)) { query ->
                                              query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                              makeKindKey("G"))
                                           }
#### Properties

Property queries return all properties associated with a kind. You can either query for properties of a specific kind
or for properties of all kinds.

    // Get all properties of all kinds
    List<Entity> allProperties = datastore.getProperties()

    // Get properties of kinds but limit the result set
    List<Entity> limitedProperties = datastore.getProperties(FetchOptions.Builder.withLimit(4))

    // Get properties of filtered kinds
    List<Entity> filteredProperties = datastore.getProperties() { query ->
                                         query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                         makeKindKey("G"))
                                      }

    // Get properties of filtered kinds but limit result set
    List<Entity> limitedAndFilteredProperties = datastore.getProperties(FetchOptions.Builder.withLimit(2)) { query ->
                                                   query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                                   makeKindKey("G"))
                                                }
    // Get properties for kind "User"
    List<Entity> entityProperties = datastore.getProperties("User")

    // Get limited properties for kind "User"
    List<Entity> limitedEntityProperties = datastore.getProperties("User", FetchOptions.Builder.withLimit(1))

    // Get filtered properties for kind "User"
    List<Entity> filteredProperties = datastore.getProperties("User") { query ->
                                         query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                         makePropertyKey("User", "n"))
                                      }

    // Get limited and filtered properties for kind "User"
    List<Entity> limitedAndFilteredProperties = datastore.getProperties("User", FetchOptions.Builder.withLimit(1)) { query ->
                                                   query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                                                   makePropertyKey("User", "d"))
                                                }

#### Property Representations

A property representation query returns additional information on a property of a kind.

    // Get specific property of kind "User"
    Entity property = datastore.getProperty("User", "name")

## Plugin Access

To make sure you protect access to the plugin's functionality you need to set appropriate security constraints.
Google App Engine provides authentication and authorization via the [Google Accounts API](http://code.google.com/appengine/docs/java/users/).
[Security constraints](http://code.google.com/appengine/docs/java/config/webxml.html#Security_and_Authentication) get
defined in your web.xml. The following example only grants access to registered developers (administrators):

    <security-constraint>
       <web-resource-collection>
          <url-pattern>/_ah/gaelyk-datastore-viewer/*</url-pattern>
       </web-resource-collection>
       <auth-constraint>
          <role-name>admin</role-name>
       </auth-constraint>
    </security-constraint>
