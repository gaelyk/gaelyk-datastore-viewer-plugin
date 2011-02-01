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

import com.google.appengine.api.blobstore.BlobKey
import com.google.appengine.api.users.User
import com.google.appengine.api.datastore.*

/**
 * Datastore property type.
 *
 * @author Benjamin Muschko
 */
enum DatastorePropertyType {
    BOOLEAN("Boolean", Boolean.class, { _this, value -> value.toString() }, { _this, value -> Boolean.valueOf(value) }),
    BYTE_SHORT("Byte, short", ShortBlob.class, { _this, value -> new String(value.getBytes(), 'UTF-8') }, { _this, value -> new ShortBlob(value.getBytes('UTF-8')) }),
    BYTE_LONG("Byte, long", Blob.class, { _this, value -> new String(value.getBytes(), 'UTF-8') }, { _this, value -> new Blob(value.getBytes('UTF-8')) }),
    CATEGORY("Category", Category.class, { _this, value -> value.category }, { _this, value -> new Category(value) }),
    DATE_TIME("Date & Time", Date.class, { _this, value -> value.format('yyyy-MM-dd HH:mm:ss') }, { _this, value -> new Date().parse("yyyy-MM-dd HH:mm:ss",value) }),
    EMAIL("Email Address", Email.class, { _this, value -> value.email }, { _this, value -> new Email(value) }),
    FLOAT("Float", Float.class, { _this, value -> value.toString() }, { _this, value -> Float.valueOf(value) }),
    DOUBLE("Double", Double.class, { _this, value -> value.toString() }, { _this, value ->  Double.valueOf(value) }),
    GEO_POINT("Geographical Point", GeoPt.class, { _this, value -> value.toString() }, { _this, value -> def latLong = value.split(','); new GeoPt(latLong[0].trim().toFloat(), latLong[1].trim().toFloat()) }),
    GOOGLE_ACCOUNT_USER("Google Account User", User.class, { _this, value -> value.getEmail() }, { _this, value -> new User(value, '') }),
    SHORT("Short", Short.class, { _this, value -> value.toString() }, { _this, value -> Short.valueOf(value) }),
    INTEGER("Integer", Integer.class, { _this, value -> value.toString() }, { _this, value -> Integer.valueOf(value) }),
    LONG("Long", Long.class, { _this, value -> value.toString() }, { _this, value -> Long.valueOf(value) }),
    BLOBSTORE_KEY("Key, Blobstore", BlobKey.class, { _this, value -> value.toString() }, { _this, value -> new BlobKey(value) }),
    DATASTORE_KEY("Key, Datastore", Key.class, { _this, value -> KeyFactory.keyToString(value) }, { _this, value -> KeyFactory.stringToKey(value) }),
    LINK("Link", Link.class, { _this, value -> value.toString() }, { _this, value -> new Link(value) }),
    MESSAGING_HANDLE("Messaging Handle", IMHandle.class, { _this, value -> value.toDatastoreString() }, { _this, value -> IMHandle.fromDatastoreString(value) }),
    NULL("Null", null, { _this, value -> "<null>" }, { _this, value -> null }),
    POSTAL_ADDRESS("Postal Address", PostalAddress.class, { _this, value -> value.address }, { _this, value -> new PostalAddress(value) }),
    RATING("Rating", Rating.class, { _this, value -> Integer.toString(value.rating) }, { _this, value -> new Rating(value.toInteger()) }),
    PHONE_NUMBER("Phone Number", PhoneNumber.class, { _this, value -> value.number }, { _this, value -> new PhoneNumber(value) }),
    TEXT_SHORT("Text, short", String.class, { _this, value -> value.toString() }, { _this, value -> value }),
    TEXT_LONG("Text, long", Text.class, { _this, value -> value.value }, { _this, value -> new Text(value) })

    static final Map propertyTypes

    static {
        propertyTypes = [:]

        values().each { propertyType ->
            propertyTypes.put(propertyType.javaType, propertyType)
        }
    }

    final String label
    final Class javaType
    final Closure formatValue
    final Closure parseValue

    DatastorePropertyType(String label, Class javaType, Closure formatValueClosure, Closure parseValueClosure) {
        this.label = label
        this.javaType = javaType
        formatValue = formatValueClosure.curry(this)
        parseValue = parseValueClosure.curry(this)
    }

    static DatastorePropertyType getPropertyTypeForJavaType(Class klass) {
        DatastorePropertyType datastorePropertyType = propertyTypes.get(klass)

        if(datastorePropertyType == null) {
            throw new IllegalArgumentException("Google App Engine does not support the datastore java type '" + klass.name + "'")
        }

        datastorePropertyType
    }
}
