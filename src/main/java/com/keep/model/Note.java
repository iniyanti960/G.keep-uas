package com.keep.model;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private String title;
    private String content;
    private boolean pinned;
    private boolean archived;
    private boolean deleted;
    private String color;
    private String label;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

public Note() {

}

public Note(String title, String content) {
    this.title = title;
    this.content = content;

    this.pinned = false;
    this.archived = false;
    this.deleted = false;

    this.color = "WHITE";
    this.label = "General";

    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pinned=" + pinned +
                ", archived=" + archived +
                ", deleted=" + deleted +
                ", color='" + color + '\'' +
                ", label='" + label + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
    
