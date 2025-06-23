<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>${employee.id != null ? 'Edit Employee' : 'Add Employee'}</h3>

<form:form method="post" modelAttribute="employee">
    <div class="form-group">
        <label for="firstName">First Name</label>
        <form:input path="firstName" id="firstName" />
    </div>
    <form:errors path="firstName" element="div" cssClass="error" delimiter="<br/>" />

    <div class="form-group">
        <label for="lastName">Last Name</label>
        <form:input path="lastName" id="lastName" />
    </div>
    <form:errors path="lastName" element="div" cssClass="error" delimiter="<br/>" />

    <div class="form-group">
        <label for="dateOfBirth">Date of Birth</label>
        <form:input path="dateOfBirth" type="date" id="dateOfBirth" />
    </div>
    <form:errors path="dateOfBirth" element="div" cssClass="error" delimiter="<br/>" />

    <div class="form-group">
        <label for="department">Department</label>
        <form:select path="department" id="department">
            <form:option value="" label="-- Select --" />
            <c:forEach items="${departments}" var="dept">
                <form:option value="${dept}" label="${dept.description}" />
            </c:forEach>
        </form:select>
    </div>
    <form:errors path="department" element="div" cssClass="error" delimiter="<br/>" />

    <div class="form-group">
        <label for="salary">Salary</label>
        <div class="salary-group">
            <form:input path="salary" id="salary" type="number" step="0.01" min="0" />
            <form:select path="currency">
                <form:option value="AED">AED</form:option>
                <form:option value="USD">USD</form:option>
                <form:option value="EUR">EUR</form:option>
                <form:option value="GBP">GBP</form:option>
                <form:option value="INR">INR</form:option>
                <form:option value="JPY">JPY</form:option>
            </form:select>
        </div>
    </div>
    <form:errors path="salary" element="div" cssClass="error" delimiter="<br/>" />

    <div class="form-group">
        <label for="manager">Manager</label>
        <form:select path="managerId">
            <form:option value="" label="-- None --" />
            <c:forEach items="${managers}" var="manager">
                <form:option value="${manager.id}" label="${manager.firstName} ${manager.lastName}" />
            </c:forEach>
        </form:select>
    </div>
    <form:errors path="managerId" cssClass="error" element="div" />

    <div class="button-group">
        <a href="/employees/list" class="btn-secondary">Back to List</a>
        <input type="submit" value="Save" />
    </div>
</form:form>

