package com.xiaoyv.fuck.scroll.top

import androidx.compose.runtime.Immutable
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.xiaoyv.fuck.scroll.top.lsp.Constant
import io.github.libxposed.service.XposedService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Immutable
data class MainState(
    val disableScrollTop: Boolean
)

class MainViewModel : ViewModel() {
    private var _serviceFlow: MutableStateFlow<XposedService?> = MutableStateFlow(null)
    val service = _serviceFlow.asStateFlow()

    private var _state: MutableStateFlow<MainState> = MutableStateFlow(MainState(disableScrollTop = true))
    val state = _state.asStateFlow()

    fun updateDisableScrollTop(value: Boolean) {
        _state.update { it.copy(disableScrollTop = value) }

        // 保存设置
        val service = service.value ?: return
        service.getRemotePreferences(Constant.SP_GROUP).edit {
            putBoolean(Constant.KEY_DISABLE_CLICK_SCROLL_TOP, value)
        }
    }

    fun updateService(xposedService: XposedService?) {
        _serviceFlow.update { xposedService }

        // 读取设置
        val service = service.value ?: return
        val disableScrollTop = service.getRemotePreferences(Constant.SP_GROUP).getBoolean(Constant.KEY_DISABLE_CLICK_SCROLL_TOP, false)
        _state.update { it.copy(disableScrollTop = disableScrollTop) }
    }
}