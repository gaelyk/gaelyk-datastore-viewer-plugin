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

/**
 * Pagination parameters.
 *
 * @author Benjamin Muschko
 */
class Pagination {
    static final int DEFAULT_OFFSET = 0
    static final int DEFAULT_LIMIT = 10
    final int offset
    final int limit

    Pagination(Integer offset, Integer limit) {
        this.offset = offset ? offset : DEFAULT_OFFSET
        this.limit = limit ? limit : DEFAULT_LIMIT       
    }

    @Override
    public String toString() {
        return "Pagination{" +
               "offset=" + offset +
               ", limit=" + limit +
               '}' ;
    }
}
