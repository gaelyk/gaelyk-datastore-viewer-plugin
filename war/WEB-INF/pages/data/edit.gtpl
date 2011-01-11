<% include '/WEB-INF/includes/data/header.gtpl' %>

<%
  def entity = request.entity
%>

<h2>Edit Entity: ${entity.kind}</h2>

<b>Decoded entity key:</b> ${entity.key.id}<br>
<b>Entity key:</b> ${com.google.appengine.api.datastore.KeyFactory.keyToString(entity.key)}
<br><br>
<table border="0">
  <tbody>
     <% request.kindProperties.each { property -> %>
     <tr>
        <td>
           <b>${property.key.name}</b><br>
           <table>
              <tbody>
                 <tr>
                    <td>value:</td>
                    <td>${entity.getProperty(property.key.name)}</td>
                 </tr>
                 <tr>
                    <td>type:</td>
                    <td>${property.getProperty("property_representation")}</td>
                 </tr>
              </tbody>
           </table>
        </td>
     </tr>
     <tr><td><br></td></tr>
     <% } %>
  </tbody>
</table>
<a href="/data/browse?kind=${entity.kind}">&lsaquo;&lsaquo; back</a>

<% include '/WEB-INF/includes/data/footer.gtpl' %>