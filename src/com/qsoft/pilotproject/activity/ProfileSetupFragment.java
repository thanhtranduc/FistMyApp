package com.qsoft.pilotproject.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;
import com.qsoft.pilotproject.R;

/**
 * User: thanhtd
 * Date: 10/14/13
 * Time: 3:48 PM
 */
public class ProfileSetupFragment extends FragmentActivity {
    final Context context = this;
    static final int DATE_DIALOG_ID = 999;
    private static int RESULT_LOAD_IMAGE = 1;
    private int year;
    private int month;
    private int day;
    private DatePicker dpResult;
    private RelativeLayout rlCover;
    private ImageView ivProfile;
    private EditText tvBirthday;
    private EditText tvCountry;
    private ImageButton ibLeft;
    private ImageButton ibRight;
    private Boolean flag = null;
    private ScrollView svDescription;
    private EditText etDescription;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        tvBirthday = (EditText) findViewById(R.id.profile_et_birthday);
        dpResult = (DatePicker) findViewById(R.id.dpResult);
        rlCover = (RelativeLayout) findViewById(R.id.profile_relativeLayout);
        rlCover.setOnClickListener(ivCoverListener);
        ivProfile = (ImageView) findViewById(R.id.profile_iv_icon);
        ivProfile.setOnClickListener(ivProfileListener);
        tvBirthday.setOnClickListener(tvBirthdayListener);
        ibLeft = (ImageButton) findViewById(R.id.profile_ibleft);
        ibLeft.setSelected(true);
        ibRight = (ImageButton) findViewById(R.id.profile_ibright);
        ibRight.setSelected(false);
        ibLeft.setOnClickListener(ibLeftListener);
        ibRight.setOnClickListener(ibRightListener);
        tvCountry = (EditText) findViewById(R.id.profile_et_country);
        tvCountry.setOnClickListener(btArrowCountryListener);
        etDescription = (EditText) findViewById(R.id.profile_et_desciption);
    }

    View.OnClickListener ibLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ibLeft.isSelected()) {

            } else {
                ibLeft.setSelected(true);
                ibLeft.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_select_left));
                ibRight.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_unselect_right));
                ibRight.setSelected(false);
            }
        }
    };

    View.OnClickListener ibRightListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ibRight.isSelected()) {

            } else {
                ibRight.setSelected(true);
                ibRight.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_select_right));
                ibLeft.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.profile_btn_unselect_left));
                ibLeft.setSelected(false);
            }
        }
    };

    View.OnClickListener ivCoverListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            flag = true;
            uploadImage();
        }
    };

    View.OnClickListener ivProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flag = false;
            uploadImage();
        }
    };

    private void uploadImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data && flag == true) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmImg = BitmapFactory.decodeFile(picturePath);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmImg, rlCover.getWidth(), rlCover.getHeight(), true);
            Drawable drawable = new BitmapDrawable(bMapScaled);
            rlCover.setBackgroundDrawable(drawable);
        }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data && flag == false)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmImg = BitmapFactory.decodeFile(picturePath);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bmImg, ivProfile.getWidth(), ivProfile.getHeight(), true);
            Drawable drawable = new BitmapDrawable(bMapScaled);

            ivProfile.setImageDrawable(drawable);

//            ivProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    View.OnClickListener tvBirthdayListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialog(DATE_DIALOG_ID);
        }
    };

    View.OnClickListener btArrowCountryListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewListCountry();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            tvBirthday.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            dpResult.init(year, month, day, null);
        }
    };

    private void viewListCountry() {
        final String[] countryList = getResources().getStringArray(R.array.country);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("list country");

        builder.setSingleChoiceItems(countryList, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvCountry.setText(countryList[which]);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
