import groovyx.gaelyk.plugins.datastore.viewer.service.DatastoreViewerService

log.info "Registering datastore viewer plugin..."

binding {
    datastoreViewerService = new DatastoreViewerService()

    // Plugin library variable
    plugins = [
        datastoreviewer: [
            version: "0.4"]
        ]
}

routes {
    get  "/data",                   redirect: "/data/browse"
    get  "/data/browse",            forward:  "/data/browseData.groovy"
    get  "/data/edit/@kind/@id",    forward:  "/data/editData.groovy?kind=@kind&id=@id"
    post "/data/update",            forward:  "/data/updateData.groovy"
    post "/data/delete",            forward:  "/data/deleteData.groovy"
    get  "/data/create/@kind",      forward:  "/data/createData.groovy?kind=@kind"
    post "/data/insert/@kind",      forward:  "/data/insertData.groovy?kind=@kind"
}