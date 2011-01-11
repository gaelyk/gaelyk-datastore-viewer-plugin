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

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import groovyx.gaelyk.plugins.datastore.viewer.category.MetaDataCategoryJUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.appengine.api.datastore.*

/**
 * Test for datastore viewer service.
 *
 * @author Benjamin Muschko
 */
@RunWith(MetaDataCategoryJUnitRunner.class)
class DatastoreViewerServiceTest {
    def helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
    def datastoreViewerService

    @Before
    public void setUp() {
        helper.setUp()
        datastoreViewerService = new DatastoreViewerService()
    }

    @After
    public void tearDown() {
        helper.tearDown()
        datastoreViewerService = null
    }

    @Test
    void testGetEntityBrowsingData() {
        Entity goal = new Entity("Goal", "Goal")
        goal.name = "Traveling"
        goal.description = "All continents"
        datastoreViewerService.datastore.put goal

        def entityBrowsingData = datastoreViewerService.getEntityBrowsingData("Goal", 0, 10)
        assert entityBrowsingData.kinds.size() == 2
        assert entityBrowsingData.kinds.get(0).key.name == "Goal"
        assert entityBrowsingData.kinds.get(1).key.name == "User"
        assert entityBrowsingData.selectedKind == "Goal"
        assert entityBrowsingData.kindProperties.size() == 2
        assert entityBrowsingData.kindProperties.get(0).key.name == "description"
        assert entityBrowsingData.kindProperties.get(1).key.name == "name"
        assert entityBrowsingData.entityPage.offset == 0
        assert entityBrowsingData.entityPage.limit == 10
        assert entityBrowsingData.entityPage.recordsIncludingNext.size() == 1
        assert entityBrowsingData.entityPage.recordsIncludingNext.get(0) == goal
    }

    @Test
    void testDetermineSelectedKindForProvidedKind() {
        assert datastoreViewerService.determineSelectedKind("User", new ArrayList<Entity>()), "User"
    }

    @Test
    void testDetermineSelectedKindForNoProvidedKind() {
        def entities = new ArrayList<Entity>()
        entities << new Entity("User", "User")
        entities << new Entity("Goal", "Goal")
        assert datastoreViewerService.determineSelectedKind(null, entities), "User"
    }

    @Test
    void testDeleteEntity() {
        Key key = KeyFactory.createKey("User", 1L)
        def entity = new Entity(key)
        datastoreViewerService.datastore.put entity
        List<Entity> entitiesAfterInsert = queryEntities()
        assert entitiesAfterInsert.size() == 1
        assert entitiesAfterInsert.get(0).key.id == 1
        datastoreViewerService.deleteEntity("User", 1L)
        List<Entity> entitiesAfterDelete = queryEntities()
        assert entitiesAfterDelete.size() == 0
    }

    private List<Entity> queryEntities() {
        def query = new Query("User")
        PreparedQuery preparedQuery = datastoreViewerService.datastore.prepare(query)
        preparedQuery.asList(FetchOptions.Builder.withDefaults())
    }
}
