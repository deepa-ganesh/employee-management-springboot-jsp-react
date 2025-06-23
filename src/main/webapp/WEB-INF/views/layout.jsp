<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title><tiles:insertAttribute name="title" /></title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />
</head>
<body>
<header>
    <h1>Employee Management</h1>
</header>

<nav class="main-nav">
    <a href="/employees/list">Employees</a>
    <a href="/employees/add">Add Employee</a>
</nav>

<div class="container">
    <tiles:insertAttribute name="body" />
</div>
</body>
</html>
