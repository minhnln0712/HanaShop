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
import minhnln.DAO.OrderDAO;
import minhnln.DAO.ProductDAO;
import minhnln.DTO.OrderDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HISTORY_PAGE = "history.jsp";

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
        try {
            OrderDAO Odao = new OrderDAO();
            ProductDAO Pdao = new ProductDAO();
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("ROLE");
            //
            String keyword = "";
            if (request.getParameter("txtUserIDHistory") != null && !request.getParameter("txtUserIDHistory").trim().equals("")) {
                keyword = request.getParameter("txtUserIDHistory");
                if (Pdao.checkStringInput(keyword)) {
                    url = HISTORY_PAGE;
                    return;
                }
                request.setAttribute("USERIDHISTORY", keyword);
            } else if (request.getParameter("txtUserIDHistory") == null) {
                if (role.equals("ADMIN")) {
                    keyword = "%";
                } else if(role.equals("USER")) {
                    keyword = (String) session.getAttribute("USERID");
                }
            }

            String startDate = "1000-01-01";
            if (!request.getParameter("txtStartDate").trim().equals("")) {
                startDate = request.getParameter("txtStartDate");
                request.setAttribute("STARTDATE", startDate);
            }
            String endDate = "9999-12-31";
            if (!request.getParameter("txtEndDate").trim().equals("")) {
                endDate = request.getParameter("txtEndDate");
                request.setAttribute("ENDDATE", endDate);
            }
            //
            List<OrderDTO> Olist = Odao.searchOrder(keyword, startDate, endDate);
            request.setAttribute("LISTHISTORY", Olist);
            url = HISTORY_PAGE;
        } catch (Exception e) {
            log("Error at SearchHistoryServlet: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
