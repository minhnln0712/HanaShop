/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhnln.dao.ProductDAO;
import minhnln.dao.RegistrationDAO;
import minhnln.dto.RegistrationDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String LOGIN_PAGE = "login.jsp";
    private final String DISPLAY_PRODUCT_SERVLET = "DisplayProductServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        boolean geterror = false;
        try {
            //Gọi session
            HttpSession session = request.getSession();
            //Khai báo những biến cần thiết
            RegistrationDAO Rdao = new RegistrationDAO();
            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");
            String fullname = request.getParameter("txtFullname");
            //Check xem cái login này có phải là google hay không
            if (!request.getParameter("logingoogle").isEmpty()) {
                //Nếu account này chưa có đăng ký thì sẽ tự động DKý luôn
                if (!Rdao.checkUserByUserID(userID)) {
                    Rdao.signupforGoogle(userID, fullname);
                }
                session.setAttribute("USERID", userID);
                session.setAttribute("ROLE", "USER");
                session.setAttribute("NAME", fullname);
                url = DISPLAY_PRODUCT_SERVLET;
                return;
            }
            //Nếu k thì qua phần đăng nhập = user ID & password
            RegistrationDTO dto = Rdao.login(userID, password);
            //Check hoa thường của mật khẩu
            if (!password.equals(Rdao.getPasswordByUserID(userID))) {
                geterror = true;
                url = "login.jsp?txtUserID=" + userID;
                request.setAttribute("ERROR", "Wrong username or password!!");
                return;
            }
            //Nếu như sử dụng c/n login thành công thì đưa những thông tin cần thiết vào session
            if (dto != null) {
                if (dto.getRole().equals("ADMIN")) {
                    session.setAttribute("ROLE", dto.getRole());
                    session.setAttribute("NAME", dto.getFullname());
                } else if (dto.getRole().equals("USER")) {
                    session.setAttribute("ROLE", dto.getRole());
                    session.setAttribute("NAME", dto.getFullname());
                }
                url = DISPLAY_PRODUCT_SERVLET;
                session.setAttribute("USERID", userID);
            }
        } catch (Exception e) {
            log("Error at LoginServlet: " + e.getMessage());
        } finally {
            if (geterror) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
