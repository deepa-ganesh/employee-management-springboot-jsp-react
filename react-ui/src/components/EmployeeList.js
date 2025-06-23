import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getEmployees, deleteEmployee } from '../services/api';
import './Employee.css';

const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchEmployees = () => {
    setLoading(true);
    getEmployees()
      .then(response => {
        setEmployees(response.data);
      })
      .catch(error => {
        console.error('Error fetching employees:', error);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  const handleDelete = (id, firstName, lastName) => {
    if (window.confirm(`Are you sure you want to delete ${firstName} ${lastName}?`)) {
      setLoading(true);
      deleteEmployee(id)
        .then(() => {
          fetchEmployees();
        })
        .catch(error => {
          console.error('Error deleting employee:', error);
          alert('Failed to delete employee. Please try again.');
          setLoading(false);
        });
    }
  };

  return (
    <div className="employee-container">
      <div className="employee-card">
        <div className="card-header">
          <h2>Employee List</h2>
        </div>
      {loading && <div style={{ textAlign: 'center', padding: '20px' }}>Loading...</div>}
      <table className="employee-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
            <th>Date of Birth</th>
            <th>Salary</th>
            <th>Manager</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {employees.length > 0 ? (
            employees.map(emp => (
              <tr key={emp.id}>
                <td>{emp.id}</td>
                <td>{emp.firstName}</td>
                <td>{emp.lastName}</td>
                <td>{emp.department}</td>
                <td>{new Date(emp.dateOfBirth).toLocaleDateString('en-GB', {
                  day: '2-digit',
                  month: '2-digit',
                  year: 'numeric'
                })}</td>
                <td>
                  {Number(emp.salary).toLocaleString(undefined, {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                  })} {emp.currency}
                </td>
                <td>{emp.managerName || 'None'}</td>
                <td className="action-links">
                  <Link to={`/view/${emp.id}`}>View</Link>
                  <Link to={`/edit/${emp.id}`}>Edit</Link>
                  <button 
                    className="delete" 
                    onClick={() => handleDelete(emp.id, emp.firstName, emp.lastName)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="8">No employees found.</td>
            </tr>
          )}
        </tbody>
      </table>
      </div>
    </div>
  );
};

export default EmployeeList;
