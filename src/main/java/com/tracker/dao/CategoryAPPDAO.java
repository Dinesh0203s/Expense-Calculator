package com.tracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tracker.model.Category;
import com.tracker.util.DatabaseConnection;

public class CategoryAPPDAO {
    private static final String GET_ALL_CATEGORIES = "SELECT * FROM category";
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getDBConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCategory_name(resultSet.getString("Name"));
                categories.add(category);
            }
        }
        return categories;
    }
}
