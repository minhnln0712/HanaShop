/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhnln.dao.OrderDAO;
import minhnln.dao.OrderDetailDAO;
import minhnln.dao.ProductDAO;
import minhnln.dto.CartDTO;
import minhnln.dto.ProductDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
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
            if (role.equals("USER")) {
                ProductDAO Pdao = new ProductDAO();
                OrderDAO Odao = new OrderDAO();
                OrderDetailDAO ODdao = new OrderDetailDAO();
                //Xử lý toàn bộ thông tin
                //Xử lý order
                String orderID = Odao.getLastOrderID();
                if (orderID.equals("")) {
                    orderID = "O-0000001";
                } else {
                    String[] orderIDArray = orderID.split("-");
                    orderID = "O-" + String.format("%07d", (Integer.parseInt(orderIDArray[1]) + 1));
                }
                String userID = (String) session.getAttribute("USERID");
                Date datetimenow = (Date) Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String createDate = df.format(datetimenow);
                float totalPrice = (float) session.getAttribute("TOTAL");
                //Xử lý orderDetail
                String[] productIDArray = request.getParameterValues("txtProductID");
                String[] quantityArray = request.getParameterValues("txtQuantity");
                String[] priceArray = request.getParameterValues("txtPrice");
                String[] quantityRemainArray = request.getParameterValues("txtQuantityRemain");
                //Tiếp nhận thông tin
                if (Odao.createOrder(orderID, userID, createDate, totalPrice, "Delivering")) {
                    for (int i = 0; i < productIDArray.length; i++) {
                        //Xử lý orderDetail
                        String orderDetailID = ODdao.getLastOrderDetailID();
                        if (orderDetailID.equals("")) {
                            orderDetailID = orderID + "-001";
                        } else {
                            String[] orderDetailIDArray = orderDetailID.split("-");
                            orderDetailID = orderID + "-" + String.format("%03d", (Integer.parseInt(orderDetailIDArray[2]) + 1));
                        }
                        String productID = productIDArray[i];
                        int quantity = Integer.parseInt(quantityArray[i]);
                        int quantityRemain = Integer.parseInt(quantityRemainArray[i]);
                        float price = Float.parseFloat(priceArray[i]);
                        if (ODdao.createOrderDetail(orderDetailID, orderID, productIDArray[i], quantity, price)) {
                            //Trừ đi số lượng đã đặt
                            if (Pdao.setRemainQuantity(productID, quantityRemain)) {
                                if (quantityRemain == 0) {
                                    Pdao.deleteProduct(productID);
                                }
                                url = DISPLAY_PRODUCT_SERVLET;
                                session.removeAttribute("CART");
                            }
                        }
                    }
                }
            } else {
                url = DISPLAY_PRODUCT_SERVLET;
            }
        } catch (Exception e) {
            log("Error at CheckoutServlet: " + e.getMessage());
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
