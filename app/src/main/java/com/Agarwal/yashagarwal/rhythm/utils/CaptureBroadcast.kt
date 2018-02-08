package com.Agarwal.yashagarwal.rhythm.utils

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.telephony.TelephonyManager
import com.Agarwal.yashagarwal.rhythm.activities.MainActivity
import com.Agarwal.yashagarwal.rhythm.activities.MainActivity.Staticated.notificationManager
import com.Agarwal.yashagarwal.rhythm.fragments.SongPlayingFragment


/**
 * Created by ADMIN on 6/19/2017.
 */
internal class CaptureBroadcast : BroadcastReceiver() {

    object Statified {
        var incomingFlag = false
        // var incoming_number: String? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {

            Statified.incomingFlag = false
            try {
                MainActivity.Staticated.notificationManager?.cancel(1978)

                if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                    (SongPlayingFragment.Statified.mediaPlayer as MediaPlayer).pause()

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            val tm: TelephonyManager = context.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager

            when (tm.callState) {

                TelephonyManager.CALL_STATE_RINGING -> {
                    Statified.incomingFlag = true
                    //  CaptureBroadcast.Statified.incoming_number = intent.getStringExtra("incoming_number")
                    try {
                        if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                            (SongPlayingFragment.Statified.mediaPlayer as MediaPlayer).pause()

                            notificationManager?.cancel(1978)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                }
            }

        }

    }


}