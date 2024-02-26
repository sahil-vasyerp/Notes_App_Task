package com.example.notesapp_task.ModelClass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "myNotes")
public class ExpenseModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;
    private  String titleNotes;
    private  String descriptionNotes;
    private  int color;
    private  String imageUri;
    private  String createDate;

    public ExpenseModel(@NonNull int id, String titleNotes, String descriptionNotes, int color, String imageUri, String createDate) {
        this.id = id;
        this.titleNotes = titleNotes;
        this.descriptionNotes = descriptionNotes;
        this.color = color;
        this.imageUri = imageUri;
        this.createDate = createDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id)
    {
        this.id=id;
    }

    public String getTitleNotes() {
        return titleNotes;
    }

    public void setTitleNotes(String titleNotes) {
        this.titleNotes = titleNotes;
    }

    public String getDescriptionNotes() {
        return descriptionNotes;
    }

    public void setDescriptionNotes(String descriptionNotes) {
        this.descriptionNotes = descriptionNotes;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
