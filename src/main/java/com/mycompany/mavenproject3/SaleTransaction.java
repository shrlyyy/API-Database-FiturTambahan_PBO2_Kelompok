/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class SaleTransaction {
    private int id;
    private int customerId;
    private int cashierId;
    private LocalDate date;
    private LocalTime time;
    private List<SaleItem> saleItems;

 public SaleTransaction(int id, int customerId, int cashierId, LocalDate date, LocalTime time, List<SaleItem> saleItems) {
        this.id = id;
        this.customerId = customerId;
        this.cashierId = cashierId;
        this.date = date;
        this.time = time;
        this.saleItems = saleItems;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getCashierId() { return cashierId; }
    public void setCashierId(int cashierId) { this.cashierId = cashierId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public List<SaleItem> getSaleItems() { return saleItems; }
    public void setSaleItems(List<SaleItem> saleItems) { this.saleItems = saleItems; }

    public double getTotalPrice() {
        double total = 0;
        for (SaleItem item : saleItems) {
            total += item.getSubTotal();
        }
        return total;
    }
}

