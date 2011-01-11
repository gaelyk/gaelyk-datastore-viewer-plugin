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
 * Page representation for a specific entity.
 *
 * @author Benjamin Muschko
 */
class EntityPage {
    final int offset
    final int limit
    final recordsIncludingNext

    EntityPage(recordsIncludingNext) {
        this.offset = Pagination.DEFAULT_OFFSET
        this.limit = Pagination.DEFAULT_LIMIT
        this.recordsIncludingNext = recordsIncludingNext
    }

    EntityPage(int offset, recordsIncludingNext) {
        this.offset = offset
        this.limit = Pagination.DEFAULT_LIMIT
        this.recordsIncludingNext = recordsIncludingNext
    }

    EntityPage(int offset, int limit, recordsIncludingNext) {
        this.offset = offset
        this.limit = limit
        this.recordsIncludingNext = recordsIncludingNext
    }

    boolean hasNextPage() {
        recordsIncludingNext.size() > limit ? true : false
    }

    def getRecords() {
        int size = recordsIncludingNext.size()
        def records

        if(recordsIncludingNext.size() == 0) {
            records = recordsIncludingNext
        }
        else if(size < limit + 1) {
            records = recordsIncludingNext[0..size - 1]
        }
        else {
            records = recordsIncludingNext[0..limit - 1]
        }

        records
    }
}