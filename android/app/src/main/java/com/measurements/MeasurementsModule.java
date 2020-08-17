package com.measurements;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class MeasurementsModule extends ReactContextBaseJavaModule {
    private ReactContext reactContext;

    public MeasurementsModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "Measurements";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants =  new HashMap<>();

        constants.put("SOFT_MENU_BAR_ENABLED", hasPermanentMenuKey());

        return constants;
    }

    @ReactMethod
    public boolean hasPermanentMenuKey() {
        final Context ctx = getReactApplicationContext();
        int id = ctx.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return !(id > 0 && ctx.getResources().getBoolean(id));
    }

    private boolean hasImmersive() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        }
        Display d = ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth > displayWidth) || (realHeight > displayHeight);
    }

    @ReactMethod
    public void hasSoftKeys(final Promise promise) {
        promise.resolve(hasImmersive());
    }

    @ReactMethod
    public void hasSoftKeys2(final Promise promise) {
        boolean hasMenuKey = ViewConfiguration.get(reactContext).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        promise.resolve(!hasMenuKey && !hasBackKey);
    }

    @ReactMethod
    public void hasSoftKeys3(final Promise promise) {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

        promise.resolve(!(hasBackKey && hasHomeKey));
    }

    @ReactMethod
    public void hasMenuKey(final Promise promise) {
        promise.resolve(ViewConfiguration.get(reactContext).hasPermanentMenuKey());
    }

    @ReactMethod
    public void hasBackKey(final Promise promise) {
        promise.resolve(KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK));
    }

    @ReactMethod
    public void hasHomeKey(final Promise promise) {
        promise.resolve(KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME));
    }
}
