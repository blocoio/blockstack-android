package org.blockstack.android.sdk.ui

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.lifecycle.coroutineScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_connect_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.blockstack.android.sdk.BlockstackConnect
import org.blockstack.android.sdk.R

class ConnectBottomSheetDialogFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance(): ConnectBottomSheetDialogFragment =
                ConnectBottomSheetDialogFragment().apply {
                    arguments = Bundle()
                }
    }

    private val REQUEST_HELP = 1
    private lateinit var blockstackConnect: BlockstackConnect
    private var resourceTheme: Int = R.style.Theme_Blockstack

    fun setBlockstackConnect(blockstackConnect: BlockstackConnect): ConnectBottomSheetDialogFragment {
        this.blockstackConnect = blockstackConnect
        return this
    }

    fun setTheme(@StyleRes resource: Int): ConnectBottomSheetDialogFragment {
        resourceTheme = resource
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.theme?.applyStyle(resourceTheme, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(R.layout.fragment_connect_dialog)

        activity?.apply {
            dialog.connect_dialog_title.text = getString(R.string.connect_dialog_title, getString(applicationInfo.labelRes))
            dialog.connect_dialog_app_icon.setImageResource(applicationInfo.icon)
            dialog.connect_dialog_get_started.setOnClickListener {
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    blockstackConnect.redirectUserToSignIn(this@apply, sendToSignIn = false)
                    this@ConnectBottomSheetDialogFragment.dismiss()
                }
            }
            dialog.connect_dialog_restore.setOnClickListener {
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    blockstackConnect.redirectUserToSignIn(this@apply, sendToSignIn = true)
                    this@ConnectBottomSheetDialogFragment.dismiss()
                }
            }
            dialog.connect_dialog_help.setOnClickListener {
                this@ConnectBottomSheetDialogFragment.startActivityForResult(Intent(this, ConnectHelpActivity::class.java), REQUEST_HELP)
                this@ConnectBottomSheetDialogFragment.dismiss()
            }
        }

        return dialog
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_HELP && resultCode == RESULT_OK) {
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                blockstackConnect.redirectUserToSignIn(requireActivity())
                this@ConnectBottomSheetDialogFragment.dismiss()
            }
        }
    }
}