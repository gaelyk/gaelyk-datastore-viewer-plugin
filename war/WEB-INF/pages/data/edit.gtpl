<% import groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType %>

<% include '/WEB-INF/includes/data/header.gtpl' %>

<%
  def entity = request.entity
  def entityKey = com.google.appengine.api.datastore.KeyFactory.keyToString(entity.key)
%>

<h2>Edit Entity: ${entity.kind}</h2>

<form action="/data/update" method="post">
<b>Decoded entity key:</b> ${entity.key.id}<br>
<b>Entity key:</b> ${entityKey}
<input type="hidden" name="key" value="${entityKey}">
<br><br>
<table border="0">
  <tbody>
     <% request.kindProperties.eachWithIndex { property, index -> %>
     <% def propertyValue = entity.getProperty(property.key.name) %>
     <% def propertyType = propertyValue == null ? DatastorePropertyType.NULL : DatastorePropertyType.getPropertyTypeForJavaType(propertyValue.class) %>
     <% def formattedPropertyValue = propertyType == DatastorePropertyType.NULL ? '' : propertyType.formatValue(propertyValue) %>
     <tr>
        <td>
           <b>${property.key.name}</b><span id="hint_${index}" class="hint"><% if(propertyType.hint != null) { %>${propertyType.hint}<% } %></span><br>
           <table>
              <tbody>
                 <tr>
                    <td>Value:</td>
                    <td>
                       <input type="hidden" name="name_${index}" value="${property.key.name}">
                       <input type="text" name="value_${index}" size="32" value="${formattedPropertyValue}">
                       <% if(propertyType == DatastorePropertyType.DATASTORE_KEY) { %>
                          (<a href="/data/edit/${propertyValue.kind}/${propertyValue.id}">${propertyValue.kind}:id=${propertyValue.id}</a>)
                       <% } %>
                    </td>
                 </tr>
                 <tr>
                    <td>Type:</td>
                    <td>
                       <select name="type_${index}">
                          <% if(propertyType != DatastorePropertyType.NULL) { %>
                             <option value="${DatastorePropertyType.NULL}">Null</option>
                             <option value="${propertyType}" selected>${propertyType.label}</option>
                          <% } else { %>
                             <% DatastorePropertyType.values().each { datastorePropertyType -> %>
                                <% if(datastorePropertyType == propertyType) { %>
                                   <option value="${datastorePropertyType}" selected>${datastorePropertyType.label}</option>
                                <% } else { %>
                                   <option value="${datastorePropertyType}">${datastorePropertyType.label}</option>
                                <% } %>
                             <% } %>
                          <% } %>
                       </select>
                    </td>
                 </tr>
              </tbody>
           </table>
        </td>
     </tr>
     <tr><td><br></td></tr>
     <% } %>
  </tbody>
</table>
<input type="submit" value="Update" name="Update">&nbsp;<input type="button" value="Cancel" name="Cancel" onclick="javascript:document.location.href='/data/browse?kind=${entity.kind}&namespace=${request.namespace}'">
</form>

<% include '/WEB-INF/includes/data/footer.gtpl' %>