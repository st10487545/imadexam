package com.preston.imadexam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private val Any.size: String
    get() {
        TODO("Not yet implemented")
    }

class Song(val title: String, val artist: String, val rating: Int, val comments: String)

class PlaylistRepository {
    object defaultPlaylist {

        val songs: Any
            get() {
                TODO()
            }
        val name: String = ""
        val id: Any = TODO()
    }

    companion object {
        fun addSongToPlaylist(id: Any, newSong: Song): Boolean {
                TODO("Not yet implemented")
        }
    }

}

class MainActivity : AppCompatActivity() {

    class MainActivity() : AppCompatActivity(), Parcelable {

        private lateinit var btnAddSongToPlaylist: Button
        private lateinit var btnViewPlaylistDetails: Button
        private lateinit var btnExitApp: Button
        private lateinit var tvMainStatus: TextView

        constructor(parcel: Parcel) : this() {

        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Button 1: Add Song to Playlist
            btnAddSongToPlaylist.setOnClickListener {
                showAddSongDialog()
            }

            // Button 2: View Playlist Details (Navigate to Second Screen)
            btnViewPlaylistDetails.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)

                PlaylistRepository.defaultPlaylist.let {

                } ?: run {
                    Toast.makeText(
                        this,
                        "Default playlist not available to view.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Button 3: Exit App
            btnExitApp.setOnClickListener {
                finishAffinity() // Closes all activities in this task and finishes the app
            }

            updateMainStatusDisplay() // Initial status update
        }

        override fun onResume() {
            super.onResume()
            // Refresh status when returning to this activity, in case songs were added/changed
            updateMainStatusDisplay()
        }

        @SuppressLint("MissingInflatedId")
        private fun showAddSongDialog() {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_song, null)
            val edtSongTitle = dialogView.findViewById<EditText>(R.id.edtSongTitleDialog)
            val edtArtistName = dialogView.findViewById<EditText>(R.id.edtArtistNameDialog)
            val ratingBarSong = dialogView.findViewById<RatingBar>(R.id.ratingBarSongDialog)
            val edtComments = dialogView.findViewById<EditText>(R.id.edtCommentsDialog)

            AlertDialog.Builder(this)
                .setTitle("Add New Song")
                .setView(dialogView)
                .setPositiveButton("Add") { dialog, _ ->
                    val title = edtSongTitle.text.toString().trim()
                    val artist = edtArtistName.text.toString().trim()
                    val rating = ratingBarSong.rating.toInt()
                    val comments = edtComments.text.toString().trim()

                    if (title.isNotEmpty() && artist.isNotEmpty() && rating > 0) {
                        val newSong = Song(
                            title = title,
                            artist = artist,
                            rating = rating,
                            comments = comments
                        )

                        PlaylistRepository.defaultPlaylist?.let { playlist ->
                            if (PlaylistRepository.addSongToPlaylist(playlist.id, newSong)) {
                                Toast.makeText(
                                    this,
                                    "'$title' added to ${playlist.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                updateMainStatusDisplay()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error: Could not add song to playlist.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } ?: run {
                            Toast.makeText(
                                this,
                                "Error: Default playlist not found!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Please fill in title, artist, and provide a rating.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        @SuppressLint("SetTextI18n")
        private fun updateMainStatusDisplay() = PlaylistRepository.defaultPlaylist?.let {
            "Playlist '${it.name}' has ${it.songs.size} song(s).".also { tvMainStatus.text = it }
        } ?: run {
            tvMainStatus.text = "No default playlist loaded."
        }


        override fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MainActivity> {
            override fun createFromParcel(parcel: Parcel): MainActivity {
                return MainActivity(parcel)
            }

            override fun newArray(size: Int): Array<MainActivity?> {
                return arrayOfNulls(size)
            }
        }
    }//end MainActivity class
}