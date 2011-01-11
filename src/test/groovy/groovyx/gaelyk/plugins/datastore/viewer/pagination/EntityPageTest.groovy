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
package groovyx.gaelyk.plugins.datastore.viewer.pagination

import org.junit.Test

/**
 * Test for entity page.
 *
 * @author Benjamin Muschko
 */
class EntityPageTest {
    @Test
    void testHasNextPageForDefaultLimitAndMoreThanLimit() {
        assert new EntityPage([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]).hasNextPage() == true
    }

    @Test
    void testHasNextPageForDefaultLimitAndExactLimit() {
        assert new EntityPage([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]).hasNextPage() == false
    }

    @Test
    void testHasNextPageForDefaultLimitAndLessThanLimit() {
        assert new EntityPage([1, 2, 3, 4, 5, 6, 7, 8, 9]).hasNextPage() == false
    }

    @Test
    void testHasNextPageForProvidedLimitAndMoreThanLimit() {
        assert new EntityPage(0, 20, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]).hasNextPage() == true
    }

    @Test
    void testHasNextPageForProvidedLimitAndExactLimit() {
        assert new EntityPage(0, 20, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]).hasNextPage() == false
    }

    @Test
    void testHasNextPageForProvidedLimitAndLessThanLimit() {
        assert new EntityPage(0, 20, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]).hasNextPage() == false
    }

    @Test
    void testHasNextPageForProvidedOffsetAndMoreThanLimit() {
        assert new EntityPage(10, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]).hasNextPage() == true
    }

    @Test
    void testHasNextPageForProvidedOffsetAndExactLimit() {
        assert new EntityPage(10, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]).hasNextPage() == false
    }

    @Test
    void testHasNextPageForProvidedOffsetAndLessThanLimit() {
        assert new EntityPage(10, [1, 2, 3, 4, 5, 6, 7, 8, 9]).hasNextPage() == false
    }

    @Test
    void testGetRecordsForNoRecords() {
        assert new EntityPage([]).getRecords() == []
    }

    @Test
    void testGetRecordsForLessRecordsThanLimit() {
        assert new EntityPage([1, 2, 3, 4, 5]).getRecords() == [1, 2, 3, 4, 5]
    }

    @Test
    void testGetRecordsForSameRecordsAsLimit() {
        assert new EntityPage([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]).getRecords() == [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    }

    @Test
    void testGetRecordsForMoreRecordsThanLimit() {
        assert new EntityPage([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]).getRecords() == [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    }
}
