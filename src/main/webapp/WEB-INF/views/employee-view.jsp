<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="view-container">
    <h3>Employee Details</h3>
    
    <div class="detail-card">
        <div class="detail-row">
            <div class="detail-label">First Name</div>
            <div class="detail-value">${employee.firstName}</div>
        </div>
        
        <div class="detail-row">
            <div class="detail-label">Last Name</div>
            <div class="detail-value">${employee.lastName}</div>
        </div>
        
        <div class="detail-row">
            <div class="detail-label">Date of Birth</div>
            <div class="detail-value">${employee.dateOfBirth}</div>
        </div>

        <div class="detail-row">
            <div class="detail-label">Department</div>
            <div class="detail-value">${employee.department}</div>
        </div>
        
        <div class="detail-row">
            <div class="detail-label">Salary</div>
            <div class="detail-value">${employee.salary} ${employee.currency}</div>
        </div>
        
        <div class="detail-row">
            <div class="detail-label">Manager</div>
            <div class="detail-value">${employee.managerName != null ? employee.managerName : 'None'}</div>
        </div>
    </div>
    
    <div class="button-group">
        <a href="/employees/list" class="btn-secondary">Back to List</a>
        <a href="/employees/edit/${employee.id}" class="btn-primary">Edit</a>
    </div>
</div>

