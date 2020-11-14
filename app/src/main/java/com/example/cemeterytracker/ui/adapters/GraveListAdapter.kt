package com.example.cemeterytracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cemeterytracker.data.domain.DomainGrave
import com.example.cemeterytracker.databinding.GraveListItemBinding

class GraveListAdapter(val clickListener: GraveListListener) : ListAdapter<DomainGrave, GraveListAdapter.ViewHolder> (GraveListDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraveListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GraveListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GraveListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(val binding: GraveListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DomainGrave, clickListener: GraveListListener){
            binding.grave = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class GraveListListener(val clickListener : (id: Long) -> Unit){

        fun onClick(grave: DomainGrave) {
            clickListener(grave.graveId)
        }
    }

    class GraveListDiffUtil : DiffUtil.ItemCallback<DomainGrave>() {
        override fun areItemsTheSame(oldItem: DomainGrave, newItem: DomainGrave): Boolean {
            return oldItem.graveId == newItem.graveId
        }

        override fun areContentsTheSame(oldItem: DomainGrave, newItem: DomainGrave): Boolean {
            return oldItem == newItem
        }
    }


}