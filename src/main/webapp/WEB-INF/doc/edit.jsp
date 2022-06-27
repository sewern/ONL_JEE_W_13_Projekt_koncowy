<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Document</title>
</head>
<body>
<form:form modelAttribute="doc" path="/edit" method="post">
  ${completeError}

  <form:errors path="*">
    <div class="msg error">
      <h4>Proszę poprawić błędy!</h4>
    </div>
  </form:errors>

  <fieldset>
    <div class="field">
      <div class="input">
        <button type="submit" class="button primary"
          <c:choose>
            <c:when test="${doc.editIdx == doc.size}">
              name="resolve" value="Save">Zapisz dokument
            </c:when>
            <c:otherwise>
              name="editIdxNew" value="${doc.size}">Zakończ edycję pozycji
            </c:otherwise>
          </c:choose>
        </button>
        <button type="submit" class="button" name="resolve" value="Cancel">Powrót bez zapisu</button>
      </div>
    </div>
  </fieldset>

  <fieldset><legend>Header</legend>
    <div class="field">
      <div class="input">
        <form:label path="number">Numer dokumentu:</form:label>
        <form:input path="number" /><br>
        <form:errors path="number" cssClass="error" element="div" />

        <form:label path="issueDate">Data wystawienia:</form:label>
        <form:input path="issueDate" type="date" /><br>
        <form:errors path="issueDate" cssClass="error" element="div" />

        <form:label path="issuer">Dokument wystawił:</form:label>
        <form:input path="issuer" /><br>
        <form:errors path="issuer" cssClass="error" element="div" />

        Ilość pozycji: ${doc.size}<br>
        <form:hidden path="editIdx" />
      </div>
    </div>
  </fieldset>

  <fieldset><legend>Items</legend>
    <div class="help icon astrisk">required</div>

    <c:forEach items="${doc.items}" varStatus="vs">
      <div class="field">
        <div class="label required">
          <form:label path="items[${vs.index}].name" cssErrorClass="invalid">Name</form:label>
        </div>
        <div class="input">
          <c:set var="readOnly" value="${vs.index!= doc.editIdx}"/>
          <c:choose>
            <c:when test="${readOnly}">
              ${vs.count}.
            </c:when>
            <c:otherwise>
              #
            </c:otherwise>
          </c:choose>

          <form:input path="items[${vs.index}].name" cssErrorClass="invalid" readonly="${readOnly}" />
          <form:label path="items[${vs.index}].name" cssErrorClass="icon invalid" />
          <form:errors path="items[${vs.index}].name" cssClass="inline_invalid" />
            <%-- <input type="submit" class="button" name="modify" value="<c:out value="${vs.index}"/>" /> --%>
          <c:if test="${readOnly}">
            <button type="submit" class="button" name="editIdxNew" value="<c:out value="${vs.index}"/>">Włącz edycję</button>
          </c:if>
          <button type="submit" class="button" name="remove" value="<c:out value="${vs.index}"/>">Usuń pozycję</button>
        </div>
      </div>
      <form:hidden path="items[${vs.index}].id" />
      <hr />
    </c:forEach>

    <div class="field">
      <div class="input">
        <button type="submit" class="button primary" name="add">Dodaj pozycję</button>
        <button type="submit" class="button" name="remove">Usuń wszystkie pozycje</button>
      </div>
    </div>

  </fieldset>

</form:form>
</body>
</html>
