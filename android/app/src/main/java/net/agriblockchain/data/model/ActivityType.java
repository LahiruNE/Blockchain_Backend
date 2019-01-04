package net.agriblockchain.data.model;

public enum ActivityType {

    LANDSCAPING("LANDSCAPING"), WATERING("WATERING");

    final String text;

    ActivityType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
