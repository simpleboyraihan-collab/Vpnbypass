package com.example.vpnblocker;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class VpnBypass implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // যে অ্যাপের VPN ডিটেকশন বন্ধ করতে চান তার প্যাকেজ নেম এখানে বসাবেন
        if (lpparam.packageName.equals("com.target.app")) {
            
            XposedHelpers.findAndHookMethod(
                "android.net.NetworkCapabilities",
                lpparam.classLoader,
                "hasTransport",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        int transportType = (int) param.args[0];
                        // 4 হলো TRANSPORT_VPN-এর কোড
                        if (transportType == 4) {
                            param.setResult(false); 
                        }
                    }
                }
            );
        }
    }
}
