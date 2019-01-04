package net.agriblockchain.data.model;

import java.util.Date;

public class PHReading {

    private double ph;

    private Date readingTime;

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public Date getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Date readingTime) {
        this.readingTime = readingTime;
    }
}
