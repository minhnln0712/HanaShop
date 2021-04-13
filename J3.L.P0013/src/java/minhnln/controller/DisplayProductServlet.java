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
import minhnln.dao.CategoryDAO;
import minhnln.dao.ProductDAO;
import minhnln.dto.CategoryDTO;
import minhnln.dto.ProductDTO;

/**
 *
 * @author Welcome
 */
@WebServlet(name = "DisplayProductServlet", urlPatterns = {"/DisplayProductServlet"})
public class DisplayProductServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String SEARCH_PAGE = "search.jsp";

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
        final int pagingSize = 10; //tổng product của 1 trang
        int SumofProduct;// tổng số lượng product
        int NumofPage;//Số trang sẽ được tạo
        int pageNo = 1;//Mặc định sẽ là trang đầu tiên (pageNo=1)
        try {
            HttpSession session = request.getSession();
            ProductDAO Pdao = new ProductDAO();
            CategoryDAO Cdao = new CategoryDAO();
            //Xử lý số lượng trang
            SumofProduct = Pdao.getAmountofProduct();
            if (request.getParameter("pageNo") != null) {
                pageNo = Integer.parseInt(request.getParameter("pageNo"));
            }
            NumofPage = SumofProduct / pagingSize;
            if (SumofProduct > pagingSize * NumofPage) {//Nếu tổng sản phẩm lớn hơn tổng số lượng sản phẩm trong các trang
                NumofPage += 1;
            }
            //Xử lý việc hiển thị
            String userID = (String) session.getAttribute("USERID");
            List<ProductDTO> Plist = Pdao.loadAllProductWithPageNo(pageNo, pagingSize);
            List<CategoryDTO> Clist = Cdao.getAllCategory();
            List<ProductDTO> suggestlist = Pdao.suggestProductofAnotherUser(userID);
            url = SEARCH_PAGE;
            session.setAttribute("MAXPAGE", NumofPage);
            request.setAttribute("LISTPRODUCT", Plist);
            session.setAttribute("LISTCATEGORY", Clist);
            session.setAttribute("SUGGESTLIST", suggestlist);
            request.setAttribute("PAGENO", pageNo);
        } catch (Exception e) {
            log("Error at DisplayProductServlet: " + e.getMessage());
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
