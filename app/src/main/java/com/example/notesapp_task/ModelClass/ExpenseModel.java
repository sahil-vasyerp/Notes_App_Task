package com.example.notesapp_task.ModelClass;

import java.io.Serializable;

public class ExpenseModel implements Serializable {
    private final String titleNotes;
    private final String descriptionNotes;
    private final int color;
    private final String imageUri;
    private final String createDate;

    public ExpenseModel(String titleNotes, String descriptionNotes,int color ,String createDate,String imageUri) {
        this.titleNotes = titleNotes;
        this.descriptionNotes = descriptionNotes;
        this.color=color;
        this.createDate=createDate;
        this.imageUri=imageUri;
    }

    public String getTitleNotes() {
        return titleNotes;
    }

    public String getDescriptionNotes() {
        return descriptionNotes;
    }

    public int getColor() {
        return color;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getImageUri() {
        return imageUri;
    }
}
