/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhnln.dao.ActionDAO;
import minhnln.dao.ProductDAO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "CreateProductServlet", urlPatterns = {"/CreateProductServlet"})
public class CreateProductServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String CREATE_PAGE = "create.jsp";
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
        try {
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("ROLE");
            if (role.equals("ADMIN")) {
                ProductDAO Pdao = new ProductDAO();
                ActionDAO Adao = new ActionDAO();
                //Thong tin
                String actionID = Adao.getLastActionID();
                if (actionID.isEmpty()) {
                    actionID = "A-0000001";
                } else {
                    String[] actionIDArray = actionID.split("-");
                    actionID = "A-" + String.format("%07d", (Integer.parseInt(actionIDArray[1]) + 1));
                }
                String productID = Pdao.getlastProductID();
                if (productID.isEmpty()) {
                    productID = "P-0000001";
                } else {
                    String[] productIDarray = productID.split("-");
                    productID = "P-" + String.format("%07d", (Integer.parseInt(productIDarray[1]) + 1));
                }
                String name = request.getParameter("txtName");
                if (Pdao.getExistProductName(name)) {
                    url = CREATE_PAGE;
                    request.setAttribute("ERROR", "This Name is EXISTED!!");
                    return;
                }
                String image = request.getParameter("txtImage");
                String description = request.getParameter("txtDescription");
                float price = Float.parseFloat(request.getParameter("txtPrice"));
                String categoryID = request.getParameter("cbbCategory");
                String status = "1";
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                java.util.Date createDate = (java.util.Date) Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String createDateString = df.format(createDate);
                java.sql.Date createDateSQL = new java.sql.Date(createDate.getTime());
                String userID = (String) session.getAttribute("USERID");
                if (quantity == 0) {
                    status = "0";
                }
                if (Pdao.createProduct(productID, name, image, description, price, createDateString, categoryID, status, quantity)) {
                    if (Adao.createAction(actionID, productID, userID, "CREATE", createDateSQL)) {
                        url = DISPLAY_PRODUCT_SERVLET;
                    }
                }
            } else {
                url = DISPLAY_PRODUCT_SERVLET;
            }
        } catch (Exception e) {
            log("Error at CreateProductServlet: " + e.getMessage());
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
