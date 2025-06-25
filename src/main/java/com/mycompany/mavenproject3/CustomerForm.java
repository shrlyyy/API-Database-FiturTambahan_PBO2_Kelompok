/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerForm extends JFrame {
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JButton saveButton;
    private JButton deleteButton;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    private ArrayList<String> registeredPhones = new ArrayList<>();
    private ArrayList<Customer> customers;
    private boolean isEditing = false;
    private int editingIndex = -1;

    public CustomerForm(ArrayList<Customer> customers) {
        this.customers = customers; 
        
        setTitle("WK. Cuan | Form Customer");
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nama:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Nomor Telepon:"));
        phoneNumberField = new JTextField();
        formPanel.add(phoneNumberField);

        formPanel.add(new JLabel("Alamat:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        // Tombol Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        saveButton = new JButton("Simpan");
        deleteButton = new JButton("Hapus");
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Tabel
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor Telepon", "Alamat"}, 0);
        customerTable = new JTable(tableModel);
        getContentPane().add(new JScrollPane(customerTable), BorderLayout.CENTER);

        // Event tombol
        saveButton.addActionListener(e -> saveCustomer());

        deleteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer removed = customers.remove(selectedRow);
                registeredPhones.remove(removed.getPhoneNumber().toString());
                refreshTable();
                clearFields();
                isEditing = false;
                editingIndex = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Pilih customer untuk dihapus.");
            }
        });

        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    Customer c = customers.get(selectedRow);
                    nameField.setText(c.getName());
                    phoneNumberField.setText(c.getPhoneNumber().toString());
                    addressField.setText(c.getAddress());
                    editingIndex = selectedRow;
                    isEditing = true;
                } else {
                    clearFields();
                    isEditing = false;
                    editingIndex = -1;
                }
            }
        });

        // Muat awal (jika perlu)
        refreshTable();
    }

    private void saveCustomer() {
        try {
            String name = nameField.getText().trim();
            String phoneText = phoneNumberField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty() || phoneText.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!");
                return;
            }

            if (!phoneText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Nomor telepon harus berupa angka!");
                return;
            }

            Long phoneNumber = Long.parseLong(phoneText);

            if (isEditing && editingIndex != -1) {
                Customer existing = customers.get(editingIndex);
                // Jika nomor berubah, pastikan belum digunakan
                if (!existing.getPhoneNumber().toString().equals(phoneText) && registeredPhones.contains(phoneText)) {
                    JOptionPane.showMessageDialog(this, "Nomor telepon sudah terdaftar!");
                    return;
                }

                registeredPhones.remove(existing.getPhoneNumber().toString());
                registeredPhones.add(phoneText);

                existing.setName(name);
                existing.setPhoneNumber(phoneNumber);
                existing.setAddress(address);
                isEditing = false;
                editingIndex = -1;

            } else {
                // Tambah baru
                if (registeredPhones.contains(phoneText)) {
                    JOptionPane.showMessageDialog(this, "Nomor telepon sudah terdaftar!");
                    return;
                }
                Customer newCustomer = new Customer(name, phoneNumber, address);
                customers.add(newCustomer);
                registeredPhones.add(phoneText);
            }

            refreshTable();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Customer c : customers) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getName(),
                c.getPhoneNumber(),
                c.getAddress()
            });
        }
    }
}