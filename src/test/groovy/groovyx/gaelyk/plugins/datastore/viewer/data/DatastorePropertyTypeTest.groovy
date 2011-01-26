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
    void testBooleanForFalse() {
        assert DatastorePropertyType.BOOLEAN.formatValue(false) == 'false'
    }

    @Test
    void testBooleanForTrue() {
        assert DatastorePropertyType.BOOLEAN.formatValue(true) == 'true'
    }

    @Test
    void testByteShort() {
        byte[] bytes = 'test'.getBytes('UTF-8')
        assert DatastorePropertyType.BYTE_SHORT.formatValue(new ShortBlob(bytes)) == '<ShortBlob: 4 bytes>'
    }

    @Test
    void testByteLong() {
        byte[] bytes = 'test'.getBytes('UTF-8')
        assert DatastorePropertyType.BYTE_LONG.formatValue(new Blob(bytes)) == '<Blob: 4 bytes>'
    }

    @Test
    void testCategory() {
        assert DatastorePropertyType.CATEGORY.formatValue(new Category('FUNNY')) == 'FUNNY'
    }

    @Test
    void testDateAndTime() {
        assert DatastorePropertyType.DATE_TIME.formatValue(new Date().parse("d/M/yyyy HH:mm:ss","21/3/2008 17:30:20")) == '2008-03-21 17:30:20'
    }

    @Test
    void testEmail() {
        assert DatastorePropertyType.EMAIL.formatValue(new Email('foo@bar.com')) == 'foo@bar.com'
    }

    @Test
    void testFloatForFloat() {
        assert DatastorePropertyType.FLOAT.formatValue(new Float(1.0)) == '1.0'
    }

    @Test
    void testFloatForDouble() {
        assert DatastorePropertyType.FLOAT.formatValue(new Double(1.0345)) == '1.0345'
    }

    @Test
    void testGeoPoint() {
        assert DatastorePropertyType.GEO_POINT.formatValue(new GeoPt(21.03F, 67.45F)) == '21.030001,67.449997'
    }

    @Test
    void testGoogleAccountUser() {
        assert DatastorePropertyType.GOOGLE_ACCOUNT_USER.formatValue(new User('foo@bar.com', 'authDomain')) == 'foo@bar.com'
    }

    @Test
    void testIntegerForShort() {
        assert DatastorePropertyType.INTEGER.formatValue(Short.parseShort('1')) == '1'
    }

    @Test
    void testIntegerForInteger() {
        assert DatastorePropertyType.INTEGER.formatValue(new Integer(1)) == '1'
    }

    @Test
    void testIntegerForLong() {
        assert DatastorePropertyType.INTEGER.formatValue(new Long(1)) == '1'
    }

    @Test
    void testBlobstoreKey() {
        BlobKey key = new BlobKey("User")
        assert DatastorePropertyType.BLOBSTORE_KEY.formatValue(key) == '<BlobKey: User>'
    }

    @Test
    void testDatastoreKey() {
        Key key = new Key("User", "User")
        assert DatastorePropertyType.DATASTORE_KEY.formatValue(key) == 'agR0ZXN0cg4LEgRVc2VyIgRVc2VyDA'
    }

    @Test
    void testLink() {
        assert DatastorePropertyType.LINK.formatValue(new Link('http://www.google.com')) == 'http://www.google.com'
    }

    @Test
    void testMessageHandle() {
        assert DatastorePropertyType.MESSAGING_HANDLE.formatValue(new IMHandle(new URL('http://www.google.com'), 'http://www.google.com')) == 'http://www.google.com'
    }

    @Test
    void testPostalAddress() {
        assert DatastorePropertyType.POSTAL_ADDRESS.formatValue(new PostalAddress('Washington, DC')) == 'Washington, DC'
    }

    @Test
    void testRating() {
        assert DatastorePropertyType.RATING.formatValue(new Rating(5)) == '5'
    }

    @Test
    void testPhoneNumber() {
        assert DatastorePropertyType.PHONE_NUMBER.formatValue(new PhoneNumber('567-345-5678')) == '567-345-5678'
    }

    @Test
    void testTextShort() {
        assert DatastorePropertyType.TEXT_SHORT.formatValue('123') == '123'
    }

    @Test
    void testTextLong() {
        assert DatastorePropertyType.TEXT_LONG.formatValue(new Text('123')) == '123'
    }
}

