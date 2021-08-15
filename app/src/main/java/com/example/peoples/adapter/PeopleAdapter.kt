package com.example.peoples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peoples.R
import com.example.peoples.base.Person

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.MyViewHolder>() {

    private var personList = mutableListOf<Person>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var idTextView: TextView
        var nameTextView: TextView

        init {
            idTextView = itemView.findViewById(R.id.tvId)
            nameTextView = itemView.findViewById(R.id.tvName)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleAdapter.MyViewHolder {
        val contentView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_people_info, parent, false)

        return MyViewHolder(contentView)
    }

    override fun onBindViewHolder(holder: PeopleAdapter.MyViewHolder, position: Int) {
        val currentItem = personList[position]
        holder.nameTextView.text = currentItem.fullName
        holder.idTextView.text = currentItem.id.toString()
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    //Add new list
    fun setData(persons: List<Person>) {
        val distinct = deleteSameIds(persons)
        this.personList.addAll(distinct)
        notifyDataSetChanged()
    }

    //When Refresh Change List
    fun changeList(persons: List<Person>) {
        this.personList.clear()
        this.personList = deleteSameIds(persons) as MutableList<Person>
        notifyDataSetChanged()
    }

    fun deleteSameIds(persons: List<Person>): List<Person> {
        val distinct = persons.distinctBy { it.id }
        return distinct
    }
}