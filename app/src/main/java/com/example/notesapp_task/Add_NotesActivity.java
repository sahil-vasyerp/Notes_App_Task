package com.example.notesapp_task;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.example.notesapp_task.viewmodel.NotesViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;


public class Add_NotesActivity extends AppCompatActivity implements View.OnClickListener {


    private NotesViewModel notesViewModel;
    TextInputEditText etTitle, etDescription;
    ExtendedFloatingActionButton btnSave;
    ExpenseModel expenseModel;
    View picColorButton;
    ImageButton uploadImage;
    ImageView showImage, backButton;
    Context context;
    Date date;
    private String currentDate;
    int mDefaultColor;
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
            mDefaultColor = expenseModel.getColor();

            if (expenseModel.getImageUri() != null) {

                Uri myUri = Uri.parse(expenseModel.getImageUri());
                imageUri = myUri.toString();
                Glide.with(this).load(myUri).into(showImage);

            }

        }
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSaveNotes);
        picColorButton = findViewById(R.id.pick_color_button);
        uploadImage = findViewById(R.id.uploadImage);
        showImage = findViewById(R.id.showImage);
        backButton = findViewById(R.id.backButton);


        context = Add_NotesActivity.this;
        mDefaultColor = context.getColor(R.color.list_zero);
        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);


        btnSave.setOnClickListener(this);
        picColorButton.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
        backButton.setOnClickListener(this);


        getDate();

    }

    private void getDate() {
        date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        currentDate = df.format(date);

//        Log.d("currentDate", "initViews: "+currentDate);


    }

    private void addArraylist() {

        String addTitle = etTitle.getText().toString().trim();
        String addDescription = etDescription.getText().toString().trim();


        if (addTitle.isEmpty() && addDescription.isEmpty()) {
            Toast.makeText(this, "No value ", Toast.LENGTH_SHORT).show();
        } else {

            int position = getIntent().getIntExtra("position", -1);


            expenseModel=new ExpenseModel(0,addTitle,addDescription,mDefaultColor,imageUri,currentDate);

            if (position == -1) {


//      -----------------------Room db value Store--------------------------------------------------
                notesViewModel.insert(expenseModel);
//      -----------------------Room db value Store--------------------------------------------------


            } else {
//      -----------------------Room db value update-------------------------------------------------
                expenseModel.setId(position);
                notesViewModel.update(expenseModel);
//      -----------------------Room db value update-------------------------------------------------



            }


            Intent i = new Intent();

            setResult(RESULT_OK, i);
            finish();

        }


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnSaveNotes: {
                addArraylist();
                break;
            }
            case R.id.pick_color_button: {
                openColorPickerDialogue();
                break;
            }
            case R.id.uploadImage: {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), SELECT_PICTURE);
                break;
            }
            case R.id.backButton: {
                finish();
                break;
            }
        }
    }

    private void openColorPickerDialogue() {
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

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri != null) {
                    imageUri = selectedImageUri.toString();
                    showImage.setImageURI(selectedImageUri);

                }
            }
        }
    }


}