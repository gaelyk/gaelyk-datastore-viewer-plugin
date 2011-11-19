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
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.appengine.api.datastore.*

/**
 * Test for datastore property type.
 *
 * @author Benjamin Muschko
 */
class DatastorePropertyTypeTest {
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
    void testFormatValueForBooleanFalse() {
        assert DatastorePropertyType.BOOLEAN.formatValue(false) == 'false'
    }

    @Test
    void testFormatValueForBooleanTrue() {
        assert DatastorePropertyType.BOOLEAN.formatValue(true) == 'true'
    }

    @Test
    void testFormatValueForByteShort() {
        byte[] bytes = 'test'.getBytes('UTF-8')
        assert DatastorePropertyType.BYTE_SHORT.formatValue(new ShortBlob(bytes)) == 'test'
    }

    @Test
    void testFormatValueForByteLong() {
        byte[] bytes = 'test'.getBytes('UTF-8')
        assert DatastorePropertyType.BYTE_LONG.formatValue(new Blob(bytes)) == 'test'
    }

    @Test
    void testFormatValueForCategory() {
        assert DatastorePropertyType.CATEGORY.formatValue(new Category('FUNNY')) == 'FUNNY'
    }

    @Test
    void testFormatValueForDateAndTime() {
        assert DatastorePropertyType.DATE_TIME.formatValue(new Date().parse("d/M/yyyy HH:mm:ss","21/3/2008 17:30:20")) == '2008-03-21 17:30:20'
    }

    @Test
    void testFormatValueForEmail() {
        assert DatastorePropertyType.EMAIL.formatValue(new Email('foo@bar.com')) == 'foo@bar.com'
    }

    @Test
    void testFormatValueForFloat() {
        assert DatastorePropertyType.FLOAT.formatValue(new Float(1.0)) == '1.0'
    }

    @Test
    void testFormatValueForDouble() {
        assert DatastorePropertyType.DOUBLE.formatValue(new Double(1.0345)) == '1.0345'
    }

    @Test
    void testFormatValueForGeoPoint() {
        assert DatastorePropertyType.GEO_POINT.formatValue(new GeoPt(21.03F, 67.45F)) == '21.030001,67.449997'
    }

    @Test
    void testFormatValueForGoogleAccountUser() {
        assert DatastorePropertyType.GOOGLE_ACCOUNT_USER.formatValue(new User('foo@bar.com', 'authDomain')) == 'foo@bar.com'
    }

    @Test
    void testFormatValueForShort() {
        assert DatastorePropertyType.SHORT.formatValue(Short.parseShort('1')) == '1'
    }

    @Test
    void testFormatValueForInteger() {
        assert DatastorePropertyType.INTEGER.formatValue(new Integer(1)) == '1'
    }

    @Test
    void testFormatValueForLong() {
        assert DatastorePropertyType.LONG.formatValue(new Long(1)) == '1'
    }

    @Test
    void testFormatValueForBlobstoreKey() {
        BlobKey key = new BlobKey("User")
        assert DatastorePropertyType.BLOBSTORE_KEY.formatValue(key) == '<BlobKey: User>'
    }

    @Test
    void testFormatValueForDatastoreKey() {
        Key key = new Key("User", "User")
        assert DatastorePropertyType.DATASTORE_KEY.formatValue(key) == 'agR0ZXN0cg4LEgRVc2VyIgRVc2VyDA'
    }

    @Test
    void testFormatValueForLink() {
        assert DatastorePropertyType.LINK.formatValue(new Link('http://www.google.com')) == 'http://www.google.com'
    }

    @Test
    void testFormatValueForMessageHandle() {
        assert DatastorePropertyType.MESSAGING_HANDLE.formatValue(new IMHandle(IMHandle.Scheme.unknown, 'http://www.google.com')) == 'http://www.google.com'
    }

    @Test
    void testFormatValueForNull() {
        assert DatastorePropertyType.NULL.formatValue(null) == '&lt;null&gt;'
    }

    @Test
    void testFormatValueForPostalAddress() {
        assert DatastorePropertyType.POSTAL_ADDRESS.formatValue(new PostalAddress('Washington, DC')) == 'Washington, DC'
    }

