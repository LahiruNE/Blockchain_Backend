package net.agriblockchain.data.model;

public class Trace {

    private String timestamp;

    private Stakeholder authperson;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Stakeholder getAuthperson() {
        return authperson;
    }

    public void setAuthperson(Stakeholder authperson) {
        this.authperson = authperson;
    }
}
