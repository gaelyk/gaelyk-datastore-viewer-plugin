<% import groovyx.gaelyk.plugins.datastore.viewer.data.DatastorePropertyType %>

<% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/header.gtpl' %>

<%
  def entity = request.entity
  def entityKey = com.google.appengine.api.datastore.KeyFactory.keyToString(entity.key)
%>

<div class="page-header">
	<h2>Edit Entity: ${entity.kind}</h2>
</div>

<form action="/_ah/gaelyk-datastore-viewer/update" method="post">
	<p><span class="label notice">Notice</span> Enter information for the entity below. If you'd like to change a property's type, set it to Null, save the entity, edit the entity again, and change the type.</p>
<div class="alert-message block-message info">
	<strong>Decoded entity key:</strong> ${entity.key.id}<br><br>
	<strong>Entity key:</strong> ${entityKey}
</div>
<input type="hidden" name="key" value="${entityKey}">
<fieldset>
     <% request.kindProperties.eachWithIndex { property, index -> %>
     <% def propertyValue = entity.getProperty(property.key.name) %>
     <% def propertyType = propertyValue == null ? DatastorePropertyType.NULL : DatastorePropertyType.getPropertyTypeForJavaType(propertyValue.class) %>
     <% def formattedPropertyValue = propertyType == DatastorePropertyType.NULL ? '' : propertyType.formatValue(propertyValue) %>
     <h4>${property.key.name}</h4><span id="hint_${index}" class="hint"><% if(propertyType.hint != null) { %>${propertyType.hint}<% } %></span>
     <div class="clearfix">
     	<label>Value:</label>
        <div class="input">
        	<input type="hidden" name="name_${index}" value="${property.key.name}">
            <input type="text" name="value_${index}" size="32" value="${formattedPropertyValue}" class="span5">
            <% if(propertyType == DatastorePropertyType.DATASTORE_KEY) { %>
            	(<a href="/_ah/gaelyk-datastore-viewer/edit/${propertyValue.kind}/${propertyValue.id}">${propertyValue.kind}:id=${propertyValue.id}</a>)
           	<% } %>
        </div>
     </div>
     <div class="clearfix">
     	<label>Type:</label>
        <div class="input">
        	<select name="type_${index}" class="span5">
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
         </div>
       </div>
	<% } %>
</fieldset>
<div class="actions">
	<input type="submit" value="Update" name="Update" class="btn primary">
	<input type="button" value="Cancel" name="Cancel" class="btn" onclick="javascript:document.location.href='/_ah/gaelyk-datastore-viewer/browse?kind=${entity.kind}&namespace=${request.namespace}'">
</div>
</form>

<% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/footer.gtpl' %>