package com.preston.imadexam // Your package name

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.io.path.name

class SecondActivity : AppCompatActivity() {

    private lateinit var tvSecondActivityTitle: TextView
    private lateinit var btnDisplaySongs: Button
    private lateinit var btnCalculateAverageRating: Button
    private lateinit var btnReturnToMain: Button
    private lateinit var tvSongListDisplay: TextView

    private var currentPlaylist: Playlist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second) // Uses activity_second.xml

        tvSecondActivityTitle = findViewById(R.id.tvSecondAcivitytitle)
        btnDisplaySongs = findViewById(R.id.btnDisplaySongs)
        btnCalculateAverageRating = findViewById(R.id.btnCalculateAverageRating)
        btnReturnToMain = findViewById(R.id.btnReturnToMain)
        tvSongListDisplay = findViewById(R.id.tvSongListDisplay)

        // Retrieve the playlist ID passed from MainActivity
        val playlistId = intent.getStringExtra("PLAYLIST_ID")

        if (playlistId != null) {
            currentPlaylist = PlaylistRepository.getPlaylistById(playlistId) as Playlist? // Use the repository
            if (currentPlaylist != null) {
                tvSecondActivityTitle.text = "Details for: ${currentPlaylist!!.name}"
            } else {
                tvSecondActivityTitle.text = "Playlist Not Found"
                Toast.makeText(this, "Could not load playlist.", Toast.LENGTH_LONG).show()
                // Disable buttons if playlist not found
                btnDisplaySongs.isEnabled = false
                btnCalculateAverageRating.isEnabled = false
            }
        } else {
            tvSecondActivityTitle.text = "No Playlist ID Provided"
            Toast.makeText(this, "No playlist ID received.", Toast.LENGTH_LONG).show()
            btnDisplaySongs.isEnabled = false
            btnCalculateAverageRating.isEnabled = false
        }

        btnDisplaySongs.setOnClickListener {
            displaySongs()
        }

        btnCalculateAverageRating.setOnClickListener {
            calculateAndDisplayAverageRating()
        }

        btnReturnToMain.setOnClickListener {
            finish()
        }
    }

    class PlaylistRepository {
        companion object

    }

    private fun displaySongs() {
        TODO("Not yet implemented")
    }

    private fun calculateAndDisplayAverageRating() {
        TODO("Not yet implemented")
    }
}

class Playlist {

    val name: String
        get() {
            TODO()
        }
}

private fun SecondActivity.PlaylistRepository.Companion.getPlaylistById(playlistId: String): Any {
    TODO("Not yet implemented")
}
