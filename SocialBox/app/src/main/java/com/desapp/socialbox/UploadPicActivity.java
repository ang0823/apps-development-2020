package com.desapp.socialbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.desapp.socialbox.services.network.ApiEndpoint;
import com.desapp.socialbox.services.network.VolleyRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadPicActivity extends AppCompatActivity {
    ImageView selectedImg;
    EditText imgDesc;
    Button selectImgButton;
    Button uploadImgButton;
    final int PICK_IMAGE = 1;
    String username;
    Bitmap bitmap;
    VolleyRequest volley;
    RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        initializeComponents();
    }

    private void initializeComponents() {
        Intent intent = getIntent();
        username = intent.getStringExtra("sender");
        selectedImg = findViewById(R.id.selected_img);
        imgDesc = findViewById(R.id.img_desc_input);
        selectImgButton = findViewById(R.id.btn_select);
        uploadImgButton = findViewById(R.id.btn_upload);
        volley = VolleyRequest.getInstance(UploadPicActivity.this);
        colaPeticiones = volley.getColaPeticiones();

        selectImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });

        uploadImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(v);
            }
        });
    }

    private void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imgPath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgPath);
                selectedImg.setImageBitmap(bitmap);
                selectedImg.setVisibility(View.VISIBLE);
                imgDesc.setVisibility(View.VISIBLE);
                uploadImgButton.setEnabled(true);
            } catch (IOException e) {
                Toast.makeText(UploadPicActivity.this,
                        "No se pudo cargar la imagen:\n" + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage(View view) {
        String image = bitmapToString();
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("image", image);
        params.put("description", imgDesc.getText().toString().trim());

        JSONObject body = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiEndpoint.uploadPic,
                body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(UploadPicActivity.this, "La imagen ha sido guardada", Toast.LENGTH_SHORT).show();
                selectedImg.setVisibility(View.GONE);
                imgDesc.setVisibility(View.GONE);
                uploadImgButton.setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UploadPicActivity.this, "Ocurrió un error:\n" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        volley.agregarACola(request);
    }

    private String bitmapToString() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);
        byte[] imgBytes = byteArray.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}