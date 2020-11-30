package com.example.cemeterytracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.domain.DomainCemetery
import com.example.cemeterytracker.databinding.UserAddedCemListItemBinding

class UserAddedCemListAdapter(val clickListener: UserAddedCemListListener) : ListAdapter<DomainCemetery, UserAddedCemListAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder(val binding: UserAddedCemListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(cemetery: DomainCemetery, clickListener: UserAddedCemListListener){
            binding.clickListener = clickListener
            binding.cemetery = cemetery
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserAddedCemListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class DiffUtilCallback() : DiffUtil.ItemCallback<DomainCemetery>(){
        override fun areItemsTheSame(oldItem: DomainCemetery, newItem: DomainCemetery): Boolean {
            return oldItem.cemeteryId == newItem.cemeteryId
        }

        override fun areContentsTheSame(oldItem: DomainCemetery, newItem: DomainCemetery): Boolean {
            return oldItem == newItem

        }
    }

    class UserAddedCemListListener(val clickListener: (id : Long) -> Unit){

        fun onClick(cemetery : DomainCemetery){
            clickListener(cemetery.cemeteryId)
        }
    }


}

