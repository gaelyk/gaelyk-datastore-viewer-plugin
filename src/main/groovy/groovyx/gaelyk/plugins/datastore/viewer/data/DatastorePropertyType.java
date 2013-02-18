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
package groovyx.gaelyk.plugins.datastore.viewer.data;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Ben
 * Date: 11/19/11
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public enum DatastorePropertyType {
    BOOLEAN("Boolean", Boolean.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return Boolean.valueOf(value);
        }
    },
    BYTE_SHORT("Byte, short", ShortBlob.class, null) {
        String formatValue(Object value) {
            try {
                return new String(((ShortBlob)value).getBytes(), "UTF-8");
            }
            catch(UnsupportedEncodingException e) {
                return null;
            }
        }

        Object parseValue(String value) {
            try {
                return new ShortBlob(value.getBytes("UTF-8"));
            }
            catch(UnsupportedEncodingException e) {
                return null;
            }
        }
    },
    BYTE_LONG("Byte, long", Blob.class, null) {
        String formatValue(Object value) {
            try {
                return new String(((Blob)value).getBytes(), "UTF-8");
            }
            catch(UnsupportedEncodingException e) {
                return null;
            }
        }

        Object parseValue(String value) {
            try {
                return new Blob(value.getBytes("UTF-8"));
            }
            catch(UnsupportedEncodingException e) {
                return null;
            }
        }
    },
    CATEGORY("Category", Category.class, null) {
        String formatValue(Object value) {
            return ((Category)value).getCategory();
        }

        Object parseValue(String value) {
            return new Category(value);
        }
    },
    DATE_TIME("Date & Time", Date.class, "yyyy-MM-dd HH:mm:ss") {
        String formatValue(Object value) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((Date)value));
        }

        Object parseValue(String value) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
            }
            catch(ParseException e) {
                return null;
            }
        }
    },
    EMAIL("Email Address", Email.class, null) {
        String formatValue(Object value) {
            return ((Email)value).getEmail();
        }

        Object parseValue(String value) {
            return new Email(value);
        }
    },
    FLOAT("Float", Float.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return Float.valueOf(value);
        }
    },
    DOUBLE("Double", Double.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return Double.valueOf(value);
        }
    },
    GEO_POINT("Geographical Point", GeoPt.class, "latitude,longitude") {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            String[] latLong = value.split(",");
            return new GeoPt(Float.parseFloat(latLong[0].trim()), Float.parseFloat(latLong[1].trim()));
        }
    },
    GOOGLE_ACCOUNT_USER("Google Account User", User.class, null) {
        String formatValue(Object value) {
            return ((User)value).getEmail();
        }

        Object parseValue(String value) {
            return new User(value, "");
        }
    },
    SHORT("Short", Short.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return Short.valueOf(value);
        }
    },
    INTEGER("Integer", Integer.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return Integer.valueOf(value);
        }
    },
    LONG("Long", Long.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return Long.valueOf(value);
        }
    },
    BLOBSTORE_KEY("Key, Blobstore", BlobKey.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return new BlobKey(value);
        }
    },
    DATASTORE_KEY("Key, Datastore", Key.class, null) {
        String formatValue(Object value) {
            return KeyFactory.keyToString((Key)value);
        }

        Object parseValue(String value) {
            return KeyFactory.stringToKey(value);
        }
    },
    LINK("Link", Link.class, "Must be a valid URL.") {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return new Link(value);
        }
    },
    MESSAGING_HANDLE("Messaging Handle", IMHandle.class, null) {
        String formatValue(Object value) {
            return ((IMHandle)value).getAddress();
        }

        Object parseValue(String value) {
            return new IMHandle(IMHandle.Scheme.unknown, value);
        }
    },
    NULL("Null", null, null) {
        String formatValue(Object value) {
            return "&lt;null&gt;";
        }

        Object parseValue(String value) {
            return null;
        }
    },
    POSTAL_ADDRESS("Postal Address", PostalAddress.class, null) {
        String formatValue(Object value) {
            return ((PostalAddress)value).getAddress();
        }

        Object parseValue(String value) {
            return new PostalAddress(value);
        }
    },
    RATING("Rating", Rating.class, null) {
        String formatValue(Object value) {
            return Integer.toString(((Rating)value).getRating());
        }

        Object parseValue(String value) {
            return new Rating(Integer.parseInt(value));
        }
    },
    PHONE_NUMBER("Phone Number", PhoneNumber.class, null) {
        String formatValue(Object value) {
            return ((PhoneNumber)value).getNumber();
        }

        Object parseValue(String value) {
            return new PhoneNumber(value);
        }
    },
    TEXT_SHORT("Text, short", String.class, null) {
        String formatValue(Object value) {
            return value.toString();
        }

        Object parseValue(String value) {
            return value;
        }
    },
    TEXT_LONG("Text, long", Text.class, null) {
        String formatValue(Object value) {
            return ((Text)value).getValue();
        }

        Object parseValue(String value) {
            return new Text(value);
        }
    };

    static final Map<Class, DatastorePropertyType> propertyTypes;

    static {
        propertyTypes = new HashMap<Class, DatastorePropertyType>();

        for(DatastorePropertyType propertyType : values()) {
            propertyTypes.put(propertyType.javaType, propertyType);
        }
    }

    final String label;
    final Class javaType;
    final String hint;

    private DatastorePropertyType(String label, Class javaType, String hint) {
        this.label = label;
        this.javaType = javaType;
        this.hint = hint;
    }

    public static DatastorePropertyType getPropertyTypeForJavaType(Class klass) {
        DatastorePropertyType datastorePropertyType = propertyTypes.get(klass);

        if(datastorePropertyType == null) {
            throw new IllegalArgumentException("Google App Engine does not support the datastore java type '" + klass.getName() + "'");
        }

        return datastorePropertyType;
    }

    public String getLabel() {
        return label;
    }

    public Class getJavaType() {
        return javaType;
    }

    public String getHint() {
        return hint;
    }

    abstract String formatValue(Object value);
    abstract Object parseValue(String value);
}
