package com.zona.recreativacr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zona.recreativacr.com.zona.data.Package;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PackagesAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button uploadImgBtn;
    EditText nombreET, capacidadET, precioET, descripcionET;
    Switch breakfastSW, coffeeSW, lunchSW, activeSW;
    Spinner typeSP;
    String type = "";
    ProgressBar packagesAddPB;
    Uri imgUri = null;
    Package packag = null;
    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_add);
        setTitle("Agregar un nuevo paquete");

        nombreET = findViewById(R.id.name_pq_editText);
        capacidadET = findViewById(R.id.capacity_pq_editText);
        precioET = findViewById(R.id.price_pq_editText);
        typeSP = findViewById(R.id.type_spinner);
        descripcionET = findViewById(R.id.descripcion_pq_editText);
        breakfastSW = findViewById(R.id.breakfast_switch);
        coffeeSW = findViewById(R.id.coffee_switch);
        lunchSW = findViewById(R.id.lunch_switch);
        activeSW = findViewById(R.id.active_switch);
        packagesAddPB = findViewById(R.id.packageAdd_progressBar);
        uploadImgBtn = findViewById(R.id.uploadImg_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeSP.setAdapter(adapter);
        typeSP.setOnItemSelectedListener(this);

        if(getIntent().hasExtra("package")) {
            packag = getIntent().getParcelableExtra("package");
            nombreET.setText(packag.name);
            capacidadET.setText(packag.capacity);
            precioET.setText(packag.price);
            setSelectionSpinner(packag.type);
            descripcionET.setText(packag.descrip);
            if(packag.breakfast)
                breakfastSW.setChecked(true);
            if(packag.coffe)
                coffeeSW.setChecked(true);
            if(packag.lunch)
                lunchSW.setChecked(true);
            if(packag.active)
                activeSW.setChecked(true);
            uploadImgBtn.setVisibility(View.GONE);
            uploadImgBtn.setEnabled(false);
        }
    }

    public void addPackage(final View view){
        packagesAddPB.setVisibility(View.VISIBLE);
        mDatabase = FirebaseFirestore.getInstance();
        final String name = nombreET.getText().toString();
        final String capacity = capacidadET.getText().toString();
        final String price = precioET.getText().toString();
        final String description = descripcionET.getText().toString();
        final boolean breakfast = breakfastSW.isChecked();
        final boolean coffee = coffeeSW.isChecked();
        final boolean lunch = lunchSW.isChecked();
        final boolean active = activeSW.isChecked();
        final String idDoc = UUID.randomUUID().toString();

        if(packag == null) {
            String refImage = "";
            if(imgUri != null) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                refImage = "packages/"+idDoc+"/"+idDoc+".png";
                final StorageReference imagesRef = storageRef.child(refImage);

                Bitmap img = getResizedBitmap(imgUri, 500);
                //Bitmap thumbnail = getResizedBitmap(imgRealPath, 75);

                ByteArrayOutputStream baosImg = new ByteArrayOutputStream();
                ByteArrayOutputStream baosThumb = new ByteArrayOutputStream();

                if (img != null) {
                    //Log.d("ImagenID", idDoc);
                    img.compress(Bitmap.CompressFormat.PNG, 100, baosImg);
                    byte[] imgData = baosImg.toByteArray();
                    final String finalRefImage = refImage;
                    imagesRef.putBytes(imgData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Log.d("ImagenUri", uri.toString());
                                    onCallBack(name, capacity, price, description, breakfast, coffee, lunch, active, idDoc, uri.toString(),
                                            finalRefImage, uri.toString(), finalRefImage, view);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Log.d("Imagen", e.getMessage());
                        }
                    });
                }
            }
        } else {
            Map<String, Object> docData = new HashMap<>();
            docData.put("active", active);
            docData.put("breakfast", breakfast);
            docData.put("capacity", capacity);
            docData.put("coffe", coffee);
            docData.put("descrip", description);
            docData.put("id", packag.id);
            docData.put("imgURL", packag.imgURL);
            docData.put("lunch", lunch);
            docData.put("name", name);
            docData.put("price", price);
            docData.put("refImage", packag.refImage);
            docData.put("refThumbnail", packag.refThumbnail);
            docData.put("thumbnailURL", packag.thumbnailURL);
            docData.put("type", type);

            mDatabase.collection("Paquetes").document(packag.id)
                    .set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar snackbar;
                            snackbar = Snackbar.make(view, R.string.okUpdated,
                                    Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                                    R.color.LightBlueDark));
                            snackbar.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar snackbar;
                    snackbar = Snackbar.make(view, R.string.errorUpdated,
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                            R.color.LightBlueDark));
                    snackbar.show();
                }
            });
            packagesAddPB.setVisibility(View.GONE);
        }
    }

    private void addPackage_aux(String name, String capacity, String price, String description, boolean breakfast,
                                boolean coffee, boolean lunch, boolean active, String idDoc, String imgUrl, String refImage,
                                String thumbnailUrl, String refThumbnail, final View view){
        Map<String, Object> docData = new HashMap<>();
        docData.put("active", active);
        docData.put("breakfast", breakfast);
        docData.put("capacity", capacity);
        docData.put("coffe", coffee);
        docData.put("descrip", description);
        docData.put("id", idDoc);
        docData.put("imgURL", imgUrl);
        docData.put("lunch", lunch);
        docData.put("name", name);
        docData.put("price", price);
        docData.put("refImage", refImage);
        docData.put("refThumbnail", refThumbnail);
        docData.put("thumbnailURL", thumbnailUrl);
        docData.put("type", type);

        mDatabase.collection("Paquetes").document(idDoc).set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackbar;
                        snackbar = Snackbar.make(view, R.string.okCreated,
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                                R.color.LightBlueDark));
                        snackbar.show();
                        nombreET.setText("");
                        capacidadET.setText("");
                        precioET.setText("");
                        typeSP.setSelection(0);
                        descripcionET.setText("");
                        breakfastSW.setChecked(false);
                        coffeeSW.setChecked(false);
                        lunchSW.setChecked(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar;
                snackbar = Snackbar.make(view, R.string.errorCreated,
                        Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(getBaseContext(),
                        R.color.LightBlueDark));
                snackbar.show();
            }
        });
        packagesAddPB.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = (String) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
    }

    private void setSelectionSpinner(String type) {
        switch (type) {
            case "educativo":
                typeSP.setSelection(1);
                break;
            case "científico":
                typeSP.setSelection(2);
                break;
            case "recreativo":
                typeSP.setSelection(3);
                break;
            default:
                typeSP.setSelection(0);
                break;
        }
    }

    public void addImgToRequest(View view){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imgUri = imageReturnedIntent.getData();
                //Log.d("Imagen", imgUri);
            }
        }
    }

    private Bitmap getResizedBitmap(Uri img, int size) {
        try {
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img);
            bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void onCallBack(String name, String capacity, String price, String description, boolean breakfast,
                            boolean coffee, boolean lunch, boolean active, String idDoc, String imgUrl, String refImage,
                            String thumbnailUrl, String refThumbnail, final View view){
        addPackage_aux(name, capacity, price, description, breakfast,
        coffee, lunch, active, idDoc, imgUrl, refImage, thumbnailUrl, refThumbnail, view);
    }
}
