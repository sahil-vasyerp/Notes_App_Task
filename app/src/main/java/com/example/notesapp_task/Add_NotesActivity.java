package com.example.notesapp_task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;


public class Add_NotesActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etTitle, etDescription;

    ExtendedFloatingActionButton btnSave;
    ExpenseModel expenseModel;
    View picColorButton;
    ImageButton uploadImage;
    ImageView showImage;
    Context context;
    Date date;
    private String currentDate;
    int  mDefaultColor;
    int SELECT_PICTURE = 200;
    String imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);


        initViews();
        getValues();
    }

    private void getValues() {

        expenseModel = null;


        if (getIntent().hasExtra(MainActivity.NEXT_SCREEN)) {
            expenseModel = (ExpenseModel) getIntent().getSerializableExtra(MainActivity.NEXT_SCREEN);

        }
        if (expenseModel != null) {
            etTitle.setText(expenseModel.getTitleNotes());
            etDescription.setText(expenseModel.getDescriptionNotes());

            if (expenseModel.getImageUri() != null)
            {

                Uri myUri = Uri.parse(expenseModel.getImageUri());
                imageUri=myUri.toString();
                Glide.with(this).load(myUri).into(showImage);

            }

        }
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSaveNotes);
        picColorButton=findViewById(R.id.pick_color_button);
        uploadImage=findViewById(R.id.uploadImage);
        showImage=findViewById(R.id.showImage);


        context=Add_NotesActivity.this;
        mDefaultColor =context.getColor(R.color.list_zero);





        btnSave.setOnClickListener(this);
        picColorButton.setOnClickListener(this);
        uploadImage.setOnClickListener(this);


        getDate();

    }

    private void getDate() {
        date= Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault());
        currentDate=df.format(date);

//        Log.d("currentDate", "initViews: "+currentDate);


    }

    private void addArraylist() {

        String addTitle = etTitle.getText().toString().trim();
        String addDescription = etDescription.getText().toString().trim();


        if (addTitle.isEmpty() && addDescription.isEmpty())
        {
            Toast.makeText(this,"No value ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int position = getIntent().getIntExtra("position",-1);




            Intent i = new Intent();
            i.putExtra("position", position);
            i.putExtra("title", addTitle);
            i.putExtra("description", addDescription);
            i.putExtra("date",currentDate);
            i.putExtra("color",mDefaultColor);
            i.putExtra("imageUri",imageUri);


            setResult(RESULT_OK, i);
            finish();

        }



    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.btnSaveNotes:
            {
                addArraylist();
                break;
            }
            case R.id.pick_color_button:
            {
                openColorPickerDialogue();
                break;
            }
            case R.id.uploadImage:
            {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), SELECT_PICTURE);
                break;
            }
        }
    }

    private void openColorPickerDialogue()
    {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {

                        mDefaultColor = color;

                        picColorButton.setBackgroundColor(mDefaultColor);

                    }
                });
        colorPickerDialogue.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK)
        {
            if (requestCode==SELECT_PICTURE)
            {
                Uri selectedImageUri=data.getData();

                if (selectedImageUri != null)
                {
                    imageUri=selectedImageUri.toString();
                    showImage.setImageURI(selectedImageUri);

                }
            }
        }
    }
}