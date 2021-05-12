package tutorialwing.com.zxinglibrary

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

import org.json.JSONException
import org.json.JSONObject

class QRCodeFragment : Fragment() {

    internal var txtName: TextView? = null

    internal var txtSiteName: TextView? = null

    internal var btnScan: Button? = null

    internal var qrScanIntegrator: IntentIntegrator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qrcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtName = view.findViewById(R.id.name)
        txtSiteName = view.findViewById(R.id.site_name)

        btnScan = view.findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator.forFragment(this)
        qrScanIntegrator?.setOrientationLocked(false)

        qrScanIntegrator?.setPrompt(getString(R.string.scan_a_code))
        qrScanIntegrator?.setCameraId(0)  // Use a specific camera of the device
        qrScanIntegrator?.setBeepEnabled(false)
        qrScanIntegrator?.setBarcodeImageEnabled(true)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun performAction() {
        qrScanIntegrator?.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, R.string.result_not_found, Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
                    txtName?.text = obj.getString("name")
                    txtSiteName?.text = obj.getString("site_name")

                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
