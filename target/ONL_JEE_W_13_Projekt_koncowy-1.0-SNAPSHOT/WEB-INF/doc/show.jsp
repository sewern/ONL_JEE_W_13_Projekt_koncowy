<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Szczegóły zamówienia</title>
</head>
<body>
<header>
  <a href="/">Strona główna</a>
</header>
<main>
  <nav>
    <a href="/doc">Powrót do listy zamówień</a>
    <c:choose>
      <c:when test="${option == 'success'}">
        <h4>Dokument został zapisany.</h4>
      </c:when>
      <c:when test="${option == 'delete'}">
        <h4>Proszę potwierdzić usunięcie dokumentu!</h4>
        <form method="post">
          <button type="submit" name="delete">Tak, potwierdzam.</button>
        </form>
      </c:when>
    </c:choose>

    <br>
    <table border="1">
      <thead>
      <th>Numer dokumentu</th>
      <th>Data wystawienia</th>
      <th>Wystawił</th>
      </thead>
      <tbody>
        <tr>
          <td><c:out value="${doc.number}"/></td>
          <td><c:out value="${doc.issueDate}"/></td>
          <td><c:out value="${doc.issuer}"/></td>
        </tr>
      </tbody>
    </table>

    <br>
    <table border="1">
      <thead>
      <th>Lp.</th>
      <th>Nazwa asortymentu</th>
      <th>Ilość [szt]</th>
      </thead>
      <tbody>
      <c:forEach items="${items}" var="docItem" varStatus="vs">
        <tr>
          <td>${vs.count}.</td>
          <td><c:out value="${docItem.name}"/></td>
          <td><c:out value="${docItem.quantity}"/></td>
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
