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
 * Test for pagination representation.
 *
 * @author Benjamin Muschko
 */
class PaginationTest {
    @Test
    void testPaginationForDefaultValues() {
        def pagination = new Pagination(null, null)
        assert pagination.offset == 0
        assert pagination.limit == 10
    }

    @Test
    void testPaginationForProvidedValues() {
        def pagination = new Pagination(10, 20)
        assert pagination.offset == 10
        assert pagination.limit == 20
    }
}
