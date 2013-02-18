<% import groovyx.gaelyk.plugins.datastore.viewer.data.PropertyRepresentation %>

<% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/header.gtpl' %>

<%
  def kind = request.kind
%>

<div class="page-header">
	<h2>Create Entity: ${kind}</h2>
</div>

<form action="/_ah/gaelyk-datastore-viewer/insert/${kind}" method="post">
	<p><span class="label notice">Notice</span> Enter information for the new entity below.</p>
	<div class="alert-message block-message info">
       <strong>Namespace:</strong>&nbsp;&nbsp;&nbsp;<input type="text" name="namespace" value="${request.namespace}">
    </div>
<fieldset>
     <% request.kindProperties.eachWithIndex { property, index -> %>
     <h4>${property.key.name}</h4>
     <div class="clearfix">
     	<label>Value:</label>
        <div class="input">
        	<input type="hidden" name="name_${index}" value="${property.key.name}">
            <input type="text" name="value_${index}" size="32" class="span5">
        </div>
     </div>
	 <div class="clearfix">
     	<label>Type:</label>
        <div class="input">
        	<select name="type_${index}" class="span5">
            	<% if(!property.getProperty("property_representation").contains('NULL')) { %>
                	<option value="${PropertyRepresentation.NULL}">Null</option>
                <% } %>
                <% property.getProperty("property_representation").each { representation -> %>
                	<% def propertyRepresentation = PropertyRepresentation.valueOf(representation) %>
                    	<option value="${propertyRepresentation.datastorePropertyType}" selected>${propertyRepresentation.datastorePropertyType.label}</option>
                    <% } %>
            </select>
        </div>
     </div>
     <% } %>
</fieldset>
<div class="actions">
	<input type="submit" value="Insert" name="Insert" class="btn primary">
	<input type="button" value="Cancel" name="Cancel" class="btn" onclick="javascript:document.location.href='/_ah/gaelyk-datastore-viewer/browse?kind=${kind}&namespace=${request.namespace}'">
</div>
</form>

<% include '/WEB-INF/includes/groovyx/gaelyk/plugins/datastore/viewer/footer.gtpl' %>