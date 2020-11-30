package com.example.cemeterytracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cemeterytracker.data.domain.DomainCemetery
import com.example.cemeterytracker.databinding.CemListItemBinding

class CemeteryListAdapter(val clickListener: CemListListener) : ListAdapter<DomainCemetery, CemeteryListAdapter.ViewHolder>(CemListDiffUtil()){

    class ViewHolder(val binding: CemListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cemetery: DomainCemetery, clickListener: CemListListener){
            binding.cemetery = cemetery
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CemListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class CemListListener(val clickListener: (id : Long) -> Unit){

        fun onClick(cemetery: DomainCemetery){
            clickListener(cemetery.cemeteryId)
        }
    }

    class CemListDiffUtil : DiffUtil.ItemCallback<DomainCemetery>() {
        override fun areItemsTheSame(oldItem: DomainCemetery, newItem: DomainCemetery): Boolean {
            return oldItem.cemeteryId == newItem.cemeteryId
        }

        override fun areContentsTheSame(oldItem: DomainCemetery, newItem: DomainCemetery): Boolean {
            return oldItem == newItem
        }
    }
}