/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.Controller;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhnln.DAO.ActionDAO;
import minhnln.DAO.ProductDAO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/UpdateProductServlet"})
public class UpdateProductServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String DISPLAY_PRODUCT_SERVLET = "DisplayProductServlet";
    private final String SEARCH_PRODUCT_SERVLET = "SearchProductServlet";

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
                ActionDAO Adao = new ActionDAO();
                ProductDAO Pdao = new ProductDAO();
                //Thong tin
                String actionID = Adao.getLastActionID();
                if (actionID.isEmpty()) {
                    actionID = "A-0000001";
                } else {
                    String[] actionIDarray = actionID.split("-");
                    actionID = "A-" + String.format("%07d", (Integer.parseInt(actionIDarray[1]) + 1));
                }
                String productID = request.getParameter("txtProductID");
                String name = request.getParameter("txtName");
                String image = request.getParameter("txtImage");
                String description = request.getParameter("txtDescription");
                float price = Float.parseFloat(request.getParameter("txtPrice"));
                String categoryID = request.getParameter("cbbCategory");
                String status = request.getParameter("cbbStatus");
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                String createDateProduct = request.getParameter("txtCreateDate");
                java.util.Date createDate = (java.util.Date) Calendar.getInstance().getTime();
                java.sql.Date createDateSQL = new java.sql.Date(createDate.getTime());
                String userID = (String) session.getAttribute("USERID");
                if (Pdao.updateProduct(productID, name, image, description, price, createDateProduct, categoryID, status, quantity)) {
                    if (Adao.createAction(actionID, productID, userID, "UPDATE", createDateSQL)) {
                        if (quantity == 0) {
                            Pdao.deleteProduct(productID);
                        }
                        String pageNo = request.getParameter("pageNo");
                        String searchname = request.getParameter("txtSearchName");
                        String minprice = request.getParameter("txtMinPrice");
                        String maxprice = request.getParameter("txtMaxPrice");
                        String category = request.getParameter("cbbCategoryy");
                        String statuss = request.getParameter("cbbStatuss");
                        url = SEARCH_PRODUCT_SERVLET + "?pageNo=" + pageNo + "&txtSearchName=" + searchname + "&txtMinPrice=" + minprice + "&txtMaxPrice=" + maxprice + "&cbbCategory=" + category + "&cbbStatus=" + statuss;
                    }
                }
            } else {
                url = DISPLAY_PRODUCT_SERVLET;
            }
        } catch (Exception e) {
            log("Error at UpdateProductServlet: " + e.getMessage());
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
