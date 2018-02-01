package com.example.yashagarwal.rhythm.fragments


import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.example.yashagarwal.rhythm.CurrentSongHelper
import com.example.yashagarwal.rhythm.R
import com.example.yashagarwal.rhythm.Songs
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class SongPlayingFragment : Fragment() {

    var myActivity: Activity? = null
    var mediaplayer: MediaPlayer? = null
    var startTimeText : TextView? = null
    var endTimeText : TextView? = null
    var playPauseImageButton: ImageButton? =null
    var previousImageButton: ImageButton? = null
    var nextImageButton: ImageButton? =null
    var loopImageButton: ImageButton? =null
    var seekbar: SeekBar? =null
    var songArtistView: TextView? =null
    var songTitleView: TextView? =null
    var shuffleImageButton: ImageButton? =null
    var currentSongHelper: CurrentSongHelper?= null
    var currentPosition : Int = 0
    var fetchSongs: ArrayList<Songs>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_song_playing, container, false)

        seekbar = view?.findViewById(R.id.seekBar)
        startTimeText = view?.findViewById(R.id.startTime)
        endTimeText = view?.findViewById(R.id.endTime)
        playPauseImageButton = view?.findViewById(R.id.playPauseButton)
        previousImageButton = view?.findViewById(R.id.previousButton)
        nextImageButton = view?.findViewById(R.id.nextButton)
        loopImageButton = view?.findViewById(R.id.loopButton)
        songArtistView = view?.findViewById(R.id.songArtist)
        songTitleView = view?.findViewById(R.id.songTitle)
        shuffleImageButton = view?.findViewById(R.id.shuffleButton)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myActivity = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        currentSongHelper = CurrentSongHelper()

        currentSongHelper?.isPlaying = true
        currentSongHelper?.isLoop = false
        currentSongHelper?.isShuffle = false
        var path : String ? = null
        var _songTitle : String ? = null
        var _songArtist : String ? = null
        var songId: Long = 0
        try{
            path = arguments.getString("path")
            _songArtist = arguments.getString("songArtist")
            _songTitle = arguments.getString("songTitle")
            songId = arguments.getInt("songId").toLong()
            currentPosition = arguments.getInt("songPosition")
            fetchSongs = arguments.getParcelableArrayList("songData")

            currentSongHelper?.songArtist = _songArtist
            currentSongHelper?.songId = songId
            currentSongHelper?.songPath = path
            currentSongHelper?.songTitle = _songTitle
            currentSongHelper?.currentPosition = currentPosition


        }catch (e : Exception)
        {
            e.printStackTrace()
        }
        mediaplayer = MediaPlayer()
        mediaplayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try{
            mediaplayer?.setDataSource(myActivity, Uri.parse(path))
            mediaplayer?.prepare()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
        mediaplayer?.start()
        if(currentSongHelper?.isPlaying as Boolean){
            playPauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
        }else{
            playPauseImageButton?.setBackgroundResource(R.drawable.play_icon)
        }
    }

    fun clickHandler(){
        shuffleImageButton?.setOnClickListener({})

        nextImageButton?.setOnClickListener({
            currentSongHelper?.isPlaying = true
            if(currentSongHelper?.isShuffle as Boolean){
                playNext("PlayNextLikeNormalShuffle")
            }else{
                playNext("PlayNextNormal")
            }
        })

        previousImageButton?.setOnClickListener({})

        loopImageButton?.setOnClickListener({})

        playPauseImageButton?.setOnClickListener({
            if(mediaplayer?.isPlaying as Boolean){
                mediaplayer?.pause()
                currentSongHelper?.isPlaying = false
                playPauseImageButton?.setBackgroundResource(R.drawable.play_icon)
            }
            else{
                mediaplayer?.start()
                currentSongHelper?.isPlaying = true
                playPauseImageButton?.setBackgroundResource(R.drawable.pause_icon)
            }
        })
    }

    fun playNext(check : String){
        if(check.equals("PlayNextNormal",true)){
            currentPosition++
        }else if(check.equals("PlayNextLikeNormalShuffle",true)){
            var randomObject = Random()
            var randomPosition = randomObject.nextInt(fetchSongs?.size?.plus(1) as Int)
            currentPosition = randomPosition

        }
        if(currentPosition == fetchSongs?.size){
            currentPosition = 0
        }
        currentSongHelper?.isLoop = false
        var nextSong = fetchSongs?.get(currentPosition)
        currentSongHelper?.songArtist = nextSong?.artist
        currentSongHelper?.songId = nextSong?.songID as Long
        currentSongHelper?.songPath = nextSong?.songData
        currentSongHelper?.songTitle = nextSong?.songTitle
        currentSongHelper?.currentPosition = currentPosition
        mediaplayer?.reset()
        try{

            mediaplayer?.setDataSource(myActivity,Uri.parse(currentSongHelper?.songPath))
            mediaplayer?.prepare()
            mediaplayer?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun playPrevious(){
        currentPosition--
        if(currentPosition == -1){

        }
    }
}// Required empty public constructor
