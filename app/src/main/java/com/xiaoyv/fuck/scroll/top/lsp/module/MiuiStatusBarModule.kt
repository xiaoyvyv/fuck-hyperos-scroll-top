package com.xiaoyv.fuck.scroll.top.lsp.module

import android.util.Log
import androidx.annotation.Keep
import com.xiaoyv.fuck.scroll.top.lsp.Constant
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface

@Keep
class MiuiStatusBarModule : XposedModule() {

    override fun onPackageReady(param: XposedModuleInterface.PackageReadyParam) {
        val cls = runCatching { Class.forName("miui.hardware.input.MiuiInputManager", false, param.classLoader) }.getOrNull()
        if (cls != null) {
            val method = cls.getDeclaredMethod("scrollToTop")
            if (method != null) hook(method).intercept {
                val preferences = getRemotePreferences(Constant.SP_GROUP)
                val disableScrollTop = preferences.getBoolean(Constant.KEY_DISABLE_CLICK_SCROLL_TOP, false)
                if (disableScrollTop) {
                    log(Log.INFO, "MiuiStatusBarModule", "状态栏点击回顶拦截")
                } else {
                    log(Log.INFO, "MiuiStatusBarModule", "状态栏点击回顶不拦截")
                    it.proceed()
                }
            }
        }
    }
}