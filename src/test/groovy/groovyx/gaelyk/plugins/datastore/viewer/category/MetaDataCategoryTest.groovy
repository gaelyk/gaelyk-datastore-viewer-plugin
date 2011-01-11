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

import com.google.appengine.api.NamespaceManager
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.appengine.api.datastore.*

/**
 * Test for metadata category.
 *
 * @author Benjamin Muschko
 */
@RunWith(MetaDataCategoryJUnitRunner.class)
class MetaDataCategoryTest {
    def helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
    def datastore

    @Before
    public void setUp() {
        helper.setUp()
        datastore = DatastoreServiceFactory.datastoreService
    }

    @After
    public void tearDown() {
        helper.tearDown()
        datastore = null
    }

    @Test
    void testGetNamespacesForDefaultFetchOptions() {
        addNamespaces()
        def namespaces = datastore.getNamespaces()
        assert namespaces.size() == 3
        assert namespaces.get(0).key.name == "tenant1"
        assert namespaces.get(1).key.name == "tenant2"
        assert namespaces.get(2).key.name == "tenant3"
    }

    @Test
    void testGetNamespacesForLimitedResultSet() {
        addNamespaces()
        def namespaces = datastore.getNamespaces(FetchOptions.Builder.withLimit(1))
        assert namespaces.size() == 1
        assert namespaces.get(0).key.name == "tenant1"
    }

