package com.justclack.qrgeneratorandbarcodescanner;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView gen, scan, hist, shis;
    StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "164252806", "209874594", true); //TODO: Replace with your Application ID
        setContentView(R.layout.activity_main);
        gen = findViewById(R.id.generate);
        scan = findViewById(R.id.scan);
        hist = findViewById(R.id.his);
        shis = findViewById(R.id.shis);

        gen.setOnClickListener(this);
        scan.setOnClickListener(this);
        hist.setOnClickListener(this);
        shis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (gen == v) {
            // Show an Ad
            startAppAd.showAd(new AdDisplayListener() {

                @Override
                public void adHidden(Ad ad) {

                    // Run second activity right after the ad was hidden
                    checkMain2Permission();
                }

                @Override
                public void adDisplayed(Ad ad) {

                }

                @Override
                public void adClicked(Ad arg0) {

                }

                @Override
                public void adNotDisplayed(Ad arg0) {
                    checkMain2Permission();
                }
            });
        }
        if (scan == v) {
            // Show an Ad
            startAppAd.showAd(new AdDisplayListener() {

                @Override
                public void adHidden(Ad ad) {

                    // Run second activity right after the ad was hidden
                    checkScanPermission();
                }

                @Override
                public void adDisplayed(Ad ad) {

                }

                @Override
                public void adClicked(Ad arg0) {

                }

                @Override
                public void adNotDisplayed(Ad arg0) {
                    checkScanPermission();
                }
            });

        }
        if (hist == v) {
            // Show an Ad
            startAppAd.showAd(new AdDisplayListener() {

                @Override
                public void adHidden(Ad ad) {

                    checkAAPermission();
                }

                @Override
                public void adDisplayed(Ad ad) {

                }

                @Override
                public void adClicked(Ad arg0) {

                }

                @Override
                public void adNotDisplayed(Ad arg0) {
                    checkAAPermission();
                }
            });

        }
        if (shis == v) {
            // Show an Ad
            startAppAd.showAd(new AdDisplayListener() {

                @Override
                public void adHidden(Ad ad) {

                    checkHistoryPermission();
                }

                @Override
                public void adDisplayed(Ad ad) {

                }

                @Override
                public void adClicked(Ad arg0) {

                }

                @Override
                public void adNotDisplayed(Ad arg0) {
                    checkHistoryPermission();
                }
            });

        }
    }

    private boolean checkHistoryPermission() {
        Dexter.withActivity(MainActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(MainActivity.this, "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(MainActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
        return true;
    }

    private boolean checkAAPermission() {
        Dexter.withActivity(MainActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(MainActivity.this, "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, AActivity.class));
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(MainActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
        return true;
    }

    private boolean checkScanPermission() {
        Dexter.withActivity(MainActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(MainActivity.this, "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, ScanActivity.class));
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(MainActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
        return true;
    }

    private boolean checkMain2Permission() {
        Dexter.withActivity(MainActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(MainActivity.this, "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Main2Activity.class));
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(MainActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
        return true;
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
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