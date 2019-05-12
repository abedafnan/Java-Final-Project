package com.abedafnan.models;

public class Order {
    Customer customer;
    Product product;
    String orderDate;
    int quantity;
    double price;
    double totalPrice;

    public Order(Customer customer, Product product, String orderDate, int quantity, double price, double totalPrice) {
        this.customer = customer;
        this.product = product;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
