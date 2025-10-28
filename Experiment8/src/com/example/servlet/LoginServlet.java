package Experiment8.src.com.example.servlet;

import Experiment8.src.com.example.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean ok = false;
        try {
            Path webInf = Paths.get(getServletContext().getRealPath("/WEB-INF"));
            try (Connection conn = DBUtil.getConnection(webInf);
                 PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE username=? AND password=?")) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        ok = true;
                    }
                }
            }
        } catch (Exception e) {
            // Fallback demo login when DB/table not ready
            ok = "admin".equals(username) && "admin123".equals(password);
        }

        if (ok) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", username);
            resp.sendRedirect("employees");
        } else {
            resp.sendRedirect("login.html?error=Invalid%20credentials");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.html");
    }
}


