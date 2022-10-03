package com.zasa.nsbotdummyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn:Button
    private lateinit var recv: RecyclerView
    private lateinit var userList:ArrayList<MapData>
    private lateinit var userAdapter:MapAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**set List*/
        userList = ArrayList()
        /**set find Id*/
        addsBtn = findViewById(R.id.btnAddMap)
        recv = findViewById(R.id.rvMaps)
        /**set Adapter*/
        userAdapter = MapAdapter(this,userList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter
        /**set Dialog*/
        addsBtn.setOnClickListener { addInfo() }

    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_imap,null)
        /**set view*/
        val locationName = v.findViewById<EditText>(R.id.etLocationTitle)
        val locationId = v.findViewById<EditText>(R.id.etLocationID)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            val locationName = locationName.text.toString()
            val locationId = locationId.text.toString()
            userList.add(MapData("Location Name: $locationName","Table ID. : $locationId"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }


}