package com.bhashasetu.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String email;
    private String fullName;
    private String profileImageUrl;
    private String preferredLanguage; // "english" or "hindi"
    private int totalPoints;
    private int currentStreak;
    private int maxStreak;
    private Date dateJoined;
    private Date lastActiveDate;
    private String learningGoal;
    private boolean isActive;

    // Constructors
    public User() {
        this.dateJoined = new Date();
        this.lastActiveDate = new Date();
        this.totalPoints = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.isActive = true;
        this.preferredLanguage = "english";
    }

    public User(String username, String email, String fullName) {
        this();
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }

    // Getters and Setters (implement all)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }

    public int getMaxStreak() { return maxStreak; }
    public void setMaxStreak(int maxStreak) { this.maxStreak = maxStreak; }

    public Date getDateJoined() { return dateJoined; }
    public void setDateJoined(Date dateJoined) { this.dateJoined = dateJoined; }

    public Date getLastActiveDate() { return lastActiveDate; }
    public void setLastActiveDate(Date lastActiveDate) { this.lastActiveDate = lastActiveDate; }

    public String getLearningGoal() { return learningGoal; }
    public void setLearningGoal(String learningGoal) { this.learningGoal = learningGoal; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}