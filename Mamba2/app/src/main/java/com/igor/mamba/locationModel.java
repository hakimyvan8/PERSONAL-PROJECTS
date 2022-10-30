package com.igor.mamba;

public class locationModel {

    private String LocationName, LocationPrince, locationid;

    private float distanceKm;

    public locationModel() {

    }



    public String getLocationName() {

        return LocationName;
    }

    public void setLocationName(String locationName) {

        LocationName = locationName;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getLocationPrince() {

        return LocationPrince;
    }

    public void setLocationPrince(String locationPrince) {

        LocationPrince = locationPrince;
    }

    public float getDistanceKm() {

        return distanceKm;
    }

    public void setDistanceKm(float distanceKm) {

        this.distanceKm = distanceKm;
    }
}