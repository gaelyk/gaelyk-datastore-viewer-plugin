import com.google.appengine.api.datastore.Entity
import groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType

log.info "Inserting data"

def kind = params.kind
def selectedNamespace = params.namespace
def numberOfFields = params.size() - 1 == 0 ? 0 : (params.size() - 1) / 3

namespace.of(selectedNamespace) {
    def entity = new Entity(kind)

    for(i in 0..numberOfFields - 1) {
        def name = params["name_${i}"]
        def value = params["value_${i}"]
        def type = params["type_${i}"]

        DatastorePropertyType datastorePropertyType = DatastorePropertyType.valueOf(type)
        entity[name] = datastorePropertyType.parseValue(value)
    }

    entity.save()
}

redirect "/data/browse?kind=${kind}&namespace=${selectedNamespace}"