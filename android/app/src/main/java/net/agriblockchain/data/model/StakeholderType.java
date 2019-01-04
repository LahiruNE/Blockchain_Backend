package net.agriblockchain.data.model;

public enum StakeholderType {

    ADMIN("ADMIN"),
    FARMER("FARMER"),
    DISTRIBUTION("DISTRIBUTION"),
    PACKAGING("PACKAGING"),
    WAREHOUSE("WAREHOUSE"),
    RETAIL("RETAIL"),
    FERTILIZER("FERTILIZER"),
    SEED("SEED"),
    PESTICIDE("PESTICIDE"),
    CERTIFICATION("CERTIFICATION");

    public final String value;

    StakeholderType(final String value) {
        this.value = value;
    }

}
