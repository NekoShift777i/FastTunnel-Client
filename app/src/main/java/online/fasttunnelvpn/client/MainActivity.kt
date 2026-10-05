package online.fasttunnelvpn.client

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

import online.fasttunnelvpn.client.ui.theme.FastTunnelClientTheme
import online.fasttunnelvpn.client.vpn.FastTunnelVpnService

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

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        Log.i("FastTunnel", "Notification permission granted: $granted")
    }

    fun startVpnService() {
        Log.i("FastTunnel", "Starting VPN service")

        val serviceIntent = Intent(
            context,
            FastTunnelVpnService::class.java
        )

       context.startForegroundService(serviceIntent)

    }

    val vpnPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            startVpnService()
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
                    startVpnService()
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