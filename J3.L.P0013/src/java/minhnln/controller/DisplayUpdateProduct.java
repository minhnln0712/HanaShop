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
import minhnln.dao.ProductDAO;
import minhnln.dto.ProductDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "DisplayUpdateProduct", urlPatterns = {"/DisplayUpdateProduct"})
public class DisplayUpdateProduct extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String UPDATE_PAGE = "update.jsp";

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
            String pageNo = request.getParameter("pageNo");
            String searchname = request.getParameter("txtSearchName");
            String minprice = request.getParameter("txtMinPrice");
            String maxprice = request.getParameter("txtMaxPrice");
            String category = request.getParameter("cbbCategory");
            String status = request.getParameter("cbbStatus");
            String productID = request.getParameter("txtProductID");
            ProductDAO Pdao = new ProductDAO();
            List<ProductDTO> Plist = Pdao.getProductbyID(productID);
            request.setAttribute("UPDATEPRODUCT", Plist);
            request.setAttribute("PAGENO", pageNo);
            request.setAttribute("SEARCHNAME", searchname);
            request.setAttribute("MINPRICE", minprice);
            request.setAttribute("MAXPRICE", maxprice);
            request.setAttribute("CATEGORYID", category);
            request.setAttribute("STATUS", status);
            url = UPDATE_PAGE;
        } catch (Exception e) {
            log("Error at DisplayUpdateProduct: " + e.getMessage());
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
