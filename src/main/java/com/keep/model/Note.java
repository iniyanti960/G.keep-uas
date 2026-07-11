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

    private LocalDateTime reminderAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Note() {

    }

    public Note(String title, String content) {

        this.title = title;
        this.content = content;

        pinned = false;
        archived = false;
        deleted = false;

        color = "WHITE";
        label = "General";

        reminderAt = null;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

    }

    public Note(
            int id,
            String title,
            String content,
            boolean pinned,
            boolean archived,
            boolean deleted,
            String color,
            String label,
            LocalDateTime reminderAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.title = title;
        this.content = content;

        this.pinned = pinned;
        this.archived = archived;
        this.deleted = deleted;

        this.color = color;
        this.label = label;

        this.reminderAt = reminderAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

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

    public LocalDateTime getReminderAt() {
        return reminderAt;
    }

    public void setReminderAt(LocalDateTime reminderAt) {
        this.reminderAt = reminderAt;
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
                ", reminderAt=" + reminderAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';

    }

}