    @Test
    void testFormatValueForRating() {
        assert DatastorePropertyType.RATING.formatValue(new Rating(5)) == '5'
    }

    @Test
    void testFormatValueForPhoneNumber() {
        assert DatastorePropertyType.PHONE_NUMBER.formatValue(new PhoneNumber('567-345-5678')) == '567-345-5678'
    }

    @Test
    void testFormatValueForTextShort() {
        assert DatastorePropertyType.TEXT_SHORT.formatValue('123') == '123'
    }

    @Test
    void testFormatValueForTextLong() {
        assert DatastorePropertyType.TEXT_LONG.formatValue(new Text('123')) == '123'
    }

    @Test
    void testParseValueForBooleanFalse() {
        assert DatastorePropertyType.BOOLEAN.parseValue('false') == Boolean.FALSE
    }

    @Test
    void testParseValueForBooleanTrue() {
        assert DatastorePropertyType.BOOLEAN.parseValue('true') == Boolean.TRUE
    }

    @Test
    void testParseValueForByteShort() {
        assert DatastorePropertyType.BYTE_SHORT.parseValue('test') == new ShortBlob('test'.getBytes('UTF-8'))
    }

    @Test
    void testParseValueForByteLong() {
        assert DatastorePropertyType.BYTE_LONG.parseValue('test') == new Blob('test'.getBytes('UTF-8'))
    }

    @Test
    void testParseValueForCategory() {
        assert DatastorePropertyType.CATEGORY.parseValue('FUNNY') == new Category('FUNNY')
    }

    @Test
    void testParseValueForDateAndTime() {
        assert DatastorePropertyType.DATE_TIME.parseValue('2008-03-21 17:30:20') == new Date().parse("d/M/yyyy HH:mm:ss","21/3/2008 17:30:20")
    }

    @Test
    void testParseValueForEmail() {
        assert DatastorePropertyType.EMAIL.parseValue('foo@bar.com') == new Email('foo@bar.com')
    }

    @Test
    void testParseValueForFloat() {
        assert DatastorePropertyType.FLOAT.parseValue('1.0') == new Float(1.0)
    }

    @Test
    void testParseValueForDouble() {
        assert DatastorePropertyType.DOUBLE.parseValue('1.0345') == new Double(1.0345)
    }

    @Test
    void testParseValueForGeoPoint() {
        assert DatastorePropertyType.GEO_POINT.parseValue('21.030001,67.449997') == new GeoPt(21.030001, 67.449997)
    }

    @Test
    void testParseValueForGoogleAccountUser() {
        assert DatastorePropertyType.GOOGLE_ACCOUNT_USER.parseValue('foo@bar.com') == new User('foo@bar.com', '')
    }

    @Test
    void testParseValueForShort() {
        assert DatastorePropertyType.SHORT.parseValue('1') == Short.parseShort('1')
    }

    @Test
    void testParseValueForInteger() {
        assert DatastorePropertyType.INTEGER.parseValue('124') == new Integer(124)
    }

    @Test
    void testParseValueForLong() {
        assert DatastorePropertyType.LONG.parseValue('124345') == new Long(124345)
    }

    @Test
    void testParseValueForBlobstoreKey() {
        assert DatastorePropertyType.BLOBSTORE_KEY.parseValue('User') == new BlobKey('User')
    }

    @Test
    void testParseValueForDatastoreKey() {
        assert DatastorePropertyType.DATASTORE_KEY.parseValue('agR0ZXN0cg4LEgRVc2VyIgRVc2VyDA') == new Key("User", "User")
    }

    @Test
    void testParseValueForLink() {
        assert DatastorePropertyType.LINK.parseValue('http://www.google.com') == new Link('http://www.google.com')
    }

    @Test
    void testParseValueForMessageHandle() {
        assert DatastorePropertyType.MESSAGING_HANDLE.parseValue('http://www.google.com') == new IMHandle(IMHandle.Scheme.unknown, 'http://www.google.com')
    }

    @Test
    void testParseValueForNull() {
        assert DatastorePropertyType.NULL.parseValue('') == null
    }

    @Test
    void testParseValueForPostalAddress() {
        assert DatastorePropertyType.POSTAL_ADDRESS.parseValue('Washington, DC') == new PostalAddress('Washington, DC')
    }

