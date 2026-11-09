package com.keepsaver.xposed;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StickyDisableGateTest {

    @Test
    public void onlyTheFullChargeCutoffIsForcedOff() {
        assertTrue(StickyDisableGate.shouldReportDisabled(
                "low_power_sticky_auto_disable_enabled"));
        assertFalse(StickyDisableGate.shouldReportDisabled("low_power"));
        assertFalse(StickyDisableGate.shouldReportDisabled("low_power_sticky"));
        assertFalse(StickyDisableGate.shouldReportDisabled(
                "low_power_sticky_auto_disable_level"));
        assertFalse(StickyDisableGate.shouldReportDisabled(null));
        assertFalse(StickyDisableGate.shouldReportDisabled(1));
    }
}
