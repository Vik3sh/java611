package Experiment8.src.com.example.servlet;

import Experiment8.src.com.example.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class AttendanceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("studentId");
        String dateStr = req.getParameter("date");
        String status = req.getParameter("status");

        try {
            Path webInf = Paths.get(getServletContext().getRealPath("/WEB-INF"));
            try (Connection conn = DBUtil.getConnection(webInf);
                 PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance(student_id, att_date, status) VALUES(?,?,?)")) {
                ps.setString(1, studentId);
                ps.setDate(2, Date.valueOf(dateStr));
                ps.setString(3, status);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            // ignore to allow demo flow without DB
        }

        resp.sendRedirect("attendance.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("attendance.jsp");
    }
}


