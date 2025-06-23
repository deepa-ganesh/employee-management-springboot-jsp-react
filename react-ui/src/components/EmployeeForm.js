import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { createEmployee, getEmployeeById, updateEmployee, getEmployees } from '../services/api';
import './Employee.css';

const initialState = {
  firstName: '',
  lastName: '',
  dateOfBirth: '',
  department: '',
  salary: '',
  currency: 'AED',
  managerId: ''
};

const EmployeeForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const [employee, setEmployee] = useState(initialState);

  const [errors, setErrors] = useState({});
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    if (!isEdit) {
      setEmployee(initialState);
      setErrors({});
    } else {
      getEmployeeById(id).then(response => {
        setEmployee(response.data);
      });
    }

    getEmployees().then(response => {
      const all = response.data;
      setEmployees(isEdit ? all.filter(e => e.id !== parseInt(id)) : all);
    });
  }, [id, isEdit]);

  const validate = () => {
    const err = {};
    if (!employee.firstName || employee.firstName.length < 1 || employee.firstName.length > 50)
      err.firstName = 'First name must be between 1 and 50 characters';
    if (!employee.lastName || employee.lastName.length < 1 || employee.lastName.length > 50)
      err.lastName = 'Last name must be between 1 and 50 characters';
    if (!employee.dateOfBirth)
      err.dateOfBirth = 'Date of Birth is required';
    else if (new Date(employee.dateOfBirth) >= new Date())
      err.dateOfBirth = 'Date of Birth must be in the past';
    if (!employee.department)
      err.department = 'Department is required';
    if (!employee.salary || parseFloat(employee.salary) <= 0)
      err.salary = 'Salary must be a positive number';

    setErrors(err);
    return Object.keys(err).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validate()) return;

    const action = isEdit ? updateEmployee(id, employee) : createEmployee(employee);
    action
      .then(() => navigate('/'))
      .catch(error => {
        if (error.response && error.response.data) {
          const backendErrors = error.response.data;
          if (backendErrors.find(err => err.defaultMessage?.includes('circular reference'))) {
            setErrors(prev => ({
              ...prev,
              managerId: 'Cannot assign this manager as it would create a circular reference'
            }));
          } else {
            setErrors(prev => ({
              ...prev,
              managerId: backendErrors.map(err => err.defaultMessage).join(', ')
            }));
          }
        }
      });
  };

  return (
    <div className="employee-container form-view">
      <div className="employee-card">
        <div className="card-header">
          <h2>{isEdit ? 'Edit' : 'Add'} Employee</h2>
          {isEdit && <div className="employee-id">ID: {id}</div>}
        </div>
        <form onSubmit={handleSubmit}>
          <div className="card-content">
            <div className="detail-section">
              <div className="detail-row">
                <span className="detail-label">First Name</span>
                <span className="detail-value">
                  <input name="firstName" value={employee.firstName} onChange={handleChange} />
                  {errors.firstName && <div className="error-message">{errors.firstName}</div>}
                </span>
              </div>

              <div className="detail-row">
                <span className="detail-label">Last Name</span>
                <span className="detail-value">
                  <input name="lastName" value={employee.lastName} onChange={handleChange} />
                  {errors.lastName && <div className="error-message">{errors.lastName}</div>}
                </span>
              </div>

              <div className="detail-row">
                <span className="detail-label">Date of Birth</span>
                <span className="detail-value">
                  <input type="date" name="dateOfBirth" value={employee.dateOfBirth} onChange={handleChange} />
                  {errors.dateOfBirth && <div className="error-message">{errors.dateOfBirth}</div>}
                </span>
              </div>

              <div className="detail-row">
                <span className="detail-label">Department</span>
                <span className="detail-value">
                  <select name="department" value={employee.department} onChange={handleChange}>
                    <option value="">--Select--</option>
                    <option value="HR">HR</option>
                    <option value="Finance">Finance</option>
                    <option value="Engineering">Engineering</option>
                    <option value="Marketing">Marketing</option>
                  </select>
                  {errors.department && <div className="error-message">{errors.department}</div>}
                </span>
              </div>

              <div className="detail-row">
                <span className="detail-label">Salary</span>
                <span className="detail-value">
                  <div className="salary-group">
                    <input 
                      type="number" 
                      name="salary" 
                      value={employee.salary} 
                      onChange={handleChange} 
                    />
                    <select 
                      name="currency" 
                      value={employee.currency} 
                      onChange={handleChange}
                    >
                      <option value="AED">AED</option>
                      <option value="USD">USD</option>
                      <option value="EUR">EUR</option>
                      <option value="GBP">GBP</option>
                      <option value="INR">INR</option>
                      <option value="JPY">JPY</option>
                    </select>
                  </div>
                  {errors.salary && <div className="error-message">{errors.salary}</div>}
                </span>
              </div>

              <div className="detail-row">
                <span className="detail-label">Manager</span>
                <span className="detail-value">
                  <select name="managerId" value={employee.managerId || ''} onChange={handleChange}>
                    <option value="">--None--</option>
                    {employees.map(emp => (
                      <option key={emp.id} value={emp.id}>
                        {emp.firstName} {emp.lastName}
                      </option>
                    ))}
                  </select>
                  {errors.managerId && <div className="error-message">{errors.managerId}</div>}
                </span>
              </div>
            </div>
          </div>

          <div className="card-footer">
            <Link to="/" className="back-link">Cancel</Link>
            <button type="submit" className="edit-link">{isEdit ? 'Update' : 'Add'} Employee</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EmployeeForm;
