import groovyx.gaelyk.plugins.datastore.viewer.data.EntityBrowsingData

log.info "Deleting data"

def kind = params.kind
def ids = params.ids

if(ids) {
    // Treat single ID as array
    if(!ids instanceof List) {
        ids = [ids]
    }
    
    ids.each { id ->
        datastoreViewerService.deleteEntity(kind, id.toLong())
    }
}

def offset = params.offset?.toInteger()
def limit = params.limit?.toInteger()

EntityBrowsingData entityBrowsingData = datastoreViewerService.getEntityBrowsingData(kind, offset, limit)
request.kinds = entityBrowsingData.kinds
request.kind = entityBrowsingData.selectedKind
request.kindProperties = entityBrowsingData.kindProperties
request.page = entityBrowsingData.entityPage

forward '/WEB-INF/pages/data/browse.gtpl'