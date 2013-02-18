<% def page = request.page %>
<% if(page.records.size() > 0) { %>
   <% if(page.offset > 0) { %>
      <a href="/_ah/gaelyk-datastore-viewer/browse?kind=${request.kind}&offset=${page.offset - page.limit}&limit=${page.limit}&namespace=${request.namespace}">&lsaquo;&lsaquo; Prev ${page.limit}</a>
   <% } else { %>
      <span style="color: gray;">&lsaquo;&lsaquo; Prev ${page.limit}</span>
   <% } %>
   &nbsp;
   <b>${page.offset + 1}-${page.offset + page.records.size()}</b>
   &nbsp;
   <% if(page.hasNextPage()) { %>
      <a href="/_ah/gaelyk-datastore-viewer/browse?kind=${request.kind}&offset=${page.offset + page.limit}&limit=${page.limit}&namespace=${request.namespace}">Next ${page.limit} &rsaquo;&rsaquo;</a>
   <% } else { %>
      <span style="color: gray;">Next ${page.limit} &rsaquo;&rsaquo;</span>
   <% } %>
<% } %>