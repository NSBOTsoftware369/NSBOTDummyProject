package com.zasa.nsbotdummyproject

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_imap.view.*
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by Sangeeth Amirthanathan
 * on 10/3/2022.
 */
class MapAdapter(val context: Context, val mapList: ArrayList<MapData>) :
    RecyclerView.Adapter<MapAdapter.mapViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mapViewHolder {
        return mapViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: mapViewHolder, position: Int) {
        val mapList = mapList[position]
        holder.locationTitle.text = mapList.loccationName
        holder.locationId.text = mapList.locationId
    }

    override fun getItemCount() = mapList.size

    inner class mapViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var locationTitle: TextView
        var locationId: TextView
        var mMenu: ImageView

        init {
            locationTitle = itemView.tvLocatioTitle
            locationId = itemView.tvLocationID
            mMenu = itemView.mMenu
            mMenu.setOnClickListener {
                popupMenu(it)
            }
        }

        private fun popupMenu(view: View) {
            val position = mapList[adapterPosition]
            val popupMenu = PopupMenu(context.applicationContext, view)
            popupMenu.inflate(R.menu.show_menu)
            popupMenu.setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.editLocatioName -> {
                        val view = LayoutInflater.from(context).inflate(R.layout.add_imap, null)
                        val locationName = view.etLocationTitle
                        val locationID = view.etLocationID
                        AlertDialog.Builder(context)
                            .setView(view)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.loccationName = "Location Name : ${locationName.text.toString()}"
                                position.locationId = "Table ID : ${locationID.text.toString()}"
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "user information edited",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.deleteLocation -> {
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this item ?")
                            .setPositiveButton("Yes"){
                                dialog, _->
                                mapList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(context, "item deleted successfully", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                dialog, _->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

    }
}