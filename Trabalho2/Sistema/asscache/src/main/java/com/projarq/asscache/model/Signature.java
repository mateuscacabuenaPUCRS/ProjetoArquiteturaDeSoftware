package com.projarq.asscache.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean active;
    private Date expiryDate;

    public Signature() {
    }

    public Signature(Long id) {
        this.id = id;
    }

    public Signature(Long id, boolean active, Date expiryDate) {
        this.id = id;
        this.active = active;
        this.expiryDate = expiryDate;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "id=" + id +
                ", active=" + active +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