    @Test
    void testGetNamespacesForFilteredResultSetWithDefaultFetchOptions() {
        addNamespaces()
        def namespaces = datastore.getNamespaces() { query ->
                            query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                            makeNamespaceKey("tenant2"))
                         }
        assert namespaces.size() == 2
        assert namespaces.get(0).key.name == "tenant2"
        assert namespaces.get(1).key.name == "tenant3"
    }

    @Test
    void testGetNamespacesForFilteredResultSetWithLimitedFetchOptions() {
        addNamespaces()
        def namespaces = datastore.getNamespaces(FetchOptions.Builder.withLimit(1)) { query ->
                            query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                            makeNamespaceKey("tenant2"))
                         }
        assert namespaces.size() == 1
        assert namespaces.get(0).key.name == "tenant2"
    }

    @Test
    void testGetKindsForDefaultFetchOptions() {
        addKinds()
        def kinds = datastore.getKinds()
        assert kinds.size() == 3
        assert kinds.get(0).key.name == "Comment"
        assert kinds.get(1).key.name == "Goal"
        assert kinds.get(2).key.name == "User"
    }

    @Test
    void testGetKindsForLimitedResultSet() {
        addKinds()
        def kinds = datastore.getKinds(FetchOptions.Builder.withLimit(2))
        assert kinds.size() == 2
        assert kinds.get(0).key.name == "Comment"
        assert kinds.get(1).key.name == "Goal"
    }

    @Test
    void testGetKindsForFilteredResultSetWithDefaultFetchOptions() {
        addKinds()
        def kinds = datastore.getKinds() { query ->
                        query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                        makeKindKey("G"))
                    }
        assert kinds.size() == 2
        assert kinds.get(0).key.name == "Goal"
        assert kinds.get(1).key.name == "User"
    }

    @Test
    void testGetKindsForFilteredResultSetWithLimitedFetchOptions() {
        addKinds()
        def kinds = datastore.getKinds(FetchOptions.Builder.withLimit(1)) { query ->
                        query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                        makeKindKey("G"))
                    }
        assert kinds.size() == 1
        assert kinds.get(0).key.name == "Goal"
    }

    @Test
    void testGetPropertiesForDefaultFetchOptions() {
        addKinds()
        def properties = datastore.getProperties()
        assert properties.size() == 6
        assert properties.get(0).key.parent.name == "Comment"
        assert properties.get(0).key.name == "text"
        assert properties.get(1).key.parent.name == "Comment"
        assert properties.get(1).key.name == "userId"
        assert properties.get(2).key.parent.name == "Goal"
        assert properties.get(2).key.name == "description"
        assert properties.get(3).key.parent.name == "Goal"
        assert properties.get(3).key.name == "name"
        assert properties.get(4).key.parent.name == "User"
        assert properties.get(4).key.name == "firstname"
        assert properties.get(5).key.parent.name == "User"
        assert properties.get(5).key.name == "lastname"
    }

    @Test
    void testGetPropertiesForLimitedResultSet() {
        addKinds()
        def properties = datastore.getProperties(FetchOptions.Builder.withLimit(4))
        assert properties.size() == 4
        assert properties.get(0).key.parent.name == "Comment"
        assert properties.get(0).key.name == "text"
        assert properties.get(1).key.parent.name == "Comment"
        assert properties.get(1).key.name == "userId"
    }
    
    @Test
    void testGetPropertiesForFilteredResultSetWithDefaultFetchOptions() {
        addKinds()
        def properties = datastore.getProperties() { query ->
                            query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                            makeKindKey("G"))
                        }
        assert properties.size() == 4
        assert properties.get(0).key.parent.name == "Goal"
        assert properties.get(0).key.name == "description"
        assert properties.get(1).key.parent.name == "Goal"
        assert properties.get(1).key.name == "name"
        assert properties.get(2).key.parent.name == "User"
        assert properties.get(2).key.name == "firstname"
        assert properties.get(3).key.parent.name == "User"
        assert properties.get(3).key.name == "lastname"
    }

    @Test
    void testGetPropertiesForFilteredResultSetWithLimitedFetchOptions() {
        addKinds()
        def properties = datastore.getProperties(FetchOptions.Builder.withLimit(2)) { query ->
                            query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                            makeKindKey("G"))
                        }
        assert properties.size() == 2
        assert properties.get(0).key.parent.name == "Goal"
        assert properties.get(0).key.name == "description"
        assert properties.get(1).key.parent.name == "Goal"
        assert properties.get(1).key.name == "name"
    }

    @Test
    void testGetPropertiesForGoalKindWithDefaultFetchOptions() {
        addKinds()
        def properties = datastore.getProperties("Goal")
        assert properties.size() == 2
        assert properties.get(0).key.parent.name == "Goal"
        assert properties.get(0).key.name == "description"
        assert properties.get(1).key.parent.name == "Goal"
        assert properties.get(1).key.name == "name"
    }

    @Test
    void testGetPropertiesForGoalKindFilteredResultSetWithDefaultFetchOptions() {
        addKinds()
        def properties = datastore.getProperties("Goal", FetchOptions.Builder.withLimit(1)) { query ->
                            query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                            makePropertyKey("Goal", "d"))
                        }
        assert properties.size() == 1
        assert properties.get(0).key.parent.name == "Goal"
        assert properties.get(0).key.name == "description"
    }

    @Test
    void testGetPropertiesForGoalKindFilteredResultSetWithLimitedFetchOptions() {
        addKinds()
        def properties = datastore.getProperties("Goal") { query ->
                            query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN_OR_EQUAL,
                                            makePropertyKey("Goal", "n"))
                        }
        assert properties.size() == 1
        assert properties.get(0).key.parent.name == "Goal"
        assert properties.get(0).key.name == "name"
    }

    @Test
    void testGetPropertiesForGoalKindWithLimitedFetchOptions() {
        addKinds()
        def properties = datastore.getProperties("Goal", FetchOptions.Builder.withLimit(1))
        assert properties.size() == 1
        assert properties.get(0).key.parent.name == "Goal"
        assert properties.get(0).key.name == "description"
    }

    @Test
    void testGetProperty() {
        addKinds()
        def property = datastore.getProperty("Goal", "name")
        assert property != null
        assert property.getProperty("property_representation") == ["STRING"]
    }

    private void addNamespaces() {
        NamespaceManager.set("tenant1")
        datastore.put new Entity("User", "User")
        NamespaceManager.set("tenant2")
        datastore.put new Entity("Goal", "Goal")
        NamespaceManager.set("tenant3")
        datastore.put new Entity("Comment", "Comment")
    }

    private void addKinds() {
        def user = new Entity("User", "User")
        user.firstname = "John"
        user.lastname = "Doe"
        def goal = new Entity("Goal", "Goal")
        goal.name = "Write book"
        goal.description = "Finish writing a novel"
        def comment = new Entity("Comment", "Comment")
        comment.userId = "123"
        comment.text = "Great posting!"
        datastore.put user
        datastore.put goal
        datastore.put comment
    }

    private Key makeNamespaceKey(String namespace) {
        KeyFactory.createKey(Query.NAMESPACE_METADATA_KIND, namespace)
    }

    private Key makeKindKey(String kind) {
        KeyFactory.createKey(Query.KIND_METADATA_KIND, kind)
    }

    private Key makePropertyKey(String kind, String property) {
        KeyFactory.createKey(makeKindKey(kind), Query.PROPERTY_METADATA_KIND, property)
    }
}
