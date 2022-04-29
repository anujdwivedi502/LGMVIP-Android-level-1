package com.example.covidtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class rvAdapter(val context : Context, val stateList : ArrayList<trackerData>): RecyclerView.Adapter<rvAdapter.rvViewHolder>() {

    class rvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val state = itemView.findViewById<TextView>(R.id.state)
        val cases = itemView.findViewById<TextView>(R.id.Statecases)
        val recoverd = itemView.findViewById<TextView>(R.id.Staterecovered)
        val deaths = itemView.findViewById<TextView>(R.id.Statedeaths)
        val live = itemView.findViewById<TextView>(R.id.activecases)
        val status = itemView.findViewById<TextView>(R.id.status)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.rvitem,parent,false)
        return rvViewHolder(view)

    }

    override fun onBindViewHolder(holder: rvViewHolder, position: Int) {
        val currentState = stateList[position]
        holder.cases.text = currentState.cases.toString()
        holder.state.text = currentState.state.toString()
        holder.deaths.text = currentState.deaths.toString()
        holder.recoverd.text = currentState.recoverd.toString()
        holder.live.text = currentState.live.toString()

        if(currentState.status==false){
            holder.status.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}