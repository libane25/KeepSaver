package com.keepsaver.xposed;

import android.os.Build;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class KeepSaverLegacy implements IXposedHookLoadPackage {

    static final String TAG = "KeepSaver";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (lpparam == null) {
            return;
        }
        String packageName = lpparam.packageName;
        if (!"android".equals(packageName) && !"system".equals(packageName)) {
            if ("com.android.settings".equals(packageName)
                    || "com.google.android.settings.intelligence".equals(packageName)) {
                log("Skipped " + packageName
                        + ". Remove Settings from the Vector scope. The only target is System Framework.");
            }
            return;
        }
        try {
            log("KeepSaver " + BuildConfig.VERSION_NAME + " loaded in system framework");
            log("Android version: " + Build.VERSION.RELEASE);
            log("SDK version: " + Build.VERSION.SDK_INT);
            log("Build fingerprint: " + Build.FINGERPRINT);
            log("Device codename: " + Build.DEVICE);
            log("Model: " + Build.MODEL);
            StickyDisableInstaller.install(lpparam.classLoader, KeepSaverLegacy::log);
        } catch (Throwable error) {
            log("Hook installation exception: " + error.getClass().getName() + ": " + error.getMessage());
            log("Installed hooks: none");
        }
    }

    private static void log(String message) {
        Log.i(TAG, message);
        try {
            XposedBridge.log("[" + TAG + "] " + message);
        } catch (Throwable ignored) {
            // Logcat already has the line.
        }
    }
}
