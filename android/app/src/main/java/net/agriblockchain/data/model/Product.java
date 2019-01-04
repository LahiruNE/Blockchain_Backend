package net.agriblockchain.data.model;

import java.util.Date;

public class Product {

    private String productId = "";

    private double quantity = 0;

    private Date pluckedDate = new Date();

    private Trace[] productpath = new Trace[0];

    private String currentOwner = "";

    private String issuer = "";

    private String plot = "";

    private Certification certification = new Certification();

    private String productType = "";

    private String unit = "";

    private String activeStatus = "";

    private String divideStatus = "";

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getDivideStatus() {
        return divideStatus;
    }

    public void setDivideStatus(String divideStatus) {
        this.divideStatus = divideStatus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Trace[] getProductpath() {
        return productpath;
    }

    public void setProductpath(Trace[] productpath) {
        this.productpath = productpath;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getPluckedDate() {
        return pluckedDate;
    }

    public void setPluckedDate(Date pluckedDate) {
        this.pluckedDate = pluckedDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }
}