    @Test
    void testParseValueForRating() {
        assert DatastorePropertyType.RATING.parseValue('5') == new Rating(5)
    }

    @Test
    void testParseValueForPhoneNumber() {
        assert DatastorePropertyType.PHONE_NUMBER.parseValue('567-345-5678') == new PhoneNumber('567-345-5678')
    }

    @Test
    void testParseValueForTextShort() {
        assert DatastorePropertyType.TEXT_SHORT.parseValue('123') == '123'
    }

    @Test
    void testParseValueForTextLong() {
        assert DatastorePropertyType.TEXT_LONG.parseValue('123') == new Text('123')
    }

    @Test
    void testGetPropertyTypeForJavaTypeForBoolean() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Boolean.class) == DatastorePropertyType.BOOLEAN
    }

    @Test
    void testGetPropertyTypeForJavaTypeForShortBlob() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(ShortBlob.class) == DatastorePropertyType.BYTE_SHORT
    }

    @Test
    void testGetPropertyTypeForJavaTypeForBlob() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Blob.class) == DatastorePropertyType.BYTE_LONG
    }

    @Test
    void testGetPropertyTypeForJavaTypeForCategory() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Category.class) == DatastorePropertyType.CATEGORY
    }

    @Test
    void testGetPropertyTypeForJavaTypeForDate() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Date.class) == DatastorePropertyType.DATE_TIME
    }

    @Test
    void testGetPropertyTypeForJavaTypeForEmail() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Email.class) == DatastorePropertyType.EMAIL
    }

    @Test
    void testGetPropertyTypeForJavaTypeForFloat() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Float.class) == DatastorePropertyType.FLOAT
    }

    @Test
    void testGetPropertyTypeForJavaTypeForDouble() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Double.class) == DatastorePropertyType.DOUBLE
    }

    @Test
    void testGetPropertyTypeForJavaTypeForGeoPt() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(GeoPt.class) == DatastorePropertyType.GEO_POINT
    }

    @Test
    void testGetPropertyTypeForJavaTypeForUser() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(User.class) == DatastorePropertyType.GOOGLE_ACCOUNT_USER
    }

    @Test
    void testGetPropertyTypeForJavaTypeForShort() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Short.class) == DatastorePropertyType.SHORT
    }

    @Test
    void testGetPropertyTypeForJavaTypeForInteger() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Integer.class) == DatastorePropertyType.INTEGER
    }

    @Test
    void testGetPropertyTypeForJavaTypeForLong() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Long.class) == DatastorePropertyType.LONG
    }

    @Test
    void testGetPropertyTypeForJavaTypeForBlobKey() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(BlobKey.class) == DatastorePropertyType.BLOBSTORE_KEY
    }

    @Test
    void testGetPropertyTypeForJavaTypeForKey() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Key.class) == DatastorePropertyType.DATASTORE_KEY
    }

    @Test
    void testGetPropertyTypeForJavaTypeForLink() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Link.class) == DatastorePropertyType.LINK
    }

    @Test
    void testGetPropertyTypeForJavaTypeForIMHandle() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(IMHandle.class) == DatastorePropertyType.MESSAGING_HANDLE
    }

    @Test
    void testGetPropertyTypeForJavaTypeForNull() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(null) == DatastorePropertyType.NULL
    }

    @Test
    void testGetPropertyTypeForJavaTypeForPostalAddress() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(PostalAddress.class) == DatastorePropertyType.POSTAL_ADDRESS
    }

    @Test
    void testGetPropertyTypeForJavaTypeForRating() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Rating.class) == DatastorePropertyType.RATING
    }

    @Test
    void testGetPropertyTypeForJavaTypeForPhoneNumber() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(PhoneNumber.class) == DatastorePropertyType.PHONE_NUMBER
    }

    @Test
    void testGetPropertyTypeForJavaTypeForString() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(String.class) == DatastorePropertyType.TEXT_SHORT
    }

    @Test
    void testGetPropertyTypeForJavaTypeForText() {
        assert DatastorePropertyType.getPropertyTypeForJavaType(Text.class) == DatastorePropertyType.TEXT_LONG
    }
}

