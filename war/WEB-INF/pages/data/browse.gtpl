<% import groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType %>

<% include '/WEB-INF/includes/data/header.gtpl' %>
<script type="text/javascript" src="/js/data/datastore-viewer.js"></script>

<% if(request.kind != null) { %>
<h2>Query</h2>

<form name="queryForm" action="/data/browse" method="GET">
<table>
   <tbody>
      <tr>
         <td>By Kind:</td>
         <td>
            <select id="kind" name="kind" onchange="javascript:document.queryForm.submit();">
               <% request.kinds.each { kind -> %>
                  <% if(request.kind == kind.key.name) { %>
                     <option value="${kind.key.name}" selected>${kind.key.name}</option>
                  <% } else { %>
                     <option value="${kind.key.name}">${kind.key.name}</option>
                  <% } %>
               <% } %>
            </select>
            <input type="button" name="create" value="Create" onclick="javascript:document.location.href='/data/create/' + document.getElementById('kind').value">
         </td>
      </tr>
   </tbody>
</table>
</form>
<form name="dataForm" action="/data/delete" method="POST">
<b>${request.kind} Entities</b><br><br>
<% if(request.kindProperties.size() > 0) { %>
<table border="1" class="striped">
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
         <td><a href="/data/edit/${request.kind}/${kind.key.id}">${kind.key.id}</a></td>
         <% request.kindProperties.each { property -> %>
         <% def propertyValue = kind.getProperty(property.key.name) %>
         <% def propertyType = propertyValue == null ? DatastorePropertyType.NULL : DatastorePropertyType.getPropertyTypeForJavaType(propertyValue.class) %>
            <td>
               ${propertyType.formatValue(propertyValue)}
               <% if(propertyType == DatastorePropertyType.DATASTORE_KEY) { %>
               (<a href="/data/edit/${propertyValue.kind}/${propertyValue.id}">${propertyValue.kind}:id=${propertyValue.id}</a>)
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
               <input type="submit" id="deleteButton" value="Delete" disabled>
             </div>
             <div class="right">
                <% include '/WEB-INF/includes/data/pagination.gtpl' %>
             </div>
         </td>
      </tr>
   </tfoot>
</table>
<input type="hidden" name="kind" value="${request.kind}">
</form>
<% } else { %>
   No records available for this entity kind.
<% } %>
<% } else { %>
   No entities available in datastore.
<% } %>

<% include '/WEB-INF/includes/data/footer.gtpl' %>