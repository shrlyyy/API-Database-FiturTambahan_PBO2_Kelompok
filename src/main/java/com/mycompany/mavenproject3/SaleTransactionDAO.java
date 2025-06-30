/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SaleTransactionDAO {
    private Connection conn;

    public SaleTransactionDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertSaleTransaction(SaleTransaction sale) throws SQLException {
        String sql = "INSERT INTO saletransaction (customerId, cashierId, date, time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sale.getCustomerId());
            stmt.setInt(2, 1); 
            stmt.setDate(3, Date.valueOf(sale.getDate()));
            stmt.setTime(4, Time.valueOf(sale.getTime()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Membuat transaksi gagal, tidak ada baris yang diupdate.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // id transaksi yang baru
                } else {
                    throw new SQLException("Membuat transaksi gagal, tidak dapat memperoleh ID.");
                }
            }
        }
    }
}

