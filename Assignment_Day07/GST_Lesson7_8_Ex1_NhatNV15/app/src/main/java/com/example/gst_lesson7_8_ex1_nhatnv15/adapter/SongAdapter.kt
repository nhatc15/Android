package gst.trainingcourse.recycleviewdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gst_lesson7_8_ex1_nhatnv15.R
import com.example.gst_lesson7_8_ex1_nhatnv15.ui.MainActivity
import com.example.gst_lesson7_8_ex1_nhatnv15.ui.PlayingFragment
import com.example.gst_lesson7_8_ex1_nhatnv15.databinding.ItemSongBinding
import com.example.gst_lesson7_8_ex1_nhatnv15.model.Song

class SongAdapter(
    private val songs: List<Song>,
    private val onClickListener: (Song)->Unit
): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        with(holder.binding){
            tvSongName.text = songs[position].name
            tvArtistName.text = songs[position].artist
            itemSong.setOnClickListener{
                onClickListener(songs[position])
            }
        }
    }
}
