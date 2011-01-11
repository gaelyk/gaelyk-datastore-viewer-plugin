# Gaelyk datastore viewer plugin

The plugin implements a reusable administration interface to browse the entities that have been created by the application
leveraging [metadata queries](http://code.google.com/appengine/docs/java/datastore/metadataqueries.html) introduced with
the Google App Engine SDK 1.4.0. This plugin is intended for local development. If you want to deploy the plugin with your
application to the cloud make sure you secure the access to the plugin!

## Installation

To use the plugin in your Gaelyk application extract the distribution into your project directory. You should end up
with multiple extra files in your `war` directory as well as its subdirectories and a plugin descriptor named `datastoreViewerPlugin.groovy` under `war/WEB-INF/plugins`.

If you haven't created the file `war/WEB-INF/plugins.groovy` yet please create it and add the following line to it:

    install datastoreViewerPlugin

## Usage

After installing the plugin you will be able to access the datastore viewer via the URL [http://localhost:8080/data/browse](http://localhost:8080/data/browse).

### Lazy Variables

The plugin binds several lazy variables that are accessible in your views and controllers. First of all you can get information
about the plugin itself:

* `plugins.datastoreviewer.version`: the plugin version

### Metadata Queries

Querying for metadata looks similar to querying the datastore for entities using specific query types. To make it easier
to retrieve metadata provides a Groovy category named `MetaDataCategory`. You can query the datastore for namespaces, entity kinds,
its properties. The category directly applies to the `DatastoreService` object. By default the result list always returns
all entities. However, you can restrict your result set by defining `FetchOptions` as parameter. Moreover, you can confine
your result set by applying filters to your query. The following code snippets show the API by example. You can find more
detailed examples in the unit test class for this category named `MetaDataCategoryTest`. 

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
