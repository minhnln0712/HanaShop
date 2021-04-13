/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.controller;

import java.io.IOException;
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
@WebServlet(name = "SearchProductServlet", urlPatterns = {"/SearchProductServlet"})
public class SearchProductServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String SEARCH_PAGE = "search.jsp";
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
            ProductDAO Pdao = new ProductDAO();
            CategoryDAO Cdao = new CategoryDAO();
            //Xử lý thông tin search
            //
            String keyword = "%";
            if (!request.getParameter("txtSearchName").trim().equals("")) {
                keyword = request.getParameter("txtSearchName");
                if (Pdao.checkStringInput(keyword)) {
                    url = DISPLAY_PRODUCT_SERVLET;
                    request.setAttribute("ERROR", "Wrong KEYWORD!!");
                    return;
                }
                request.setAttribute("SEARCHNAME", keyword);
            }
            //
            int minPrice = 0;
            if (!request.getParameter("txtMinPrice").equals("")) {
                minPrice = Integer.parseInt(request.getParameter("txtMinPrice"));
                request.setAttribute("MINPRICE", minPrice);
            }

            ///
            int maxPrice = 999999999;
            if (!request.getParameter("txtMaxPrice").equals("")) {
                maxPrice = Integer.parseInt(request.getParameter("txtMaxPrice"));
                request.setAttribute("MAXPRICE", maxPrice);
            }
            if (maxPrice <= minPrice) {
                request.setAttribute("ERROR", "Max Price must bigger than Min Price");
                request.removeAttribute("MINPRICE");
                request.removeAttribute("MAXPRICE");
                url = DISPLAY_PRODUCT_SERVLET;
                return;
            }
            //
            String category = "%";
            if (request.getParameter("cbbCategory") != null && !request.getParameter("cbbCategory").trim().isEmpty()) {
                category = request.getParameter("cbbCategory");
                request.setAttribute("CATEGORY", category);
            }
            ///
            String status = "1";
            if (request.getParameter("cbbStatus") != null && !request.getParameter("cbbStatus").trim().isEmpty()) {
                status = request.getParameter("cbbStatus");
                request.setAttribute("STATUS", status);
            }
            //Xử lý số lượng trang
            final int pagingSize = 10; //tổng product của 1 trang
            int SumofProduct;// tổng số lượng product
            int NumofPage;//Số trang sẽ được tạo
            int pageNo = 1;//Mặc định sẽ là trang đầu tiên (pageNo=1)
            SumofProduct = Pdao.getAmountofProductAfterSearch(keyword, minPrice, maxPrice, category, status);
            if (request.getParameter("pageNo") != null) {
                pageNo = Integer.parseInt(request.getParameter("pageNo"));
            }
            NumofPage = SumofProduct / pagingSize;
            if (SumofProduct > pagingSize * NumofPage) {//Nếu tổng sản phẩm lớn hơn tổng số lượng sản phẩm trong các trang
                NumofPage += 1;
            }
            //SEARCH
            List<CategoryDTO> Clist = Cdao.getAllCategory();
            List<ProductDTO> Plist = Pdao.searchProductWithPageNo(pageNo, pagingSize, keyword, minPrice, maxPrice, category, status);
            request.setAttribute("LISTPRODUCT", Plist);
            session.setAttribute("LISTCATEGORY", Clist);
            session.setAttribute("MAXPAGE", NumofPage);
            request.setAttribute("PAGENO", pageNo);
            url = SEARCH_PAGE;
        } catch (Exception e) {
            log("Error at SearchProductServlet: " + e.getMessage());
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
