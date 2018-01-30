package com.example.yashagarwal.rhythm.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.yashagarwal.rhythm.R

class SplashActivity : AppCompatActivity() {

    var permissionString = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(hasPermissions(this@SplashActivity, *permissionString))
        {
            ActivityCompat.requestPermissions(this@SplashActivity, permissionString, 131)
        }
        else{
            Handler().postDelayed({
                var StartAct = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(StartAct)
                this.finish()
            }, 1000)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            131->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED)
                {
                    Handler().postDelayed({
                        var StartAct = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(StartAct)
                        this.finish()
                    }, 1000)
                }
                else{
                    Toast.makeText(this@SplashActivity, "Please Grant All the Permissions to Continue", Toast.LENGTH_SHORT).show()
                    this.finish()
                    return
                }
                return
            }
            else->{
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }

        }

    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean{
        var hasAllPermissions = true
        for(Permission in permissions){
            var res = context.checkCallingOrSelfPermission(Permission)
            if(res != PackageManager.PERMISSION_GRANTED)
            {
                hasAllPermissions = false
            }
        }
        return hasAllPermissions
    }
}
