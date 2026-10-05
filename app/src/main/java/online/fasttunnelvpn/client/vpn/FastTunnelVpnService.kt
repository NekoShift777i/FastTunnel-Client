package online.fasttunnelvpn.client.vpn

import android.content.Intent
import android.net.VpnService
import android.os.IBinder

class FastTunnelVpnService : VpnService() {

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }
}