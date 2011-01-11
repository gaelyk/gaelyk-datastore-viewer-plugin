/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovyx.gaelyk.plugins.datastore.viewer.category

import com.google.appengine.api.datastore.*

/**
 * Category for querying for datastore metadata.
 *
 * @author Benjamin Muschko
 */
class MetaDataCategory {
    static final DEFAULT_FETCH_OPTIONS = FetchOptions.Builder.withDefaults()

    /**
     * Gets datastore namespaces
     *
     * @param service Datastore service
     * @param options Fetch options
     * @return Entities
     */
    static List<Entity> getNamespaces(DatastoreService service, FetchOptions options = DEFAULT_FETCH_OPTIONS) {
        queryMetaData(service, options, Query.NAMESPACE_METADATA_KIND)
    }

    /**
     * Gets datastore namespaces. The closure lets you apply additional filters to your query.
     *
     * @param service Datastore service
     * @param options Fetch options
     * @param closure Closure
     * @return Entities
     */
    static List<Entity> getNamespaces(DatastoreService service, FetchOptions options = DEFAULT_FETCH_OPTIONS,
                                      Closure closure) {
        queryMetaData(service, options, Query.NAMESPACE_METADATA_KIND, closure)
    }

    /**
     * Gets datastore kinds.
     *
     * @param service Datastore service
     * @param options Fetch options
     * @return Entities
     */
    static List<Entity> getKinds(DatastoreService service, FetchOptions options = DEFAULT_FETCH_OPTIONS) {
        queryMetaData(service, options, Query.KIND_METADATA_KIND)
    }

    /**
     * Gets datastore kinds. The closure lets you apply additional filters to your query.
     *
     * @param service Datastore service
     * @param options Fetch options
     * @param closure Closure
     * @return Entities
     */
    static List<Entity> getKinds(DatastoreService service, FetchOptions options = DEFAULT_FETCH_OPTIONS,
                                 Closure closure) {
        queryMetaData(service, options, Query.KIND_METADATA_KIND, closure)
    }

    /**
     * Gets all datastore kinds and their properties.
     *
     * @param service Datastore service
     * @param options Fetch options
     * @return Entities
     */
    static List<Entity> getProperties(DatastoreService service, FetchOptions options = DEFAULT_FETCH_OPTIONS) {
        queryMetaData(service, options, Query.PROPERTY_METADATA_KIND)
    }

    /**
     * Gets all datastore kinds and their properties. The closure lets you apply additional filters to your query.
     *
     * @param service Datastore service
     * @param kind Kind
     * @param options Fetch options
     * @param closure Closure
     * @return Entities
     */
    static List<Entity> getProperties(DatastoreService service, FetchOptions options = DEFAULT_FETCH_OPTIONS,
                                      Closure closure) {
        queryMetaData(service, options, Query.PROPERTY_METADATA_KIND, closure)
    }

    /**
     * Gets datastore kind properties.
     *
     * @param service Datastore service
     * @param kind Kind
     * @param options Fetch options
     * @return Entities
     */
    static List<Entity> getProperties(DatastoreService service, String kind, FetchOptions options = DEFAULT_FETCH_OPTIONS) {
        Query query = new Query(Query.PROPERTY_METADATA_KIND)
        query.setAncestor(createKindKey(kind))
        PreparedQuery preparedQuery = service.prepare(query)
        preparedQuery.asList(options)
    }

    /**
     * Gets datastore kind properties. The closure lets you apply additional filters to your query.
     *
     * @param service Datastore service
     * @param kind Kind
     * @param options Fetch options
     * @param closure Closure
     * @return Entities
     */
    static List<Entity> getProperties(DatastoreService service, String kind, FetchOptions options = DEFAULT_FETCH_OPTIONS,
                                      Closure closure) {
        Query query = new Query(Query.PROPERTY_METADATA_KIND)
        query.setAncestor(createKindKey(kind))
        closure(query)
        PreparedQuery preparedQuery = service.prepare(query)
        preparedQuery.asList(options)
    }

    /**
     * Gets datastore kind property.
     *
     * @param service Datastore service
     * @param kind Kind
     * @param property Property
     * @return Entity
     */
    static Entity getProperty(DatastoreService service, String kind, String property) {
        Query query = new Query(Query.PROPERTY_METADATA_KIND)
        query.setAncestor(createPropertyKey(kind, property))
        PreparedQuery preparedQuery = service.prepare(query)
        preparedQuery.asSingleEntity()
    }

    /**
     * Queries for meta data.
     *
     * @param service Datastore service
     * @param options Fetch options
     * @param metaDataQuery Query
     * @return Entities
     */
    private static List<Entity> queryMetaData(DatastoreService service, FetchOptions options, String metaDataQuery) {
        Query query = new Query(metaDataQuery)
        PreparedQuery preparedQuery = service.prepare(query)
        preparedQuery.asList(options)
    }

    /**
     * Queries for meta data.
     *
     * @param service Datastore service
     * @param options Fetch options
     * @param metaDataQuery Query
     * @param closure Closure
     * @return Entities
     */
    private static List<Entity> queryMetaData(DatastoreService service, FetchOptions options, String metaDataQuery, Closure closure) {
        Query query = new Query(metaDataQuery)
        closure(query)
        PreparedQuery preparedQuery = service.prepare(query)
        preparedQuery.asList(options)
    }

    /**
     * Creates kind key
     *
     * @param kind Kind
     * @return Key
     */
    private static Key createKindKey(String kind) {
        KeyFactory.createKey(Query.KIND_METADATA_KIND, kind)
    }

    /**
     * Creates property key.
     *
     * @param kind Kind
     * @param property Property
     * @return Key
     */
    private static Key createPropertyKey(String kind, String property) {
        KeyFactory.createKey(createKindKey(kind), Query.PROPERTY_METADATA_KIND, property)
    }
}

