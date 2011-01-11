import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key
import groovyx.gaelyk.plugins.datastore.viewer.category.MetaDataCategory

log.info "Editing data"

def kind = params.kind
def id = Long.parseLong(params.id)

Key key = KeyFactory.createKey(kind, id)
def entity = datastore.get(key)
request.entity = entity

use(MetaDataCategory) {
    def kindProperties = datastore.getProperties(kind)
    request.kindProperties = kindProperties
}

forward '/WEB-INF/pages/data/edit.gtpl'