package com.measurements;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class MeasurementsModule extends ReactContextBaseJavaModule {
    private ReactContext reactContext;

    public MeasurementsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext = reactContext;
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
}
