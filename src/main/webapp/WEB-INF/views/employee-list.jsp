<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h3>Employees List</h3>

<table border="1">
  <thead>
  <tr>
    <th>ID</th><th>Name</th><th>Department</th><th>Date of Birth</th><th>Salary</th><th>Manager</th><th>Actions</th>
  </tr>
  </thead>
  <tbody>
    <c:forEach items="${employees}" var="employee">
      <tr>
        <td>${employee.id}</td>
        <td>${employee.firstName} ${employee.lastName}</td>
        <td>${employee.department}</td>
        <td>${employee.dateOfBirth}</td>
        <td>${employee.salary} ${employee.currency}</td>
        <td>${employee.managerName}</td>
        <td>
          <a href="/employees/view/${employee.id}">View</a> |
          <a href="/employees/edit/${employee.id}">Edit</a> |
          <a href="#" onclick="deleteEmployee(${employee.id}, '${employee.firstName} ${employee.lastName}'); return false;" class="delete-link">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<script>
function deleteEmployee(id, name) {
    if (confirm('Are you sure you want to delete employee ' + name + '?')) {
        window.location.href = '/employees/delete/' + id;
    }
}
</script>

