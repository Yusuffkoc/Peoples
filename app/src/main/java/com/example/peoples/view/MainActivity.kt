package com.example.peoples.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.peoples.R
import com.example.peoples.viewmodel.PeopleViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PeopleViewModel
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var layoutManager = LinearLayoutManager(this)
    lateinit var recyclerView: RecyclerView
    var progressBar: ProgressBar? = null

    var failText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PeopleViewModel::class.java)

        failText = findViewById(R.id.tvNullEmpty)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        refreshApp()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (RecyclerView.OVER_SCROLL_IF_CONTENT_SCROLLS == newState) {
                    viewModel.getPeople()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        //First Get People Datas
        viewModel.getPeople()
        recyclerView.adapter = viewModel.adapter
    }


    private fun refreshApp() {

        swipeRefreshLayout?.setOnRefreshListener {
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
            swipeRefreshLayout!!.isRefreshing = false

            if (viewModel.refreshList()) {
                recyclerView.visibility = View.GONE
                failText?.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                failText?.visibility = View.GONE
            }
        }
    }


}