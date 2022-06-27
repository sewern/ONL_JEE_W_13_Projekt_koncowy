<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Lista zamówień</title>
</head>
<body>
<header>
  <a href="/">Strona główna</a>
</header>
<main>
  <nav>
    <a href="/doc/edit">Dodaj zamówienie</a>
    <br>
    <table border="1">
      <thead>
      <th>Numer dokumentu</th>
      <th>Data wystawienia</th>
      <th>Wystawił</th>
      <th></th>
      </thead>
      <tbody>
      <c:forEach items="${docs}" var="doc">
        <tr>
          <td><c:out value="${doc.number}"/></td>
          <td><c:out value="${doc.issueDate}"/></td>
          <td><c:out value="${doc.issuer}"/></td>
          <td>
            <a href="<c:out value="/doc/show/${doc.id}"/>">Pokaż</a>
            <a href="<c:out value="/doc/edit/${doc.id}"/>">Edytuj</a>
            <a href="<c:out value="/doc/show/${doc.id}/delete"/>">Usuń</a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </nav>
</main>
<aside></aside>
<footer>
</footer>
</body>
</html>
