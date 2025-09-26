# Expense Calculator

A Java Swing-based desktop application for tracking personal expenses with MySQL database integration. This application provides a user-friendly interface for managing expenses and categories, allowing users to add, edit, delete, and filter their financial records.

## Features

### 🏠 Main Dashboard
- Clean and intuitive main menu with two primary functions:
  - **Expense Tracker**: Manage your daily expenses
  - **Category Management**: Organize expenses by categories

### 💰 Expense Management
- **Add Expenses**: Record new expenses with amount, description, and category
- **Edit Expenses**: Update existing expense records
- **Delete Expenses**: Remove unwanted expense entries
- **Filter by Category**: View expenses filtered by specific categories
- **Refresh Data**: Reload expense data from database
- **Real-time Table Display**: View all expenses in a sortable table format

### 📂 Category Management
- **Add Categories**: Create new expense categories (e.g., Food, Transportation, Entertainment)
- **Edit Categories**: Modify existing category names
- **Delete Categories**: Remove unused categories
- **Category List**: View all available categories

### 🗄️ Database Integration
- **MySQL Database**: Persistent storage for all data
- **Automatic Timestamps**: Tracks creation and update times
- **Data Validation**: Ensures data integrity and consistency

## Technology Stack

- **Java 11**: Core programming language
- **Java Swing**: GUI framework for desktop application
- **MySQL 8.0**: Database management system
- **Maven**: Build and dependency management
- **JDBC**: Database connectivity

## Prerequisites

Before running the application, ensure you have:

1. **Java 11 or higher** installed on your system
2. **MySQL 8.0** database server running
3. **Maven 3.6+** for building the project
4. **Git** for cloning the repository

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/Expense-Calculator.git
cd Expense-Calculator
```

### 2. Database Setup
1. Start your MySQL server
2. Create a database named `expenseTracker`:
   ```sql
   CREATE DATABASE expenseTracker;
   ```
3. Create the required tables:
   ```sql
   USE expenseTracker;
   
   -- Categories table
   CREATE TABLE category (
       id INT AUTO_INCREMENT PRIMARY KEY,
       Name VARCHAR(255) NOT NULL
   );
   
   -- Expenses table
   CREATE TABLE expensetracker (
       Id INT AUTO_INCREMENT PRIMARY KEY,
       Amount INT NOT NULL,
       Description TEXT,
       CategoryId INT,
       Created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       Updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       FOREIGN KEY (CategoryId) REFERENCES category(id)
   );
   ```

### 3. Configure Database Connection
Update the database connection details in `src/main/java/com/tracker/util/DatabaseConnection.java`:
```java
public static final String url = "jdbc:mysql://localhost:3306/expenseTracker";
public static final String username = "your_username";
public static final String password = "your_password";
```

### 4. Build and Run
```bash
# Compile the project
mvn compile

# Run the application
mvn exec:java

# Or create an executable JAR
mvn clean package
java -jar target/expense-calculator-1.0.0.jar
```

## Project Structure

```
src/main/java/com/tracker/
├── Main.java                    # Application entry point
├── dao/                         # Data Access Objects
│   ├── CategoryAPPDAO.java     # Category database operations
│   └── ExpenseTrackerAppDAO.java # Expense database operations
├── gui/                         # User Interface
│   └── ExpenseAppGUI.java      # Main GUI classes
├── model/                       # Data Models
│   ├── Category.java           # Category entity
│   └── Expense.java            # Expense entity
└── util/                        # Utilities
    └── DatabaseConnection.java # Database connection utility
```

## Usage Guide

### Getting Started
1. Launch the application using `mvn exec:java`
2. The main dashboard will appear with two options:
   - **Expense Tracker**: Click to manage your expenses
   - **Category Management**: Click to manage categories

### Managing Categories
1. Click "Category Management" from the main menu
2. Add new categories using the "Add" button
3. Select a category from the table to edit or delete it
4. Use "Refresh" to reload the category list

### Tracking Expenses
1. Click "Expense Tracker" from the main menu
2. Fill in the expense details:
   - **Amount**: Enter the expense amount (numbers only)
   - **Description**: Provide a description of the expense
   - **Category**: Select from available categories
3. Click "Add" to save the expense
4. Use the table to view, edit, or delete expenses
5. Use the "Filter" dropdown to view expenses by category

## Database Schema

### Categories Table
| Column | Type | Description |
|--------|------|-------------|
| id | INT | Primary key, auto-increment |
| Name | VARCHAR(255) | Category name |

### Expenses Table
| Column | Type | Description |
|--------|------|-------------|
| Id | INT | Primary key, auto-increment |
| Amount | INT | Expense amount |
| Description | TEXT | Expense description |
| CategoryId | INT | Foreign key to category table |
| Created_at | TIMESTAMP | Record creation time |
| Updated_at | TIMESTAMP | Last update time |

## Features in Detail

### Expense Management Features
- **CRUD Operations**: Complete Create, Read, Update, Delete functionality
- **Data Validation**: Input validation for amount and description fields
- **Category Integration**: Expenses are linked to categories for better organization
- **Filtering**: Filter expenses by category for better data analysis
- **Real-time Updates**: Changes are immediately reflected in the interface

### User Interface Features
- **Responsive Design**: Clean and intuitive interface
- **Table Selection**: Click to select and edit records
- **Error Handling**: User-friendly error messages
- **Data Refresh**: Manual refresh option to reload data

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL server is running
   - Check database credentials in `DatabaseConnection.java`
   - Ensure the `expenseTracker` database exists

2. **Application Won't Start**
   - Verify Java 11+ is installed: `java -version`
   - Check Maven installation: `mvn -version`
   - Ensure all dependencies are resolved: `mvn clean install`

3. **GUI Issues**
   - Try running with system look and feel
   - Check for Java Swing compatibility

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).

## Future Enhancements

- [ ] Export expenses to CSV/Excel
- [ ] Expense reports and analytics
- [ ] Date range filtering
- [ ] Search functionality
- [ ] Data backup and restore
- [ ] Multi-user support
- [ ] Mobile companion app

## Support

For support, please open an issue in the GitHub repository or contact the development team.

---

**Note**: This application is designed for personal expense tracking. For production use, consider implementing additional security measures and data validation.
