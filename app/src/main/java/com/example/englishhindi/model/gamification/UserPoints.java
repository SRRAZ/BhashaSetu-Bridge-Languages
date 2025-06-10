package com.bhashasetu.app.model.gamification;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bhashasetu.app.database.converters.DateConverter;
import com.bhashasetu.app.database.converters.PointsSourceConverter;

import java.util.Date;

/**
 * Entity representing points earned by a user
 */
@Entity(tableName = "user_points")
@TypeConverters({DateConverter.class, PointsSourceConverter.class})
public class UserPoints {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int userId;
    private int points;
    private String description;
    private PointsSource source;
    private Date earnedAt;
    private int sourceId;  // ID of the source (e.g., exercise ID, achievement ID)
    
    public UserPoints() {
        this.earnedAt = new Date();
    }
    
    public UserPoints(int userId, int points, String description, PointsSource source, int sourceId) {
        this.userId = userId;
        this.points = points;
        this.description = description;
        this.source = source;
        this.earnedAt = new Date();
        this.sourceId = sourceId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PointsSource getSource() {
        return source;
    }
    
    public void setSource(PointsSource source) {
        this.source = source;
    }
    
    public Date getEarnedAt() {
        return earnedAt;
    }
    
    public void setEarnedAt(Date earnedAt) {
        this.earnedAt = earnedAt;
    }
    
    public int getSourceId() {
        return sourceId;
    }
    
    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}