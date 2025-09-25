package com.tracker.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tracker.model.Expense;
import com.tracker.util.DatabaseConnection;

public class ExpenseTrackerAppDAO {
    
    // SQL Statements
    private static final String GET_ALL_EXPENSES = "SELECT * FROM expensetracker ORDER BY Created_at DESC";
    private static final String ADD_EXPENSE = "INSERT INTO expensetracker (Amount, Description, CategoryId) VALUES (?, ?, ?)";
    private static final String UPDATE_EXPENSE = "UPDATE expensetracker SET Amount = ?, Description = ?, CategoryId = ?, Updated_at = CURRENT_TIMESTAMP WHERE Id = ?";
    private static final String DELETE_EXPENSE = "DELETE FROM expensetracker WHERE Id = ?";
    private static final String GET_EXPENSES_BY_CATEGORY = "SELECT e.* FROM expensetracker e JOIN category c ON e.CategoryId = c.id WHERE c.Name = ? ORDER BY e.Created_at DESC";
    
    public List<Expense> getAllExpenses() throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_EXPENSES);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("Id"));
                expense.setAmount(rs.getInt("Amount"));
                expense.setDescription(rs.getString("Description"));
                expense.setCategory_id(rs.getInt("CategoryId"));
                expense.setCreated_at(rs.getTimestamp("Created_at"));
                expense.setUpdated_at(rs.getTimestamp("Updated_at"));
                expenses.add(expense);
            }
        }
        return expenses;
    }
    
    public void addExpense(Expense expense) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_EXPENSE)) {
            
            stmt.setInt(1, expense.getAmount());
            stmt.setString(2, expense.getDescription());
            stmt.setInt(3, expense.getCategory_id());
            stmt.executeUpdate();
        }
    }
    
    public void updateExpense(Expense expense) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_EXPENSE)) {
            
            stmt.setInt(1, expense.getAmount());
            stmt.setString(2, expense.getDescription());
            stmt.setInt(3, expense.getCategory_id());
            stmt.setInt(4, expense.getId());
            stmt.executeUpdate();
        }
    }
    
    public void deleteExpense(Expense expense) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_EXPENSE)) {
            
            stmt.setInt(1, expense.getId());
            stmt.executeUpdate();
        }
    }
    
    public List<Expense> getExpensesByCategory(String categoryName) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_EXPENSES_BY_CATEGORY)) {
            
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = new Expense();
                    expense.setId(rs.getInt("Id"));
                    expense.setAmount(rs.getInt("Amount"));
                    expense.setDescription(rs.getString("Description"));
                    expense.setCategory_id(rs.getInt("CategoryId"));
                    expense.setCreated_at(rs.getTimestamp("Created_at"));
                    expense.setUpdated_at(rs.getTimestamp("Updated_at"));
                    expenses.add(expense);
                }
            }
        }
        return expenses;
    }
}