package com.example.notesapp_task.ModelClass;

import java.io.Serializable;

public class ExpenseModel implements Serializable {
    private final int id;
    private final String titleNotes;
    private final String descriptionNotes;
    private final int color;
    private final String imageUri;
    private final String createDate;

    public ExpenseModel(int id, String titleNotes, String descriptionNotes, int color , String imageUri,String createDate) {
        this.id = id;
        this.titleNotes = titleNotes;
        this.descriptionNotes = descriptionNotes;
        this.color=color;
        this.imageUri=imageUri;
        this.createDate=createDate;
    }

    public int getId() {
        return id;
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

    public String getImageUri() {
        return imageUri;
    }

    public String getCreateDate() {
        return createDate;
    }
}
