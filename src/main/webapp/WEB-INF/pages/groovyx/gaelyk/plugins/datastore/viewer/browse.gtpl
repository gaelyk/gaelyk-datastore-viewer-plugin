<% import groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType %>

<% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/header.gtpl' %>

<% if(request.kind != null) { %>
<div class="page-header">
    <h2>Query</h2>
</div>

<form name="queryForm" action="/_ah/gaelyk-datastore-viewer/browse" method="GET">
    <fieldset>
        <div class="clearfix">
            <label for="normalSelect">By Kind:</label>
            <div class="input">
                <select id="kind" name="kind" class="span5">
                   <% request.kinds.each { kind -> %>
                      <% if(request.kind == kind.key.name) { %>
                         <option value="${kind.key.name}" selected>${kind.key.name}</option>
                      <% } else { %>
                         <option value="${kind.key.name}">${kind.key.name}</option>
                      <% } %>
                   <% } %>
                </select>
            </div>
        </div>
        <div class="clearfix">
            <label>Namespace:</label>
            <div class="input">
                <input type="text" id="namespace" name="namespace" value="${request.namespace}" class="span5">
            </div>
        </div>
        <div class="actions">
            <input type="button" name="query" value="Query" class="btn primary" onclick="javascript:document.queryForm.submit();">
            <% if(request.kinds.size() > 0) { %>
               <input type="button" name="create" value="Create" class="btn" onclick="javascript:document.location.href='/_ah/gaelyk-datastore-viewer/create/' + document.getElementById('kind').value + '?namespace=' + document.getElementById('namespace').value">
            <% } else { %>
               <input type="button" name="create" value="Create" class="btn" disabled>
            <% } %>
        </div>
    </fieldset>
</form>
<form name="dataForm" action="/_ah/gaelyk-datastore-viewer/delete" method="POST">
<h4>${request.kind} Entities</h4><br><br>
<% if(request.kindProperties.size() > 0) { %>
<table class="table table-striped">
   <thead>
      <tr>
         <th><input type="checkbox" id="idToggle" name="idToggle" onclick="javascript:toggleSelection('idToggle', 'ids');"></th>
         <th>ID</th>
         <% request.kindProperties.each { property -> %>
            <th>${property.key.name}<br></th>
         <% } %>
      </tr>
   </thead>
   <tbody>
      <% request.page.records.eachWithIndex { kind, index -> %>
      <% def trClass = index % 2 == 0 ? 'even' : 'odd' %>
      <tr class="${trClass}">
         <td><input type="checkbox" name="ids" value="${kind.key.id}" onclick="javascript:checkDeleteButtonStatus('ids');"></td>
         <td><a href="/_ah/gaelyk-datastore-viewer/edit/${request.kind}/${kind.key.id}?namespace=${request.namespace}">${kind.key.id}</a></td>
         <% request.kindProperties.each { property -> %>
         <% def propertyValue = kind.getProperty(property.key.name) %>
         <% def propertyType = propertyValue == null ? DatastorePropertyType.NULL : DatastorePropertyType.getPropertyTypeForJavaType(propertyValue.class) %>
            <td>
               ${propertyType.formatValue(propertyValue)}
               <% if(propertyType == DatastorePropertyType.DATASTORE_KEY) { %>
               (<a href="/_ah/gaelyk-datastore-viewer/edit/${propertyValue.kind}/${propertyValue.id}">${propertyValue.kind}:id=${propertyValue.id}</a>)
               <% } %>
            </td>
         <% } %>
      </tr>
      <% } %>
   </tbody>
   <tfoot>
      <tr>
         <td colspan="7">
            <div class="left">
               <input type="submit" id="deleteButton" value="Delete" class="btn danger" disabled>
             </div>
             <div class="right">
                <% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/pagination.gtpl' %>
             </div>
         </td>
      </tr>
   </tfoot>
</table>
<input type="hidden" name="kind" value="${request.kind}">
<input type="hidden" name="namespace" value="${request.namespace}">
</form>
<% } else { %>
   <div class="alert-message block-message warning">No records available for this entity kind.</div>
<% } %>
<% } else { %>
   <div class="alert-message block-message warning">No entities available in datastore.</div>
<% } %>

<% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/footer.gtpl' %>