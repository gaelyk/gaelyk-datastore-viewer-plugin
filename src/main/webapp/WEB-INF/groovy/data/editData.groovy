package data

import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory

log.info "Editing data"

def kind = params.kind
def selectedNamespace = params.namespace
def id = Long.parseLong(params.id)

selectedNamespace = selectedNamespace ? selectedNamespace : ''
request.namespace = selectedNamespace

namespace.of(selectedNamespace) {
    Key key = KeyFactory.createKey(kind, id)
    def entity = datastore.get(key)
    def kindProperties = datastore.getProperties(kind)
    request.entity = entity
    request.kindProperties = kindProperties
}

forward '/WEB-INF/pages/data/edit.gtpl'