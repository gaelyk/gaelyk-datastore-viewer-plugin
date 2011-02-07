log.info "Creating data"

def kind = params.kind
def selectedNamespace = params.namespace

def kindProperties = datastore.getProperties(kind)
request.kind = kind
request.kindProperties = kindProperties
request.namespace = selectedNamespace

forward '/WEB-INF/pages/data/create.gtpl'