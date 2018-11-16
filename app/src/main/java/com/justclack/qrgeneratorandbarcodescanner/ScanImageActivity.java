package com.justclack.qrgeneratorandbarcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;

public class ScanImageActivity extends AppCompatActivity implements View.OnClickListener {

    //initialize variables to make them global
    private ImageView Scan;
    private static final int SELECT_PHOTO = 100;
    //for easy manipulation of the result
    public String barcode;
    TextView marquee;
    private StartAppAd startAppAd = new StartAppAd(this);

    //call oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);
        marquee = findViewById(R.id.marquee);
        //marquee.setSelected(true);

        //cast neccesary variables to their views
        Scan = findViewById(R.id.ScanBut);

        //set a new custom listener
        Scan.setOnClickListener(this);
        //launch gallery via intent
        /*Intent photoPic = new Intent(Intent.ACTION_PICK);
        photoPic.setType("image/*");
        startActivityForResult(photoPic, SELECT_PHOTO);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ScanBut:
                //launch gallery via intent
                Intent photoPic = new Intent(Intent.ACTION_PICK);
                photoPic.setType("image/*");
                startActivityForResult(photoPic, SELECT_PHOTO);
                break;
        }
    }


    //call the onactivity result method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
//doing some uri parsing
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        //getting the image
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    //decoding bitmap
                    Bitmap bMap = BitmapFactory.decodeStream(imageStream);
                    Scan.setImageURI(selectedImage);// To display selected image in image view
                    int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
                    // copy pixel data from the Bitmap into the 'intArray' array
                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
                            bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
                            bMap.getHeight(), intArray);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();// use this otherwise
                    // ChecksumException
                    try {
                        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
                        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                        decodeHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

                        Result result = reader.decode(bitmap, decodeHints);
                        //*I have created a global string variable by the name of barcode to easily manipulate data across the application*//
                        barcode = result.getText().toString();

                        //do something with the results for demo i created a popup dialog
                        if (barcode != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Scan Result");
                            builder.setIcon(R.drawable.ic_done_black_24dp);
                            builder.setMessage("" + barcode);
                            AlertDialog alert1 = builder.create();
                            alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    startAppAd.showAd(new AdDisplayListener() {

                                        /**
                                         * Callback when Ad has been hidden
                                         * @param
                                         */
                                        @Override
                                        public void adHidden(Ad ad) {
                                            Intent i = new Intent(getBaseContext(), SaveCodeActivity.class);
                                            i.putExtra("bar_code", barcode);
                                            startActivity(i);
                                            dialog.dismiss();
                                        }

                                        /**
                                         * Callback when ad has been displayed
                                         * @param
                                         */
                                        @Override
                                        public void adDisplayed(Ad ad) {

                                        }

                                        /**
                                         * Callback when ad has been clicked
                                         * @param
                                         */
                                        @Override
                                        public void adClicked(Ad arg0) {

                                        }

                                        /**
                                         * Callback when ad not displayed
                                         * @param
                                         */
                                        @Override
                                        public void adNotDisplayed(Ad arg0) {
                                            Intent i = new Intent(getBaseContext(), SaveCodeActivity.class);
                                            i.putExtra("bar_code", barcode);
                                            startActivity(i);
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            });

                            alert1.setCanceledOnTouchOutside(false);

                            alert1.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Scan Result");
                            builder.setIcon(R.drawable.ic_close_black_24dp);
                            builder.setMessage("Nothing found try a different image or try again");
                            AlertDialog alert1 = builder.create();
                            alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(i);
                                }
                            });

                            alert1.setCanceledOnTouchOutside(false);

                            alert1.show();

                        }
                        //the end of do something with the button statement.

                    } catch (NotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (ChecksumException e) {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (FormatException e) {
                        Toast.makeText(getApplicationContext(), "Wrong Barcode/QR format", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    public void onBackPressed() {
        //startAppAd.onBackPressed();
        super.onBackPressed();
    }

}