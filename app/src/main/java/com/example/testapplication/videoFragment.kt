package com.example.testapplication

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class videoFragment : Fragment() {

//    val packageName = BuildConfig.APPLICATION_ID
    val packageName = activity?.packageName
    private lateinit var simpleExoPlayer: SimpleExoPlayer
//    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var videoView: PlayerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //------------this is me trying with mediasource----

        Log.d("TAG", "DK### Context: $context")
//
//        context?.let {
//            simpleExoPlayer = SimpleExoPlayer.Builder(it).build()
//            mediaDataSourceFactory = DefaultDataSourceFactory(it, Util.getUserAgent(it, "mediaPlayerSample"))
//        }
//
//        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
//            Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"))
//
//
//
//        Log.i("log", "media source is: " + mediaSource)
//
//        videoView = view.findViewById<PlayerView>(R.id.player_view)
//
//        simpleExoPlayer.prepare(mediaSource, false, false)
//        simpleExoPlayer.playWhenReady = true
//        videoView.setShutterBackgroundColor(Color.TRANSPARENT)
//        videoView.player = simpleExoPlayer
//        videoView.requestFocus()





//        ------------this is me trying with mediaitem----

        videoView = view.findViewById<PlayerView>(R.id.player_view)

        val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://com.example.testapplication/raw/leaves"))
//        val mediaItem = MediaItem.Builder().setUri(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")).build()

        Log.i("log", "media source is: " + mediaItem)
        context?.let {
            simpleExoPlayer = SimpleExoPlayer.Builder(it).build()
            videoView.player = simpleExoPlayer
            simpleExoPlayer.setMediaItem(mediaItem, false)
            simpleExoPlayer.prepare()
            simpleExoPlayer.playWhenReady = true
            simpleExoPlayer.repeatMode = SimpleExoPlayer.REPEAT_MODE_ONE
        }
    }

    override fun onStart() {
        super.onStart()
        videoView.onResume()
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.release()
    }

}
