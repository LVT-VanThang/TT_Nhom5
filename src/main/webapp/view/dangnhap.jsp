
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Đăng nhập hệ thống</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/style.css" />
  <style>
      .error-msg { color: red; font-style: italic; margin-bottom: 10px; }
  </style>
</head>
<body>
  <div class="login-container">
    <h2>📚 Quản lý Thư viện</h2>
    
    <div class="error-msg">${baoLoi}</div>

    <form action="${pageContext.request.contextPath}/DangNhap" method="post">
    
      <label for="username">Tên đăng nhập</label>
      <input type="text" id="username" name="user" placeholder="Nhập tên đăng nhập" required />

      <label for="password">Mật khẩu</label>
      <input type="password" id="password" name="pass" placeholder="Nhập mật khẩu" required />

      <button type="submit">🔐 Đăng nhập</button>
    </form>
    
    <p class="note">Chỉ dành cho Quản trị viên và Thủ thư</p>
  </div>
</body>
</html>