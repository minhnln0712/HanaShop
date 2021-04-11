/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhnln.DAO.ProductDAO;
import minhnln.DTO.CartDTO;
import minhnln.DTO.ProductDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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
            ProductDAO Pdao = new ProductDAO();
            if (role.equals("USER")) {
                String productID = request.getParameter("txtProductID");
                List<ProductDTO> list = Pdao.getProductbyID(productID);
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartDTO();
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProductID().equals(productID)) {
                        if(!cart.addToCart(list.get(i))){
                            request.setAttribute("ERROR", list.get(i).getName()+" is out of stock!!!");
                        }
                    }
                }
                String pageNo = request.getParameter("pageNo");
                String searchname = request.getParameter("txtSearchName");
                String minprice = request.getParameter("txtMinPrice");
                String maxprice = request.getParameter("txtMaxPrice");
                String category = request.getParameter("cbbCategory");
                String status = request.getParameter("cbbStatus");
                url = SEARCH_PRODUCT_SERVLET + "?pageNo=" + pageNo + "&txtSearchName=" + searchname + "&txtMinPrice=" + minprice + "&txtMaxPrice=" + maxprice + "&cbbCategory=" + category + "&cbbStatus=" + status;
                List<ProductDTO> attachlist = Pdao.suggestProductByFirstProductID(productID);
                session.setAttribute("TOTAL", cart.getTotal());
                session.setAttribute("CART", cart);
                request.setAttribute("ATTACHLIST", attachlist);
            } else {
                url = DISPLAY_PRODUCT_SERVLET;
            }
        } catch (Exception e) {
            log("Error at AddToCartServlet: " + e.getMessage());
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
