package org.blockstack.android.sdk

import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import org.blockstack.android.sdk.model.BlockstackConfig
import org.blockstack.android.sdk.ui.ConnectBottomSheetDialogFragment

class BlockstackConnect(private val sessionStore: ISessionStore, private val appConfig: BlockstackConfig, private val appDetails: AppDetails? = null)
    : BaseBlockstackAuth(sessionStore, appConfig, appDetails) {

    fun showDialog(context: AppCompatActivity, @StyleRes theme: Int = R.style.Theme_Blockstack) {
        ConnectBottomSheetDialogFragment.newInstance()
                .setBlockstackConnect(this)
                .setTheme(theme)
                .show(context.supportFragmentManager, "connectDialog")
    }

}