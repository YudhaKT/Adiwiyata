package com.example.adiwiyata_admin;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCode extends AppCompatActivity {
    String TAG = "GenerateQRCode";
    EditText edtValue;
    ImageView qrImage;
    Button start;
    Button save;
    ImageButton btnBack;
    String inputValue;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        qrImage = findViewById(R.id.QR_Image);
        edtValue = findViewById(R.id.edt_value);
        btnBack = findViewById(R.id.btn_back);
        start = findViewById(R.id.start);
        save = findViewById(R.id.save);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue = edtValue.getText().toString().trim();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    edtValue.setError("Required");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to get the image from the ImageView (say iv)
                BitmapDrawable draw = (BitmapDrawable) qrImage.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                //File sdCard = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/QRCodeAdiwiyata");
                dir.mkdirs();
                String fileName = edtValue.getText().toString() +".jpg";
                File outFile = new File(dir, fileName);
                try{
                    outStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    Toast.makeText(QRCode.this, "QRCode berhasil disimpan di "+ dir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
/*public class QRCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText edtValue;
        ImageView qrImage;
        Button start, save;
        ImageButton btnBack;
        String inputValue;
        Bitmap bitmap;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        qrImage = findViewById(R.id.QR_Image);
        edtValue = findViewById(R.id.edt_value);
        btnBack = findViewById(R.id.btn_back);
        start = findViewById(R.id.start);
        save = findViewById(R.id.save);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // generate a 150x150 QR code
                    Bitmap bm = encodeAsBitmap(edtValue, BarcodeFormat.QR_CODE, 150, 150);

                    if(bm != null) {
                        qrImage.setImageBitmap(bm);
                    }
                } catch (WriterException e) { //eek }
            }
        });
    }
}*/
