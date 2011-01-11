import groovyx.gaelyk.plugins.datastore.viewer.data.EntityBrowsingData

log.info "Browsing data"

def kind = params.kind
def offset = params.offset?.toInteger()
def limit = params.limit?.toInteger()

EntityBrowsingData entityBrowsingData = datastoreViewerService.getEntityBrowsingData(kind, offset, limit)
request.kinds = entityBrowsingData.kinds
request.kind = entityBrowsingData.selectedKind
request.kindProperties = entityBrowsingData.kindProperties
request.page = entityBrowsingData.entityPage

forward '/WEB-INF/pages/data/browse.gtpl'
