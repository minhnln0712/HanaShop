/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhnln.DAO.ProductDAO;
import minhnln.DAO.RegistrationDAO;
import minhnln.DTO.RegistrationDTO;

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
            HttpSession session = request.getSession();
            RegistrationDAO Rdao = new RegistrationDAO();
            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");
            if (!request.getParameter("logingoogle").isEmpty()) {
                if (Rdao.checkUserByUserID(userID)) {
                    session.setAttribute("USERID", userID);
                    session.setAttribute("ROLE", "USER");
                    session.setAttribute("NAME", request.getParameter("txtFullname"));
                    url = DISPLAY_PRODUCT_SERVLET;
                    return;
                } else {
                    Rdao.signupforGoogle(userID, request.getParameter("txtFullname"));
                    session.setAttribute("USERID", userID);
                    session.setAttribute("ROLE", "USER");
                    session.setAttribute("NAME", request.getParameter("txtFullname"));
                    url = DISPLAY_PRODUCT_SERVLET;
                    return;
                }
            }
            RegistrationDTO dto = Rdao.login(userID, password);
            if (!password.equals(Rdao.getPasswordByUserID(userID))) {
                geterror = true;
                url = "login.jsp?txtUserID=" + userID;
                request.setAttribute("ERROR", "Wrong username or password!!");
                return;
            }
            if (dto != null) {
                if (dto.getRole().equals("ADMIN")) {
                    url = DISPLAY_PRODUCT_SERVLET;
                    session.setAttribute("USERID", userID);
                    session.setAttribute("ROLE", dto.getRole());
                    session.setAttribute("NAME", dto.getFullname());
                } else if (dto.getRole().equals("USER")) {
                    url = DISPLAY_PRODUCT_SERVLET;
                    session.setAttribute("USERID", userID);
                    session.setAttribute("ROLE", dto.getRole());
                    session.setAttribute("NAME", dto.getFullname());
                }
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
