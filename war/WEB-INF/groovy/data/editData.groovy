import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory

log.info "Editing data"

def kind = params.kind
def id = Long.parseLong(params.id)

Key key = KeyFactory.createKey(kind, id)
def entity = datastore.get(key)
def kindProperties = datastore.getProperties(kind)
request.entity = entity
request.kindProperties = kindProperties

forward '/WEB-INF/pages/data/edit.gtpl'