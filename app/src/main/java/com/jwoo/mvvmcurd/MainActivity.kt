package com.jwoo.mvvmcurd

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jwoo.mvvmcurd.databinding.ActivityMainBinding
import com.jwoo.mvvmcurd.dbs.Subscriber
import com.jwoo.mvvmcurd.dbs.SubscriberDatabase
import com.jwoo.mvvmcurd.dbs.SubscriberRepository

// Tutorial Source: https://youtu.be/v2yocpEcE_g

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: SubscriberRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerViewSubscriber.layoutManager = LinearLayoutManager(this)
        adapter =
            SubscriberRecyclerViewAdapter({ selectedItem: Subscriber -> listItemClicked(selectedItem) })
        binding.recyclerViewSubscriber.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.d("MYTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Log.d("MYTAG", "Currently selected subscriber's email: " + subscriber.email)
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}
