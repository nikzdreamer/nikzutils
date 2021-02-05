package com.nikzdevz.nikzutils;



import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.ComponentContainer;
import android.content.Context;
import android.app.Activity;
import android.os.Environment;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;


public class nikzutils {
    private final Context myContext;
    private final Activity myActivity;
    private final double deviceDensity;
    public ComponentContainer myCContainer;
    private final boolean isCompanion;
    private final String CompanionName;
    private final Options options = new Options();
    private final AssetManager assetManager;


    public nikzutils(ComponentContainer container){
        this.myCContainer = container;
        this.myContext = container.$context();
        this.myActivity = container.$context();
        deviceDensity = (double) container.$form().deviceDensity();
        isCompanion = (container.$form() instanceof ReplForm);

        if(isCompanion){
            if((myContext.getPackageName()).equals("io.makeroid.companion")){
                CompanionName = "KodularCompanion";
            }else if((myContext.getPackageName()).equals("edu.mit.appinventor.aicompanion3")){
                CompanionName = "AppInventorCompanion";
            }else {
                CompanionName = "OtherCompanion";
            }
        }else{
            CompanionName = "NoCompanion";
        }

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inScreenDensity = (int) deviceDensity;
        options.inTargetDensity = (int) deviceDensity;
        options.inDensity = (int) deviceDensity;
        options.inSampleSize = 1;
        options.inScaled = true;
        assetManager =  myContext.getAssets();
    }

    public String getFilePath(String fileName){
//        String myPath = "";
//        if(isCompanion){
//            myPath = Environment.getExternalStorageDirectory().getPath();
//            if((myContext.getPackageName()).equals("io.makeroid.companion")){
//                myPath = myPath + "/Makeroid/assets/" + fileName;
//            }else if((myContext.getPackageName()).equals("edu.mit.appinventor.aicompanion3")){
//                myPath = myPath + "/Android/data/edu.mit.appinventor.aicompanion3/files/assets/" + fileName;
//            }
//        }
//        return ((File)myContext.getExternalFilesDir(null)).getPath() + "/assets/" + fileName;
        return myCContainer.$form().getAssetPath(fileName);
    }

    public double Px2Dp(float px){
        return (double)(px * deviceDensity);
    }

    public double Dp2Px(float Dp){
        return (double)(Dp/deviceDensity);
    }


    public double getDeviceDensity(){
        return deviceDensity;
    }

    public String getCompanionName(){
        return CompanionName;
    }


    public Bitmap getBitmap(String filePath){
        File myFile;
        Bitmap myBitmap = null;

        if (filePath.toLowerCase().startsWith("/")){
            myFile = new File(filePath);
            myBitmap = BitmapFactory.decodeFile(myFile.getAbsolutePath(), options);
        }else{
            if(isCompanion){
                myFile = new File(getFilePath(filePath));
                myBitmap = BitmapFactory.decodeFile(myFile.getAbsolutePath(), options);
            }else{
                InputStream istr;
                try {
                    istr = assetManager.open(filePath);
                    myBitmap = BitmapFactory.decodeStream(istr);
                } catch (IOException e) {
                    // handle exception
                }
            }
        }
        return myBitmap;
        }


//    public BitmapDrawable getBitmapDrawable(String filePath,boolean isAssets){
//        BitmapDrawable myBitmapDrawable;
//    }//end BitmapDrawable

    public Boolean isCompanion(){
        return isCompanion;
    }
	
	
	public Context getContext(){
        return myContext;
    }

    public Activity getActivity(){
        return myActivity;
    }

    public String GetUserID() {
        return this.myActivity.getClass().getName().split("\\.")[2];
    }

    public static void CheckView(View view){
        int myHeight = view.getHeight();
        int myWidth = view.getWidth();

        if(myHeight == 0){
            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myHeight = view.getMeasuredHeight();
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = myHeight;
            view.setLayoutParams(params);
        }

        if(myWidth == 0){
            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myWidth = view.getMeasuredWidth();
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = myWidth;
            view.setLayoutParams(params);
        }

    }


    public static int GetRequiredHeight(AndroidViewComponent component){
        View view = component.getView();
        int reqheight = -1;
        //view.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        reqheight = view.getMeasuredHeight();
        return reqheight;

    }


    public static int GetRequiredWidth(AndroidViewComponent component){
        View view = component.getView();
        int reqwidth = -1;
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return reqwidth;

    }

}

