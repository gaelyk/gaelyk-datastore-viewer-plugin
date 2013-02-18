package data

import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType

log.info "Updating data"

def encodedKey = params.key
def numberOfFields = params.size() - 1 == 0 ? 0 : (params.size() - 1) / 3

Key key = KeyFactory.stringToKey(encodedKey)
def entity = datastore.get(key)

for(i in 0..numberOfFields - 1) {
    def name = params["name_${i}"]
    def value = params["value_${i}"]
    def type = params["type_${i}"]

    DatastorePropertyType datastorePropertyType = DatastorePropertyType.valueOf(type)
    entity[name] = datastorePropertyType.parseValue(value)
}

entity.save()

redirect "/data/browse?kind=${entity.kind}&namespace=${entity.namespace}"