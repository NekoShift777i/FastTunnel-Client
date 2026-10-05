package online.fasttunnelvpn.client

import android.app.Activity
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import online.fasttunnelvpn.client.ui.theme.FastTunnelClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FastTunnelClientTheme {
                VpnPermissionScreen()
            }
        }
    }
}

@Composable
fun VpnPermissionScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val vpnPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Разрешение на создание VPN получено.
            // Позже здесь запустим FastTunnelVpnService.
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                val permissionIntent = VpnService.prepare(context)

                if (permissionIntent != null) {
                    vpnPermissionLauncher.launch(permissionIntent)
                } else {
                    // Разрешение уже было выдано ранее.
                }
            }
        ) {
            Text("Подключиться")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VpnPermissionScreenPreview() {
    FastTunnelClientTheme {
        VpnPermissionScreen()
    }
}