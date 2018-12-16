package com.divineventures.countryapp;

/**
 * Created by USER on 6/1/2018.
 */

class MyData {
    private String name;
    private String flag_link;
    private String region;
    private String population;

    public MyData(String name, String flag_link,String region,String population) {
        this.name = name;
        this.flag_link = flag_link;
        this.population = population;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag_link() {
        return flag_link;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setFlag_link(String flag_link) {
        this.flag_link = flag_link;
    }
}
