package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private int customerId;

    private String name;
    private String phone;
    private String city;
    private String customerType;

    public Customer() {}

    public Customer(int customerId, String name, String phone,
                    String city, String customerType) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.customerType = customerType;
    }

    // getters & setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }
}