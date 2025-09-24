package com.tracker.model;

import java.time.LocalDateTime;

public class Expense {
    private int id;
    private int amount;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int category_id;

    public Expense(){
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public Expense(int amount, String description, int category_id){
        this.amount = amount;
        this.description = description;
        this.category_id = category_id;
    }

    public Expense(int id, int amount, String description, int category_id){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category_id = category_id;
    }

    public Expense(int id, int amount, String description, int category_id, LocalDateTime created_at, LocalDateTime updated_at){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category_id = category_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters and Setters
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }
    
    public int getAmount() { 
        return amount; 
    }
    public void setAmount(int amount) { 
        this.amount = amount;
     }
    
    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public LocalDateTime getCreated_at() { 
        return created_at; 
    }
    public void setCreated_at(LocalDateTime created_at) { 
        this.created_at = created_at; 
    }
    
    public LocalDateTime getUpdated_at() { 
        return updated_at; 
    }
    public void setUpdated_at(LocalDateTime updated_at) {
         this.updated_at = updated_at;
         }
    
    public int getCategory_id() { 
        return category_id; 
    }
    public void setCategory_id(int category_id) {
         this.category_id = category_id; 
    }
}
