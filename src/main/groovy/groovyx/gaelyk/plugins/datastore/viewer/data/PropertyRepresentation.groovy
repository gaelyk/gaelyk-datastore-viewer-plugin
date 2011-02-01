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

/**
 * <a href="http://code.google.com/appengine/docs/java/datastore/metadataqueries.html#Property_Representation_Queries">Property representation</a>.
 *
 * @author Benjamin Muschko
 */
enum PropertyRepresentation {
    STRING(DatastorePropertyType.TEXT_SHORT), INT64(DatastorePropertyType.INTEGER), DOUBLE(DatastorePropertyType.DOUBLE),
    BOOLEAN(DatastorePropertyType.BOOLEAN), USER(DatastorePropertyType.GOOGLE_ACCOUNT_USER), REFERENCE(DatastorePropertyType.DATASTORE_KEY),
    POINT(DatastorePropertyType.GEO_POINT), NULL(DatastorePropertyType.NULL)

    final DatastorePropertyType datastorePropertyType

    PropertyRepresentation(DatastorePropertyType datastorePropertyType) {
        this.datastorePropertyType = datastorePropertyType
    }
}
