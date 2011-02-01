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
package groovyx.gaelyk.plugins.datastore.viewer.data

import org.junit.Test

/**
 * Test for property representation.
 *
 * @author Benjamin Muschko
 */
class PropertyRepresentationTest {
    @Test
    void testGetDatastorePropertyTypeForString() {
        assert PropertyRepresentation.STRING.datastorePropertyType == DatastorePropertyType.TEXT_SHORT
    }

    @Test
    void testGetDatastorePropertyTypeForInt64() {
        assert PropertyRepresentation.INT64.datastorePropertyType == DatastorePropertyType.INTEGER
    }

    @Test
    void testGetDatastorePropertyTypeForDouble() {
        assert PropertyRepresentation.DOUBLE.datastorePropertyType == DatastorePropertyType.DOUBLE
    }

    @Test
    void testGetDatastorePropertyTypeForBoolean() {
        assert PropertyRepresentation.BOOLEAN.datastorePropertyType == DatastorePropertyType.BOOLEAN
    }

    @Test
    void testGetDatastorePropertyTypeForUser() {
        assert PropertyRepresentation.USER.datastorePropertyType == DatastorePropertyType.GOOGLE_ACCOUNT_USER
    }

    @Test
    void testGetDatastorePropertyTypeForReference() {
        assert PropertyRepresentation.REFERENCE.datastorePropertyType == DatastorePropertyType.DATASTORE_KEY
    }

    @Test
    void testGetDatastorePropertyTypeForPoint() {
        assert PropertyRepresentation.POINT.datastorePropertyType == DatastorePropertyType.GEO_POINT
    }
}
