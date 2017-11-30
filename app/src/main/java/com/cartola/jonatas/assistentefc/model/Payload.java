package com.cartola.jonatas.assistentefc.model;

/**
 * Created by jonatas on 13/11/2017.
 */

public class Payload {

    private String email;
    private String password;
    private long serviceId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }
}
