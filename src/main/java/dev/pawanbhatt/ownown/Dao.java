package dev.pawanbhatt.ownown;

import org.springframework.stereotype.Component;

@Component
public class Dao {

    private String allData;

    public String getData() {
        return allData;
    }

    public void setData(String allData) {
        this.allData += " -- " + allData;
    }
}
