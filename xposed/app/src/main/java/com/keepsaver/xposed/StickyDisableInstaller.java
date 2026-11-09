package com.keepsaver.xposed;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

final class StickyDisableInstaller {

    private static final String MACHINE =
            "com.android.server.power.batterysaver.BatterySaverStateMachine";

    private StickyDisableInstaller() {
    }

    static void install(ClassLoader loader, Logger logger) {
        Method method;
        try {
            Class<?> machine = Class.forName(MACHINE, false, loader);
            method = machine.getDeclaredMethod("getGlobalSetting", String.class, int.class);
        } catch (ClassNotFoundException notFound) {
            logger.log("Skipped hook: BatterySaverStateMachine is absent. Installed hooks: none");
            return;
        } catch (NoSuchMethodException notFound) {
            logger.log("Skipped hook: getGlobalSetting(String, int) is absent. Installed hooks: none");
            return;
        }
        if (method.getReturnType() != int.class) {
            logger.log("Skipped hook: getGlobalSetting returns "
                    + method.getReturnType().getName() + ". Installed hooks: none");
            return;
        }
        XposedBridge.hookMethod(method, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                    if (param.args != null
                            && param.args.length > 0
                            && StickyDisableGate.shouldReportDisabled(param.args[0])) {
                        param.setResult(0);
                    }
                } catch (Throwable ignored) {
                    // Leave the framework's own return value in place.
                }
            }
        });
        logger.log("Installed hook: BatterySaverStateMachine.getGlobalSetting. "
                + "low_power_sticky_auto_disable_enabled returns 0, so a full charge "
                + "does not clear sticky Battery Saver. Other settings are unchanged.");
    }

    interface Logger {
        void log(String message);
    }
}
