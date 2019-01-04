package net.agriblockchain.data.model;

import java.util.Date;

public class Plot {

    private String plotId;

    private Date cultivationStartDate;

    private double extent;

    private Directions closerPlots;

    private String[] activities;

    private String[] phReadings;

    private String[] certificationBodyComments;

    private String status;

    private String cultivatedType;

    private String farm;

    private Date seededDate;

    private double seededAmount;

    public String getPlotId() {
        return plotId;
    }

    public void setPlotId(String plotId) {
        this.plotId = plotId;
    }

    public Date getCultivationStartDate() {
        return cultivationStartDate;
    }

    public void setCultivationStartDate(Date cultivationStartDate) {
        this.cultivationStartDate = cultivationStartDate;
    }

    public Double getExtent() {
        return extent;
    }

    public void setExtent(Double extent) {
        this.extent = extent;
    }

    public Directions getCloserPlots() {
        return closerPlots;
    }

    public void setCloserPlots(Directions closerPlots) {
        this.closerPlots = closerPlots;
    }

    public String[] getCertificationBodyComments() {
        return certificationBodyComments;
    }

    public void setCertificationBodyComments(String[] certificationBodyComments) {
        this.certificationBodyComments = certificationBodyComments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExtent(double extent) {
        this.extent = extent;
    }

    public String[] getActivities() {
        return activities;
    }

    public void setActivities(String[] activities) {
        this.activities = activities;
    }

    public String[] getPhReadings() {
        return phReadings;
    }

    public void setPhReadings(String[] phReadings) {
        this.phReadings = phReadings;
    }

    public String getCultivatedType() {
        return cultivatedType;
    }

    public void setCultivatedType(String cultivatedType) {
        this.cultivatedType = cultivatedType;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public Date getSeededDate() {
        return seededDate;
    }

    public void setSeededDate(Date seededDate) {
        this.seededDate = seededDate;
    }

    public double getSeededAmount() {
        return seededAmount;
    }

    public void setSeededAmount(double seededAmount) {
        this.seededAmount = seededAmount;
    }
}
