package com.kandyvilla.kandyvillathefashionstore.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String productName;
    String productImg;
    String productPrice;
    int totalPrice;
    String productQuantity;
    String productSize;
    String currentDate;
    String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public MyCartModel() {
    }

    public MyCartModel(String productName, String productImg, String productPrice, int totalPrice, String productQuantity, String productSize, String currentDate) {
        this.productName = productName;
        this.productImg = productImg;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.productQuantity = productQuantity;
        this.productSize = productSize;
        this.currentDate = currentDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
