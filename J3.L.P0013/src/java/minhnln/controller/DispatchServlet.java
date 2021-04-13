/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Welcome
 */
public class DispatchServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String DISPLAY_PRODUCT_SERVLET = "DisplayProductServlet";
    private final String LOGIN_SERVLET = "LoginServlet";
    private final String SEARCH_PRODUCT_SERVLET = "SearchProductServlet";
    private final String UPDATE_PRODUCT_SERVLET = "UpdateProductServlet";
    private final String DELETE_PRODUCT_SERVLET = "DeleteProductServlet";
    private final String CREATE_PRODUCT_SERVLET = "CreateProductServlet";
    private final String UPDATE_PRICE_CART_SERVLET = "UpdatePriceCartServlet";
    private final String CHECK_OUT_SERVLET = "CheckoutServlet";
    private final String SEARCH_HISTORY_SERVLET = "SearchHistoryServlet";
    private final String REGISTRATION_SERVLET = "RegistrationServlet";

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
        String button = request.getParameter("btAction");
        String url = ERROR_PAGE;
        try {
            if (button == null) {
                url = DISPLAY_PRODUCT_SERVLET;
            } else if (button.equals("Login")) {
                url = LOGIN_SERVLET;
            } else if (button.equals("Search")) {
                url = SEARCH_PRODUCT_SERVLET;
            } else if (button.equals("Delete")) {
                url = DELETE_PRODUCT_SERVLET;
            } else if (button.equals("Update")) {
                url = UPDATE_PRODUCT_SERVLET;
            } else if (button.equals("Create")) {
                url = CREATE_PRODUCT_SERVLET;
            } else if (button.equals("Update Price")) {
                url = UPDATE_PRICE_CART_SERVLET;
            } else if (button.equals("Check out")) {
                url = CHECK_OUT_SERVLET;
            } else if (button.equals("Search History")) {
                url = SEARCH_HISTORY_SERVLET;
            } else if (button.equals("Sign Up")) {
                url = REGISTRATION_SERVLET;
            }
        } catch (Exception e) {
            log("Error at DispatchServlet: " + e.getMessage());
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
