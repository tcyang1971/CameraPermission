package tw.edu.pu.csim.tcyang.camerapermission

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1

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
        
        var it: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(it, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        if (p0!!.isPermanentlyDenied) {
            Toast.makeText(this, "您永久拒絕拍照權限", Toast.LENGTH_SHORT).show()
            var it: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            var uri: Uri = Uri.fromParts("package", getPackageName(), null)
            it.setData(uri)
            startActivity(it)
        }
        else{
            Toast.makeText(this, "您此次拒絕拍照權限", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val img: ImageView = findViewById(R.id.img)
            img.setImageBitmap(imageBitmap)
        }
    }

}