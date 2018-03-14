package com.yaoyi.getlocalvideodemo.my.util;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by c on 13/03/2018.
 */

public class MyPermissionRequestUtil {
    private Activity mActivity;

    public MyPermissionRequestUtil(Activity activity) {
        mActivity = activity;
    }

    /**
     * Handle external storage permission.
     */
    public void externalStorage() {
        new ExternalStoragePermission().myRequestPermission();
    }

    /**
     * The type External storage permission.
     */
    private class ExternalStoragePermission {

        /**
         * Check external storage accessible ,or finish the current activity.
         */
        protected boolean checkExternalStorageAccessible() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                Toast.makeText(mActivity, "外部存储器挂载成功", Toast.LENGTH_LONG).show();

                return true;
            } else {
                Toast.makeText(mActivity, "外部存储器未挂载", Toast.LENGTH_LONG).show();
                mActivity.finish();
                return false;
            }
        }

        /**
         * My request permission.
         */
        private void myRequestPermission() {
            checkExternalStorageAccessible();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 0;
                mActivity.requestPermissions(new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
        }

    }


}
