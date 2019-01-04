package net.agriblockchain.data.model;

public class Farm {

    private String farmId;

    private String FarmLocation;

    private Directions waterSources;

    private Directions nearFactories;

    private Certification certification;

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getFarmLocation() {
        return FarmLocation;
    }

    public void setFarmLocation(String farmLocation) {
        FarmLocation = farmLocation;
    }

    public Directions getWaterSources() {
        return waterSources;
    }

    public void setWaterSources(Directions waterSources) {
        this.waterSources = waterSources;
    }

    public Directions getNearFactories() {
        return nearFactories;
    }

    public void setNearFactories(Directions nearFactories) {
        this.nearFactories = nearFactories;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }
}
