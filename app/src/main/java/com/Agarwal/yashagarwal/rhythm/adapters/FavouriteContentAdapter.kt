package com.Agarwal.yashagarwal.rhythm.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.media.MediaPlayer
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.support.v4.app.FragmentActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.Agarwal.yashagarwal.rhythm.R
import com.Agarwal.yashagarwal.rhythm.models.Songs
import com.Agarwal.yashagarwal.rhythm.fragments.SongPlayingFragment


/**
 * Created by ADMIN on 6/21/2017.
 */
class FavouriteContentAdapter(ctx: Context, _songs: ArrayList<Songs>) : RecyclerView.Adapter<FavouriteContentAdapter.FavContentViewHolder>() {
    var _getSongs: ArrayList<Songs>? = null
    var mContext: Context? = null
    var mediaPlayer: MediaPlayer? = null


    init {
        this._getSongs = _songs
        this.mContext = ctx
        this.mediaPlayer = SongPlayingFragment.Statified.mediaPlayer
    }

    override fun onBindViewHolder(holder: FavContentViewHolder?, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val mSongs = _getSongs?.get(position)
        if (mSongs?.artist.equals("<unknown>", ignoreCase = true)) {
            holder?.text_Artist?.text = "unknown"
        } else {
            holder?.text_Artist?.text = mSongs?.artist
        }
        holder?.text_Title?.text = mSongs?.songTitle
        holder?.contentHolder?.setOnClickListener(View.OnClickListener {
            try {
                if (mediaPlayer?.isPlaying as Boolean) {
                    mediaPlayer?.stop()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val args = Bundle()
            args.putString("path", mSongs?.songData)
            args.putString("songTitle", mSongs?.songTitle)
            args.putString("songArtist", mSongs?.artist)
            args.putInt("songPosition", position)
            args.putString("from", "favorite")
            args.putInt("SongId", mSongs?.songID?.toInt() as Int)
            args.putParcelableArrayList("songsData", _getSongs)
            val songPlayingFragment = SongPlayingFragment()
            songPlayingFragment.arguments = args
            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment)
                    .addToBackStack("FavoriteToBackStack")
                    .commit()
        })

    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return _getSongs?.size as Int
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavContentViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.favorite_content_custom_row, parent, false)

        return FavContentViewHolder(itemView)

    }

    inner class FavContentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var text_Artist: TextView
        var text_Title: TextView
        var contentHolder: RelativeLayout

        init {
            text_Artist = view.findViewById(R.id.trackArtist) as TextView
            text_Title = view.findViewById(R.id.trackTitle) as TextView
            contentHolder = view.findViewById(R.id.contentRow) as RelativeLayout
        }
    }

}