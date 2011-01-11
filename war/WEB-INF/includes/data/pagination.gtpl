<% def page = request.page %>
<% if(page.records.size() > 0) { %>
   <% if(page.offset > 0) { %>
      <a href="/data/browse?kind=${request.kind}&offset=${page.offset - page.limit}&limit=${page.limit}">&lsaquo;&lsaquo; Prev ${page.limit}</a>
   <% } else { %>
      <span style="color: gray;">&lsaquo;&lsaquo; Prev ${page.limit}</span>
   <% } %>
   &nbsp;
   <b>${page.offset + 1}-${page.offset + page.records.size()}</b>
   &nbsp;
   <% if(page.hasNextPage()) { %>
      <a href="/data/browse?kind=${request.kind}&offset=${page.offset + page.limit}&limit=${page.limit}">Next ${page.limit} &rsaquo;&rsaquo;</a>
   <% } else { %>
      <span style="color: gray;">Next ${page.limit} &rsaquo;&rsaquo;</span>
   <% } %>
<% } %>