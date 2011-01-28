log.info "Creating data"

def kind = params.kind

def kindProperties = datastore.getProperties(kind)
request.kind = kind
request.kindProperties = kindProperties

forward '/WEB-INF/pages/data/create.gtpl'