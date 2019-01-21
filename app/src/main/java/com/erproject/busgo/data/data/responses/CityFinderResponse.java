package com.erproject.busgo.data.data.responses;

import com.google.gson.annotations.SerializedName;

public class CityFinderResponse {
    @SerializedName("success")
    private Boolean status;
    @SerializedName("country_name")
    private String country;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("city")
    private String city;
    @SerializedName("latitude")
    private Double lat;
    @SerializedName("longitude")
    private Double lon;

    public CityFinderResponse(Boolean status) {
        this.status = status;
    }

    public CityFinderResponse() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
