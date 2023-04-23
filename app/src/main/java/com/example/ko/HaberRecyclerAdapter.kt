package com.example.ko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class HaberRecyclerAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<HaberRecyclerAdapter.PostGorunumVH>() {
    class PostGorunumVH(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostGorunumVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PostGorunumVH(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostGorunumVH, position: Int) {
        holder.itemView.recycler_row_kullanicimail.text = postList.get(position).kullaniciemail
        holder.itemView.recycler_row_kullaniciyorum.text = postList.get(position).kullaniciyorum
        Picasso.get().load(postList[position].gorselurl).into(holder.itemView.recycler_row_gorsel)
    }

}