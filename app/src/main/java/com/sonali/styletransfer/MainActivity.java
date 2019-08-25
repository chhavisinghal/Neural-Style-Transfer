package com.sonali.styletransfer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView ivPhoto;
    RecyclerView recyclerView;
    ArrayList<RecyclerViewModel> arrayList;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    private static final String MODEL_FILE_BossK_float = "file:///android_asset/bossK_float.pb";
    private static final String MODEL_FILE_Cubist_float = "file:///android_asset/cubist_float.pb";
    private static final String MODEL_FILE_Feathers_float = "file:///android_asset/feathers_float.pb";
    private static final String MODEL_FILE_Mosaic_float = "file:///android_asset/mosaic_float.pb";
    private static final String MODEL_FILE_Scream_float = "file:///android_asset/scream_float.pb";
    private static final String MODEL_FILE_Udnie_float = "file:///android_asset/udnie_float.pb";
    private static final String MODEL_FILE_Wave_float = "file:///android_asset/wave_float.pb";
    private static final String MODEL_FILE_Crayon_float = "file:///android_asset/crayon_float.pb";
    private static final String MODEL_FILE_Starry_float = "file:///android_asset/ink_float.pb";
    private static final String INPUT_NODE = "input";
    private static final String OUTPUT_NODE = "output";

    private int[] intValues;
    private float[] floatValues;
    Bitmap imageBitmap;
    private static final int CODE = 1;

    private TensorFlowInferenceInterface inferenceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        arrayList = new ArrayList<>();
        arrayList.add(new RecyclerViewModel(R.drawable.boss));
        arrayList.add(new RecyclerViewModel(R.drawable.crayon));
        arrayList.add(new RecyclerViewModel(R.drawable.cubist));
        arrayList.add(new RecyclerViewModel(R.drawable.feathers));
        arrayList.add(new RecyclerViewModel(R.drawable.mosaic));
        arrayList.add(new RecyclerViewModel(R.drawable.scream));
        arrayList.add(new RecyclerViewModel(R.drawable.starry));
        arrayList.add(new RecyclerViewModel(R.drawable.udnie));
        arrayList.add(new RecyclerViewModel(R.drawable.wave));

        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        Log.i("kkk","here1");
        recyclerViewAdapter.buttonSetOnclick(new RecyclerViewAdapter.ButtonInterface() {

            @Override
            public void onclick(View view, RecyclerViewModel model) {
                Log.i("kkk","here2");
                BitmapDrawable drawable = (BitmapDrawable) ivPhoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Log.i("kkk","here3");

                if (model.imgId == R.drawable.boss){
                    Log.i("kkk","here4");

                    initTensorFlowAndLoadModel(MODEL_FILE_BossK_float);
                    Log.i("kkk","here5");

                    Bitmap stylizeImage = stylizeImage(bitmap);
                    Log.i("kkk","here6");

                    ivPhoto.setImageBitmap(null);
                    Log.i("kkk","here7");

                    ivPhoto.setImageBitmap(stylizeImage);
                    Log.i("kkk","here8");
                }
                else if(model.imgId == R.drawable.cubist){
                    initTensorFlowAndLoadModel(MODEL_FILE_Cubist_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);
                }

                else if(model.imgId == R.drawable.feathers){
                    initTensorFlowAndLoadModel(MODEL_FILE_Feathers_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);

                } else if(model.imgId == R.drawable.mosaic){
                    initTensorFlowAndLoadModel(MODEL_FILE_Mosaic_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);

                } else if(model.imgId == R.drawable.scream){
                    initTensorFlowAndLoadModel(MODEL_FILE_Scream_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);

                } else if(model.imgId == R.drawable.udnie){
                    initTensorFlowAndLoadModel(MODEL_FILE_Udnie_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);

                } else if(model.imgId == R.drawable.wave){
                    initTensorFlowAndLoadModel(MODEL_FILE_Wave_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);

                } else if(model.imgId == R.drawable.crayon){
                    initTensorFlowAndLoadModel(MODEL_FILE_Crayon_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);

                } else if(model.imgId == R.drawable.starry){
                    initTensorFlowAndLoadModel(MODEL_FILE_Starry_float);
                    Bitmap stylizeImage = stylizeImage(bitmap);
                    ivPhoto.setImageBitmap(null);
                    ivPhoto.setImageBitmap(stylizeImage);
                }

            }
        });
    }




    private void initTensorFlowAndLoadModel(String MODEL) {
        Log.i("kkk","here9");

        intValues = new int[640 * 480];
        floatValues = new float[640 * 480 * 3];
        inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL);
        Log.i("kkk","here10");

    }



    private Bitmap stylizeImage(Bitmap bitmap) {
        //progress_bar.setVisibility(View.VISIBLE);
        Log.i("kkk","here11");

        Bitmap scaledBitmap = scaleBitmap(bitmap, 480, 640); // desiredSize
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight());
        for (int i = 0; i < intValues.length; ++i) {
            final int val = intValues[i];
            floatValues[i * 3] = ((val >> 16) & 0xFF) * 1.0f;
            floatValues[i * 3 + 1] = ((val >> 8) & 0xFF) * 1.0f;
            floatValues[i * 3 + 2] = (val & 0xFF) * 1.0f;
        }
        Log.i("kkk","here12");

        // Copy the input data into TensorFlow.
        inferenceInterface.feed(INPUT_NODE, floatValues, 640, 480, 3);
        // Run the inference call.
        inferenceInterface.run(new String[]{OUTPUT_NODE});
        // Copy the output Tensor back into the output array.
        inferenceInterface.fetch(OUTPUT_NODE, floatValues);

        for (int i = 0; i < intValues.length; ++i) {
            intValues[i] =
                    0xFF000000
                            | (((int) (floatValues[i * 3])) << 16)
                            | (((int) (floatValues[i * 3 + 1])) << 8)
                            | ((int) (floatValues[i * 3 + 2]));
        }
        scaledBitmap.setPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0,
                scaledBitmap.getWidth(), scaledBitmap.getHeight());
        Log.i("kkk","here13");

        return scaledBitmap;
    }

    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //matrix.postRotate(90);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CODE && data != null && data.getExtras() != null) {
                    imageBitmap = (Bitmap) data.getExtras().get("data");
                    ivPhoto.setImageBitmap(imageBitmap);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add("groupId", "ItemId", "OrderID", "title")
        MenuItem item = menu.add(0, 0, 0, "Add");
        item.setIcon(R.drawable.ic_camera);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 0:

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1);
                }
        }
        return super.onOptionsItemSelected(item);
    }
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("TAH", storageDir.toString());
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }
}
