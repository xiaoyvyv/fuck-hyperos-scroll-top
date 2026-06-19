package com.xiaoyv.fuck.scroll.top

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.xiaoyv.fuck.scroll.top.ui.theme.FuckScrollTopTheme
import com.xiaoyv.fuck.scroll.top.ui.theme.icons.BootstrapGithub

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollState = rememberScrollState()
    val service by viewModel.service.collectAsState()
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(text = stringResource(R.string.app_title))
                        Text(
                            text = buildAnnotatedString {
                                append("系统行为增强模块")
                                if (service == null) withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
                                    append("（模块未生效）")
                                } else withStyle(SpanStyle(color = Color.Green.copy(green = 0.8f))) {
                                    append("（模块已生效，${service?.frameworkName}-${service?.frameworkVersion}）")
                                }
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    val context = LocalContext.current
                    IconButton(onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            setData("".toUri())
                        })
                    }) {
                        Icon(
                            imageVector = BootstrapGithub,
                            contentDescription = "Github"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.toggleable(
                            enabled = service != null,
                            value = state.disableScrollTop,
                            indication = null,
                            interactionSource = null,
                            onValueChange = { viewModel.updateDisableScrollTop(it) }
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "禁用回顶机制",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Switch(
                            enabled = service != null,
                            checked = state.disableScrollTop,
                            onCheckedChange = {
                                viewModel.updateDisableScrollTop(it)
                            }
                        )
                    }
                }
            }

            // ===== 概述卡片 =====
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "模块说明",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "调整 HyperOS3 状态栏点击逻辑，禁用状态栏点击回顶机制。",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // ===== 操作步骤 =====
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "使用步骤",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StepItem(1, "在 LSPosed 模块中打开，并勾选系统界面 com.android.systemui")
                    StepItem(2, "将上面的禁用开关打开，以及确保模块已生效并重启系统")
                    StepItem(3, "打开抖音，微信等应用，在任意列表页面点击状态栏验证是否禁用回顶")
                }
            }

            // ===== 注意事项 =====
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "注意事项",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "不同 HyperOS3 版本行为可能不同，启用模块后需要重启系统才能完全生效。",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun StepItem(index: Int, text: String) {
    Row(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = "$index.",
            modifier = Modifier.width(24.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FuckScrollTopTheme {
        MainScreen(modifier = Modifier.fillMaxSize(), MainViewModel())
    }
}