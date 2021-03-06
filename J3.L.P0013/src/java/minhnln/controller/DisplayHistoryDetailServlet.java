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
import minhnln.dao.OrderDAO;
import minhnln.dao.OrderDetailDAO;
import minhnln.dao.ProductDAO;
import minhnln.dto.OrderDTO;
import minhnln.dto.OrderDetailDTO;
import minhnln.dto.ProductDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "DisplayHistoryDetailServlet", urlPatterns = {"/DisplayHistoryDetailServlet"})
public class DisplayHistoryDetailServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String HISTORY_DETAIL_PAGE = "historydetail.jsp";

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
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("ROLE");
            OrderDetailDAO ODdao = new OrderDetailDAO();
            ProductDAO Pdao = new ProductDAO();
            String orderID = request.getParameter("txtOrderID");
            List<OrderDetailDTO> ODlist = ODdao.getDetailbyOrderID(orderID);
            List<ProductDTO> Plist= Pdao.getAllProduct();
            session.setAttribute("LISTHISTORYDETAIL", ODlist);
            session.setAttribute("ALLPRODUCT", Plist);
            url = HISTORY_DETAIL_PAGE;
        } catch (Exception e) {
            log("Error at DisplayHistoryDetailServlet: " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
