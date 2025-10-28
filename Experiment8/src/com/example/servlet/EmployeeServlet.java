package Experiment8.src.com.example.servlet;

import Experiment8.src.com.example.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServlet extends HttpServlet {

    static class Employee {
        int id;
        String name;
        String department;
        String salary;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        List<Employee> employees = new ArrayList<>();
        try {
            Path webInf = Paths.get(getServletContext().getRealPath("/WEB-INF"));
            try (Connection conn = DBUtil.getConnection(webInf);
                 PreparedStatement ps = conn.prepareStatement("SELECT id, name, department, salary FROM employees");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.id = rs.getInt("id");
                    e.name = rs.getString("name");
                    e.department = rs.getString("department");
                    e.salary = String.valueOf(rs.getBigDecimal("salary"));
                    employees.add(e);
                }
            }
        } catch (Exception e) {
            // If DB not ready, show sample
            Employee demo = new Employee();
            demo.id = 1;
            demo.name = "Demo User";
            demo.department = "IT";
            demo.salary = "50000.00";
            employees.add(demo);
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>Employees</title>");
            out.println("<style>table{border-collapse:collapse}td,th{border:1px solid #ddd;padding:8px}</style>");
            out.println("</head><body>");
            out.println("<h2>Employee Records</h2>");
            out.println("<a href='login.html'>Back</a><br/><br/>");
            out.println("<table><thead><tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr></thead><tbody>");
            for (Employee e : employees) {
                out.println("<tr><td>" + e.id + "</td><td>" + e.name + "</td><td>" + e.department + "</td><td>" + e.salary + "</td></tr>");
            }
            out.println("</tbody></table>");
            out.println("</body></html>");
        }
    }
}


