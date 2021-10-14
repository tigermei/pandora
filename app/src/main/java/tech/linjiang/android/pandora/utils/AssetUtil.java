package tech.linjiang.android.pandora.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by linjiang on 05/06/2018.
 */

public class AssetUtil {


    private static String TARGET_BASE_PATH = "";

    public static String copyAssertToFiles(Context context) {
        TARGET_BASE_PATH = context.getFilesDir().getPath().concat(File.separator);
        copyFileOrDir(context, "");
        return TARGET_BASE_PATH;
    }

    private static void copyFileOrDir(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        String assets[];
        try {
            Log.i("tag", "copyFileOrDir() " + path);
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(context, path);
            } else {
                String fullPath = TARGET_BASE_PATH + path;
                Log.i("tag", "path=" + fullPath);
                File dir = new File(fullPath);
                if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    if (!dir.mkdirs())
                        Log.i("tag", "could not create dir " + fullPath);
                for (int i = 0; i < assets.length; ++i) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                        copyFileOrDir(context, p + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private static void copyFile(Context context, String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            Log.i("tag", "copyFile() " + filename);
            in = assetManager.open(filename);
            newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName, e);
        } finally {
            try {
                if(null != in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(null != out){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
