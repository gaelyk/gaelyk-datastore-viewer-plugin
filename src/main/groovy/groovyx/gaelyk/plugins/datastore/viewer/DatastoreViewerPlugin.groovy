package groovyx.gaelyk.plugins.datastore.viewer

import groovyx.gaelyk.plugins.PluginBaseScript
import groovyx.gaelyk.plugins.datastore.viewer.service.DatastoreViewerService

class DatastoreViewerPlugin extends PluginBaseScript {

    @Override public Object run() {
        binding {
            datastoreViewerService = new DatastoreViewerService()

            // Plugin library variable
            plugins = [
                datastoreviewer: [
                    version: "2.0-SNAPSHOT"]
            ]
        }

        routes {
            get  "/_ah/gaelyk-datastore-viewer",                   redirect: "/_ah/gaelyk-datastore-viewer/browse"
            get  "/_ah/gaelyk-datastore-viewer/",                  redirect: "/_ah/gaelyk-datastore-viewer/browse"
            get  "/_ah/gaelyk-datastore-viewer/browse",            forward:  "/groovyx/gaelyk/plugins/datastore/viewer/browseData.groovy"
            get  "/_ah/gaelyk-datastore-viewer/edit/@kind/@id",    forward:  "/groovyx/gaelyk/plugins/datastore/viewer/editData.groovy?kind=@kind&id=@id"
            post "/_ah/gaelyk-datastore-viewer/update",            forward:  "/groovyx/gaelyk/plugins/datastore/viewer/updateData.groovy"
            post "/_ah/gaelyk-datastore-viewer/delete",            forward:  "/groovyx/gaelyk/plugins/datastore/viewer/deleteData.groovy"
            get  "/_ah/gaelyk-datastore-viewer/create/@kind",      forward:  "/groovyx/gaelyk/plugins/datastore/viewer/createData.groovy?kind=@kind"
            post "/_ah/gaelyk-datastore-viewer/insert/@kind",      forward:  "/groovyx/gaelyk/plugins/datastore/viewer/insertData.groovy?kind=@kind"
        }
    }
}
