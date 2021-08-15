package com.example.peoples.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.peoples.adapter.PeopleAdapter
import com.example.peoples.base.DataSource
import com.example.peoples.base.FetchCompletionHandler
import com.example.peoples.base.FetchError
import com.example.peoples.base.FetchResponse

class PeopleViewModel(application: Application) : AndroidViewModel(application) {

    private var _loading = MutableLiveData<Int>()
    val loading: LiveData<Int>
        get() = _loading

    init {
        _loading.value = View.GONE
    }

    var adapter = PeopleAdapter()

    var next = 10
    fun getPeople() {
        _loading.value = View.VISIBLE
        val d = DataSource()

        d.fetch(next.toString(), object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {

                if (!p1?.people.isNullOrEmpty()) {
                    next += p1!!.people.size
                    adapter.setData(p1.people)
                    //for initialize
                    if (adapter.itemCount < 10) {
                        getPeople()
                    }
                }
            }
        })
        _loading.value = View.GONE
    }

    var isEmptyList: Boolean = false
    fun refreshList(): Boolean {
        val d = DataSource()

        d.fetch(next.toString(), object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {
                if (!p1?.people.isNullOrEmpty()) {
                    isEmptyList = false
                    next = (10..30).random()
                    adapter.changeList(p1!!.people)
                    if (adapter.itemCount < 10) {
                        getPeople()
                    }
                } else {
                    isEmptyList = true
                    next=10
                }
            }
        })
/*
        FOR YOUR TEST TO SHOW DISPLAY NO ONE IS HERE TEXT,
         JUST OPEN DOWN LINE THEN REFRESH THE PAGE          */
        //isEmptyList=true

        return isEmptyList
    }
}