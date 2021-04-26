package com.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=20, unique = true)
    private String userID;
    
    private String name;
    private String password;
    private String email;

    public String getUserID() {
        return this.userID;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public boolean matchID(Long newID) {
        if (newID == null) {
            return false;
        }

        return newID.equals(id);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(User newUser) {
        this.userID = newUser.userID;
        this.email = newUser.email;
        this.name = newUser.name;
    }

    @Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}
}
