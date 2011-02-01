<% import groovyx.gaelyk.plugins.datastore.viewer.data.PropertyRepresentation %>

<% include '/WEB-INF/includes/data/header.gtpl' %>

<%
  def kind = request.kind
%>

<h2>Create Entity: ${kind}</h2>

<form action="/data/insert/${kind}" method="post">
<table border="0">
  <tbody>
     <% request.kindProperties.eachWithIndex { property, index -> %>
     <tr>
        <td>
           <b>${property.key.name}</b><br>
           <table>
              <tbody>
                 <tr>
                    <td>Value:</td>
                    <td>
                       <input type="hidden" name="name_${index}" value="${property.key.name}">
                       <input type="text" name="value_${index}" size="32">
                    </td>
                 </tr>
                 <tr>
                    <td>Type:</td>
                    <td>
                       <select name="type_${index}">
                          <option value="${PropertyRepresentation.NULL}">Null</option>
                          <% property.getProperty("property_representation").each { representation -> %>
                             <% def propertyRepresentation = PropertyRepresentation.valueOf(representation) %>
                             <option value="${propertyRepresentation.datastorePropertyType}" selected>${propertyRepresentation.datastorePropertyType.label}</option>
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
<input type="submit" value="Insert" name="Insert">&nbsp;<input type="button" value="Cancel" name="Cancel" onclick="javascript:document.location.href='/data/browse?kind=${kind}'">
</form>

<% include '/WEB-INF/includes/data/footer.gtpl' %>