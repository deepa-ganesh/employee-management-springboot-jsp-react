import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getEmployeeById } from '../services/api';
import './Employee.css';

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('en-GB', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  });
};

const EmployeeView = () => {
  const { id } = useParams();
  const [employee, setEmployee] = useState(null);
  const [manager, setManager] = useState(null);

  useEffect(() => {
    getEmployeeById(id)
      .then(response => {
        setEmployee(response.data);
        if (response.data.managerId) {
          return getEmployeeById(response.data.managerId);
        }
      })
      .then(managerResponse => {
        if (managerResponse) {
          setManager(managerResponse.data);
        }
      })
      .catch(error => {
        console.error('Error fetching employee or manager:', error);
      });
  }, [id]);

  if (!employee) return <div>Loading...</div>;

  return (
    <div className="employee-container detail-view">
      <div className="employee-card">
        <div className="card-header">
          <h2>Employee Details</h2>
          <div className="employee-id">ID: {employee.id}</div>
        </div>

        <div className="card-content">
          <div className="detail-section">
            <div className="detail-row">
              <span className="detail-label">First Name</span>
              <span className="detail-value">{employee.firstName}</span>
            </div>
            <div className="detail-row">
              <span className="detail-label">Last Name</span>
              <span className="detail-value">{employee.lastName}</span>
            </div>
            <div className="detail-row">
              <span className="detail-label">Date of Birth</span>
              <span className="detail-value">{formatDate(employee.dateOfBirth)}</span>
            </div>
            <div className="detail-row">
              <span className="detail-label">Department</span>
              <span className="detail-value">{employee.department}</span>
            </div>
            <div className="detail-row">
              <span className="detail-label">Salary</span>
              <span className="detail-value salary">
                {Number(employee.salary).toLocaleString(undefined, {
                  minimumFractionDigits: 2,
                  maximumFractionDigits: 2
                })} {employee.currency}
              </span>
            </div>
            <div className="detail-row">
              <span className="detail-label">Manager</span>
              <span className="detail-value">
                {manager ? `${manager.firstName} ${manager.lastName}` : (employee.managerId ? 'Loading...' : 'None')}
              </span>
            </div>
          </div>
        </div>

        <div className="card-footer">
          <Link to="/" className="back-link">← Back to List</Link>
          <Link to={`/edit/${employee.id}`} className="edit-link">Edit Employee</Link>
        </div>
      </div>
    </div>
  );
};

export default EmployeeView;
