package com.example.testapplication

import android.widget.MediaController.MediaPlayerControl
import com.google.android.exoplayer2.ExoPlayer


class PlayerControl(private val exoPlayer: ExoPlayer) : MediaPlayerControl {
    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getAudioSessionId(): Int {
        throw UnsupportedOperationException()
    }

    override fun getBufferPercentage(): Int {
        return exoPlayer.bufferedPercentage
    }

    override fun getCurrentPosition(): Int {
        return exoPlayer.currentPosition
            .toInt()
    }

    override fun getDuration(): Int {
        return exoPlayer.duration.toInt()
    }

    override fun isPlaying(): Boolean {
        return exoPlayer.playWhenReady
    }

    override fun start() {
        exoPlayer.playWhenReady = true
    }

    override fun pause() {
        exoPlayer.playWhenReady = false
    }

    override fun seekTo(timeMillis: Int) {
        val seekPosition = Math.min(
            Math.max(0, timeMillis),
            duration
        ).toLong()
        exoPlayer.seekTo(seekPosition)
    }
}