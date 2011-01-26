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
     <% def propertyType = groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType.getPropertyTypeForJavaType(propertyValue.class) %>
     <tr>
        <td>
           <b>${property.key.name}</b><br>
           <table>
              <tbody>
                 <tr>
                    <td>Value:</td>
                    <td>
                       <input type="hidden" name="name_${index}" value="${property.key.name}">
                       <input type="text" name="value_${index}" size="32" value="${propertyType.formatValue(propertyValue)}">
                       <input type="hidden" name="type_${index}" value="${propertyType}">
                    </td>
                 </tr>
                 <tr>
                    <td>Type:</td>
                    <td>${propertyType.label}</td>
                 </tr>
              </tbody>
           </table>
        </td>
     </tr>
     <tr><td><br></td></tr>
     <% } %>
  </tbody>
</table>
<input type="submit" value="Update" name="Update">&nbsp;<input type="button" value="Cancel" name="Cancel" onclick="javascript:document.location.href='/data/browse?kind=${entity.kind}'">
</form>

<% include '/WEB-INF/includes/data/footer.gtpl' %>