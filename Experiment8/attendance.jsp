<%@ page import="java.sql.*,java.nio.file.*,Experiment8.src.com.example.util.DBUtil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Student Attendance</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .row { margin: 8px 0; }
        table { border-collapse: collapse; margin-top: 16px; }
        td, th { border: 1px solid #ddd; padding: 8px; }
    </style>
</head>
<body>
<h2>Student Attendance Portal</h2>
<a href="login.html">Back</a>

<form method="post" action="attendance">
    <div class="row">
        <label>Student ID</label>
        <input name="studentId" required />
    </div>
    <div class="row">
        <label>Date</label>
        <input name="date" type="date" required />
    </div>
    <div class="row">
        <label>Status</label>
        <select name="status">
            <option value="Present">Present</option>
            <option value="Absent">Absent</option>
        </select>
    </div>
    <button type="submit">Record Attendance</button>
</form>

<h3>Recent Attendance</h3>
<table>
    <thead>
    <tr><th>ID</th><th>Student</th><th>Date</th><th>Status</th></tr>
    </thead>
    <tbody>
    <%
        try {
            Path webInf = Paths.get(application.getRealPath("/WEB-INF"));
            try (Connection conn = DBUtil.getConnection(webInf);
                 PreparedStatement ps = conn.prepareStatement("SELECT id, student_id, att_date, status FROM attendance ORDER BY id DESC LIMIT 20");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
    %>
    <tr>
        <td><%= rs.getInt("id") %></td>
        <td><%= rs.getString("student_id") %></td>
        <td><%= rs.getDate("att_date") %></td>
        <td><%= rs.getString("status") %></td>
    </tr>
    <%
                }
            }
        } catch (Exception e) {
    %>
    <tr><td colspan="4">DB not ready. Sample: 1, S001, 2025-01-01, Present</td></tr>
    <%
        }
    %>
    </tbody>
    </table>
</body>
</html>


