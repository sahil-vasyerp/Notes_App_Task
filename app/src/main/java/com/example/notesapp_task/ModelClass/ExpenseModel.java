package com.example.notesapp_task.ModelClass;

import java.io.Serializable;

public class ExpenseModel implements Serializable {
    private final String titleNotes;
    private final String descriptionNotes;
    private final int color;

    public ExpenseModel(String titleNotes, String descriptionNotes,int color) {
        this.titleNotes = titleNotes;
        this.descriptionNotes = descriptionNotes;
        this.color=color;
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
}
