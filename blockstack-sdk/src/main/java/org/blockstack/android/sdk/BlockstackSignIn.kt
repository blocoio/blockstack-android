package org.blockstack.android.sdk

import android.content.Context
import org.blockstack.android.sdk.model.BlockstackConfig

data class AppDetails(val name: String, val icon: String)

class BlockstackSignIn(private val sessionStore: ISessionStore, private val appConfig: BlockstackConfig, private val appDetails: AppDetails? = null)
    : BaseBlockstackAuth(sessionStore, appConfig, appDetails) {

    override suspend fun redirectToSignInWithAuthRequest(context: Context, authRequest: String, blockstackIDHost: String?, sendToSignIn: Boolean) {
        val hostUrl = blockstackIDHost ?: LEGACY_BLOCKSTACK_ID_HOST
        super.redirectToSignInWithAuthRequest(context, authRequest, hostUrl, sendToSignIn)
    }

}
