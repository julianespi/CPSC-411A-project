package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NamesAdapter(private val names: List<String>) : RecyclerView.Adapter<NamesAdapter.NameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return NameViewHolder(view)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.nameTextView.text = names[position]
    }

    override fun getItemCount(): Int = names.size

    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(android.R.id.text1)
    }
}

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout  = findViewById(R.id.drawer_view)
        val openDrawerButton: Button = findViewById(R.id.open_drawer_button)
        val recyclerView: RecyclerView = findViewById(R.id.names_recycler_view)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val names = List(20) { "Name ${it + 1}" }
        recyclerView.adapter = NamesAdapter(names)

        // Button click to open drawer
        openDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(findViewById(R.id.drawer_view))
        }

    }
}