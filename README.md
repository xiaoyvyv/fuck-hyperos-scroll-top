# 🚀 FuckScrollTop

> ⚙️ A System UI Enhancement Module for HyperOS / Android (LSPosed)

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Framework](https://img.shields.io/badge/Framework-LSPosed-blue.svg)
![Language](https://img.shields.io/badge/Kotlin-Compose-purple.svg)
![Status](https://img.shields.io/badge/Status-Experimental-red.svg)

---

## 📌 项目简介

**FuckScrollTop** 是一个基于 **LSPosed 框架** 的系统行为修改模块，用于调整 HyperOS3 SystemUI 的“状态栏点击回顶”逻辑。

核心目标：

> ❌ 禁用或可控拦截 `Scroll-to-top`（状态栏点击回到列表顶部）行为

---

## 🎯 功能特性

- 🧠 Hook MIUI System Input 层 scroll 行为
- 🚫 可禁用 Scroll-to-Top 自动回顶
- ⚡  拦截开关实时生效（无需重启系统）
- 🧩 可观测 hook 命中日志输出

---

## 🧪 核心能力说明

### ✔️ MIUI 输入系统级 Hook

本模块并不直接 Hook UI 层事件，而是作用于 MIUI 内部输入管理组件：

- 目标类：
  ```
  miui.hardware.input.MiuiInputManager
  ```

- 目标方法：
  ```
  scrollToTop()
  ```

该方法通常在系统检测到状态栏点击且存在可滚动容器时触发。

---

## ⚙️ 使用方法

### ① 安装模块
- 安装 APK
- 在 LSPosed 中启用

---

### ② 作用域配置

勾选：

```
com.android.systemui
```

或 MIUI 相关 SystemUI 进程

---

### ③ 启用开关

在应用中打开：

- 「禁用回顶机制」

对应配置：

```
KEY_DISABLE_CLICK_SCROLL_TOP = true
```

---

### ④ 重启系统

必须重启设备，否则 hook 不完全生效

---

### ⑤ 验证效果

在以下应用中测试：

- 抖音 / TikTok 类列表页面
- 微信聊天列表
- 任意可滚动 RecyclerView 页面

点击状态栏应 **不再触发回顶**

---

## ⚠️ 注意事项

- `MiuiInputManager` 属于 MIUI 内部实现类，不同版本可能存在差异
- 部分 HyperOS 版本方法名可能混淆或被内联
- Hook 失败通常由 classloader 变化导致
- 建议在 Android 15+ / HyperOS3 环境验证

---

## 🚧 开发状态

> ⚠️ Experimental / Reverse Engineering Module

当前实现主要用于验证：

- MIUI 输入系统 hook 可行性
- scrollToTop 行为拦截稳定性
- LSPosed 跨版本兼容能力

---

## 📎 License

MIT License

---

## 💡 免责声明

本项目仅用于：

- Android 系统行为研究
- UI / 输入系统机制分析
- 学术与逆向工程实验

不涉及数据采集或恶意行为。
