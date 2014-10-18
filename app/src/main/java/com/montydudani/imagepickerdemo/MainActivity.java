package com.montydudani.imagepickerdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private ImageView imageView;
    private Uri    mImageCaptureUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = null;
                if (isExternalStorageWritable()) {
                    file = Environment.getExternalStorageDirectory();
                } else {
                    file = getDir("Image", Context.MODE_PRIVATE);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageCaptureUri = Uri.fromFile(new File(file, "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivityForResult(intent, 1);


            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra("return-data", true);
                startActivityForResult(galleryIntent, 3);
            }
        });

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    imageView.setImageURI(mImageCaptureUri);
                    break;
                case 3:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        imageView.setImageBitmap(photo);
                    }
                    break;
            }
        }
    }



}
