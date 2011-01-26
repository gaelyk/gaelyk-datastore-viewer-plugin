import groovyx.gaelyk.logging.GroovyLogger
import groovyx.gaelyk.plugins.datastore.viewer.category.MetaDataCategory
import groovyx.gaelyk.plugins.datastore.viewer.service.DatastoreViewerService

def log = new GroovyLogger("datastoreViewerPlugin")
log.info "Registering datastore viewer plugin..."

binding {
    datastoreViewerService = new DatastoreViewerService()

    // Plugin library variable
    plugins = [
        datastoreviewer: [
            version: "0.2"]
        ]        
}

routes {
    get  "/data/browse",            forward:  "/data/browseData.groovy"
    get  "/data/browse?kind=@kind", forward:  "/data/browseData.groovy?kind=@kind"
    get  "/data/edit/@kind/@id",    forward:  "/data/editData.groovy?kind=@kind&id=@id"
    post "/data/delete",            forward:  "/data/deleteData.groovy"
    post "/data/update",            forward:  "/data/updateData.groovy"
}

categories MetaDataCategory