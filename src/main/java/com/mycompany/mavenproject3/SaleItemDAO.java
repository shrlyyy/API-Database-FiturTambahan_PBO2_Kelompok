/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleItemDAO {
    private Connection conn;

    public SaleItemDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertSaleItem(SaleItem item, int saleId) throws SQLException {
        String sql = "INSERT INTO saleitem (saleId, productId, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, saleId);
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getSubTotal());
            stmt.executeUpdate();
        }
    }
}

