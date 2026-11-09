# KeepSaver 1.0.0

Vector module for the Pixel 10a system framework.

Battery Saver turns off when the phone is plugged in. Sticky mode is supposed to turn it back on at unplug. The framework setting `low_power_sticky_auto_disable_enabled` defaults to on, with a threshold of 90. A full charge clears sticky mode, so Battery Saver stays off.

KeepSaver hooks `BatterySaverStateMachine.getGlobalSetting(String, int)` and returns 0 only for `low_power_sticky_auto_disable_enabled`. The setting itself is not written. Turning Battery Saver off manually still works. Plugging in still turns it off until the cable comes out.

## Install

1. Install `KeepSaver-1.0.0.apk`.
2. Enable KeepSaver in Vector.
3. Scope: System Framework, stored as `system`.
4. Reboot.

```bash
/data/adb/lspd/cli modules enable com.keepsaver.xposed
/data/adb/lspd/cli scope set com.keepsaver.xposed system/0
```

## Recovery

Disable KeepSaver in Vector and reboot. Safe mode does not load Vector modules. `pm uninstall com.keepsaver.xposed` then reboot also removes it.
