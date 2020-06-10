package com.jwoo.mvvmcurd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jwoo.mvvmcurd.databinding.ListItemBinding
import com.jwoo.mvvmcurd.dbs.Subscriber

class SubscriberRecyclerViewAdapter (private val subscribersList : List<Subscriber>) : RecyclerView.Adapter<SubscriberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribersList[position])
    }
}

class SubscriberViewHolder(val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber : Subscriber){
        binding.txtName.text = subscriber.name
        binding.txtEmail.text = subscriber.email
    }
}