package tw.edu.pu.csim.tcyang.camerapermission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity(), PermissionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun TakePicture(view: View){
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(this)
            .check()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        makeText(this, "您已允許拍照權限", LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        if (p0!!.isPermanentlyDenied) {
            Toast.makeText(this, "您永久拒絕拍照權限", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "您此次拒絕拍照權限", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }
}