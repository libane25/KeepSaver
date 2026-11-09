package com.keepsaver.xposed;

/**
 * The framework reads low_power_sticky_auto_disable_enabled with a default of 1.
 * That cutoff clears sticky Battery Saver once the battery reaches 90 percent,
 * which a full charge always does.
 */
final class StickyDisableGate {

    static final String AUTO_DISABLE_KEY = "low_power_sticky_auto_disable_enabled";

    private StickyDisableGate() {
    }

    static boolean shouldReportDisabled(Object key) {
        return AUTO_DISABLE_KEY.equals(key);
    }
}
