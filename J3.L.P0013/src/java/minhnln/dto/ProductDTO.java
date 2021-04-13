/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.dto;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Welcome
 */
public class ProductDTO implements Serializable {

    private String productID;
    private String name;
    private String image;
    private String description;
    private String categoryID;
    private String categoryName;
    private Date createDate;
    private float price;
    private int quantity;
    private int maxquantity;
    private boolean status;

    public ProductDTO(String productID, String name, String image, String description, String categoryID, String categoryName, Date createDate, float price, int quantity, int maxquantity, boolean status) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.createDate = createDate;
        this.price = price;
        this.quantity = quantity;
        this.maxquantity = maxquantity;
        this.status = status;
    }

    /**
     * @return the productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the categoryID
     */
    public String getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the maxquantity
     */
    public int getMaxquantity() {
        return maxquantity;
    }

    /**
     * @param maxquantity the maxquantity to set
     */
    public void setMaxquantity(int maxquantity) {
        this.maxquantity = maxquantity;
    }

}
