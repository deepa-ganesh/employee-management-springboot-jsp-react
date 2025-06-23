import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Header.css';

const Header = () => {
  const location = useLocation();

  return (
    <header className="header">
      <div className="header-content">
        <div className="logo">
          <Link to="/">Employee Management</Link>
        </div>
        <nav className="nav-menu">
          <Link 
            to="/" 
            className={location.pathname === '/' ? 'active' : ''}
          >
          Employees
          </Link>
          <Link 
            to="/add" 
            className={location.pathname === '/add' ? 'active' : ''}
          >
            Add Employee
          </Link>
        </nav>
      </div>
    </header>
  );
};

export default Header;

