package groovyx.gaelyk.plugins.datastore.viewer

import groovyx.gaelyk.plugins.datastore.viewer.data.EntityBrowsingData


log.info "Browsing data"

def selectedNamespace = params.namespace
def kind = params.kind
def offset = params.offset?.toInteger()
def limit = params.limit?.toInteger()

selectedNamespace = selectedNamespace ? selectedNamespace : ''
request.namespace = selectedNamespace

namespace.of(selectedNamespace) {
    EntityBrowsingData entityBrowsingData = datastoreViewerService.getEntityBrowsingData(kind, offset, limit)
    request.kinds = entityBrowsingData.kinds
    request.kind = entityBrowsingData.selectedKind
    request.kindProperties = entityBrowsingData.kindProperties
    request.page = entityBrowsingData.entityPage
}

forward '/WEB-INF/pages/groovyx/gaelyk/plugins/datastore/viewer/browse.gtpl'
