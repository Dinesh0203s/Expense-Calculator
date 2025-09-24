package com.tracker.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.tracker.model.Expense;
import com.tracker.model.Category;
import com.tracker.dao.ExpenseTrackerAppDAO;
import com.tracker.util.DatabaseConnection;
import com.tracker.dao.CategoryAPPDAO;

import java.util.Date;
public class ExpenseAppGUI extends JFrame {
    private JButton addButton;
    private ExpenseTrackerAppGUI expenseTrackerAppGUI;
    private CategoryAppGUI categoryAppGUI;
    private JButton expenseTrackerButton;
    private JButton categoryButton;
    private JButton exitButton;
    private BoxLayout boxLayout;
    private JPanel panel;
    public ExpenseAppGUI() {
        
        initializeComponents();
        setupComponents();
        setupEventListeners();
    }
    private void initializeComponents() 
    {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 500);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
    }

    private void setupComponents() {
        setLayout(new BorderLayout());
    
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); 
    
        // Initialize buttons
        expenseTrackerButton = new JButton("Expense Tracker");
        categoryButton = new JButton("Category Management");
        exitButton = new JButton("Exit");
        
    
        // Center align each button
        expenseTrackerButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        categoryButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
    
        // Add buttons horizontally with proper spacing
        panel.add(Box.createHorizontalGlue()); // Push buttons to center
        panel.add(expenseTrackerButton);
        panel.add(Box.createHorizontalStrut(20)); // spacing
        panel.add(categoryButton);
        panel.add(Box.createHorizontalStrut(20)); // spacing
        panel.add(exitButton);
        panel.add(Box.createHorizontalGlue()); // Push buttons to center
    
        // Put the panel in the center
        add(panel, BorderLayout.CENTER);
    }
    private void setupEventListeners() {
        expenseTrackerButton.addActionListener(e -> expenseTrigger());
        categoryButton.addActionListener(e -> categoryTrigger());
        exitButton.addActionListener(e -> System.exit(0));
    }
    private void expenseTrigger() {
        new ExpenseTrackerAppGUI().setVisible(true);
    }
    private void categoryTrigger() {
        new CategoryAppGUI().setVisible(true);
    }
}
class ExpenseTrackerAppGUI extends JFrame {
    private CategoryAPPDAO categoryAppDAO;
    private ExpenseTrackerAppDAO expenseTrackerAppDAO;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JCheckBox completedCheckBox;
    private JButton addButton, deleteButton, editButton, refreshButton,closeButton;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> categoryInputComboBox;
    private JComboBox<String> filterComboBox;

    public ExpenseTrackerAppGUI() {
        categoryAppDAO = new CategoryAPPDAO();
        expenseTrackerAppDAO = new ExpenseTrackerAppDAO();
        expenseTable = new JTable();
        tableModel = new DefaultTableModel();
        initializeComponents();
        setupComponents();
        setupEventListeners();
        loadExpenses();
    }

    private void initializeComponents() {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Amount", "Description", "Category", "Created At", "Updated At"};
tableModel = new DefaultTableModel(columnNames, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

expenseTable = new JTable(tableModel);
expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Inputs
titleField = new JTextField(25);

descriptionArea = new JTextArea(4, 25);
descriptionArea.setEditable(true);
descriptionArea.setLineWrap(true);
descriptionArea.setWrapStyleWord(true);

// Replace "Completed" checkbox with Category dropdown
String[] categoryOptions = loadCategoryOptions();
categoryInputComboBox = new JComboBox<>(categoryOptions);

// Buttons
addButton = new JButton("Add");
deleteButton = new JButton("Delete");
editButton = new JButton("Edit");
refreshButton = new JButton("Refresh");

// Filter dropdown (for filtering, separate from input dropdown)
filterComboBox = new JComboBox<>(categoryOptions);
}

private void setupComponents() {
    setLayout(new BorderLayout());

    // Input panel for title, description, category dropdown
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    // Title
    gbc.gridx = 0;
    gbc.gridy = 0;
    inputPanel.add(new JLabel("Title:"), gbc);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    inputPanel.add(titleField, gbc);

    // Description
    gbc.gridx = 0;
    gbc.gridy = 1;
    inputPanel.add(new JLabel("Description:"), gbc);
    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    inputPanel.add(new JScrollPane(descriptionArea), gbc);

    // Category dropdown (instead of completed checkbox)
    gbc.gridx = 0;
    gbc.gridy = 2;
    inputPanel.add(new JLabel("Category:"), gbc);
    gbc.gridx = 1;
    inputPanel.add(categoryInputComboBox, gbc);

    // Button panel for Add, Update, Delete, Refresh
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.add(addButton);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(refreshButton);

    // Filter panel for filter label and combo box
    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    filterPanel.add(new JLabel("Filter:"));
    filterPanel.add(filterComboBox);

    // North panel to combine filter, input, and button panels
    JPanel northPanel = new JPanel(new BorderLayout());
    northPanel.add(filterPanel, BorderLayout.NORTH);
    northPanel.add(inputPanel, BorderLayout.CENTER);
    northPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(northPanel, BorderLayout.NORTH);
    add(new JScrollPane(expenseTable), BorderLayout.CENTER);

    JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(new JLabel("Select an expense to edit or delete:"));
    add(statusPanel, BorderLayout.SOUTH);
}

private void setupEventListeners() {
    addButton.addActionListener(e -> addExpense());
    editButton.addActionListener(e -> updateExpense());
    deleteButton.addActionListener(e -> deleteExpense());
    refreshButton.addActionListener(e -> refreshExpense());
    filterComboBox.addActionListener(e -> filterExpense());
    expenseTable.getSelectionModel().addListSelectionListener(event -> {
        if (!event.getValueIsAdjusting()) {
            loadSelectedExpense();
        }
    });
}

private String[] loadCategoryOptions() {
    try {
        List<Category> categories = categoryAppDAO.getAllCategories();
        return categories.stream().map(Category::getCategory_name).toArray(String[]::new);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return new String[0];
    }
}

private void loadExpenses() {
    try {
        List<Expense> expenses = expenseTrackerAppDAO.getAllExpenses();
        updateTable(todos);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading todos: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }
    
    private void addExpense() {
        // TODO: Implement add expense functionality
        System.out.println("Adding expense...");
    }
    
    private void updateExpense() {
        // TODO: Implement update expense functionality
        System.out.println("Updating expense...");
    }
    
    private void deleteExpense() {
        // TODO: Implement delete expense functionality
        System.out.println("Deleting expense...");
    }
    
    private void refreshExpense() {
        // TODO: Implement refresh expenses functionality
        System.out.println("Refreshing expenses...");
    }
    
    private void loadSelectedExpense() {
        // TODO: Implement load selected expense functionality
        System.out.println("Loading selected expense...");
    }

    private void filterExpense() {
        // TODO: Implement filter expenses functionality
        System.out.println("Filtering expenses...");
    }
}


class CategoryAppGUI extends JFrame {
    public CategoryAppGUI() {
        setTitle("Category Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
    }
}
