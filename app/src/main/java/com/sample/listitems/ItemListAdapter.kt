package com.sample.listitems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.listitems.model.ItemPojo
import kotlinx.android.synthetic.main.item_row_adapter.view.*

class ItemListAdapter
    (
    var MovieListData: ArrayList<ItemPojo>?,
    var context: Context, var mItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_adapter, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = MovieListData!![position]
        holder.mLabel.text = data.mLabel
        holder.mDescription.text = data.mDescription
        holder.mImage.setImageURI(data.mImageLink)

        holder.mEdit.setOnClickListener {
            mItemClickListener.onEditClick(data, position)
        }

        holder.mDelete.setOnClickListener {
            mItemClickListener.onDeleteClick(position)
        }
    }


    override fun getItemCount(): Int {
        return MovieListData!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mLabel = itemView.txt_label
        val mDescription = itemView.txt_description
        val mImage = itemView.image_
        val mDelete = itemView.action_delete
        val mEdit = itemView.action_edit

    }


    interface OnItemClickListener {
        fun onEditClick(response: ItemPojo?, position: Int)
        fun onDeleteClick(position: Int)

    }



}