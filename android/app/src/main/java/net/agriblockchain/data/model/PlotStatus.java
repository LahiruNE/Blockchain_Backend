package net.agriblockchain.data.model;

public enum PlotStatus {

    NEW("NEW"),
    HARVESTED("HARVESTED"),
    SEEDED("SEEDED");

    final String value;

    PlotStatus(final String value) {
        this.value = value;
    }
}
