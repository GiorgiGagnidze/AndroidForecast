package com.example.pro.forecast.model;

public class Item {
    private String temperature;
    private String icon;
    private String timePlace;
    private String summary;

    public Item(String temperature,String icon,String timePlace,String summary){
        this.temperature = temperature;
        this.icon = icon;
        this.timePlace = timePlace;
        this.summary = summary;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getTimePlace() {
        return timePlace;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
