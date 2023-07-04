package com.example.expendituremanagementapp.model;

public class ExpenseTypeStatistic {
    private int id;
    private String name;
    private Float total;

    public ExpenseTypeStatistic(int id, String name, Float total) {
        this.id = id;
        this.name = name;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
