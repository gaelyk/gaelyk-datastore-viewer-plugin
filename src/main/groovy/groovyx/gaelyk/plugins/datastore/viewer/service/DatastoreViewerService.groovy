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
package groovyx.gaelyk.plugins.datastore.viewer.service

import groovyx.gaelyk.logging.GroovyLogger
import groovyx.gaelyk.plugins.datastore.viewer.category.MetaDataCategory
import groovyx.gaelyk.plugins.datastore.viewer.data.EntityBrowsingData
import groovyx.gaelyk.plugins.datastore.viewer.pagination.EntityPage
import groovyx.gaelyk.plugins.datastore.viewer.pagination.Pagination
import com.google.appengine.api.datastore.*

/**
 * Datastore viewer service.
 *
 * @author Benjamin Muschko
 */
@Mixin(MetaDataCategory)
class DatastoreViewerService {
    def log = new GroovyLogger("groovyx.gaelyk.plugins.datastore.viewer.service.DatastoreViewerService")
    def datastore = DatastoreServiceFactory.datastoreService

    /**
     * Gets entity browsing data
     *
     * @parm kind Kind
     * @param offset Offset
     * @param limit Limit
     * @return Entity browsing data 
     */
    EntityBrowsingData getEntityBrowsingData(String kind, Integer offset, Integer limit) {
        EntityBrowsingData entityBrowsingData = new EntityBrowsingData()
        Pagination pagination = new Pagination(offset, limit)

        List<Entity> kinds = datastore.getKinds()
        kinds << new Entity("User", "User")
        entityBrowsingData.kinds = kinds

        // Use provided kind from parameter or first available kind
        def selectedKind = determineSelectedKind(kind, kinds)
        entityBrowsingData.selectedKind = selectedKind

        log.info "Getting entity data for kind '${selectedKind}' with offset ${pagination.offset} and limit ${pagination.limit}"

        if(selectedKind) {
            def kindProperties = datastore.getProperties(selectedKind)
            entityBrowsingData.kindProperties = kindProperties

            def query = new Query(selectedKind)
            PreparedQuery preparedQuery = datastore.prepare(query)
            List<Entity> kindRowsIncludingNext = preparedQuery.asList(FetchOptions.Builder.withOffset(pagination.offset).limit(pagination.limit + 1))
            entityBrowsingData.entityPage = new EntityPage(pagination.offset, pagination.limit, kindRowsIncludingNext)
        }

        entityBrowsingData
    }

    /**
     * Determines selected kind. If no kind was selected the first entity available entity kind
     * is chosen.
     *
     * @parm kind Kind
     * @param kinds Kinds
     * @return Selected kind
     */
    private determineSelectedKind(String kind, List<Entity> kinds) {
        kind ? kind : kinds.get(0)?.key?.name
    }

    /**
     * Deletes entity of given kind.
     *
     * @parm kind Kind
     * @param id ID
     */
    void deleteEntity(String kind, Long id) {
        log.info "Deleting entity of kind '${kind}' with ID ${id}"
        Key key = KeyFactory.createKey(kind, id)
        key.delete()
    }
}

