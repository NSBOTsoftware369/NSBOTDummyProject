package com.zasa.nsbotdummyproject

import android.graphics.RectF
import android.hardware.usb.UsbEndpoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slamtec.slamware.AbstractSlamwarePlatform
import com.slamtec.slamware.action.IMoveAction
import com.slamtec.slamware.discovery.DeviceManager
import com.slamtec.slamware.exceptions.*
import com.slamtec.slamware.geometry.PointF
import com.slamtec.slamware.robot.*
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var addsBtn: Button
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<MapData>
    private lateinit var userAdapter: MapAdapter
    private lateinit var rootPlatform: AbstractSlamwarePlatform
    private lateinit var startPoint: PointF
    private lateinit var area: RectF
    private lateinit var map: com.slamtec.slamware.robot.Map
    private lateinit var moveOption: MoveOption
    private lateinit var location: Location
    private lateinit var action: IMoveAction
    private lateinit var powerStatus: PowerStatus
    private lateinit var moveToPoint: IMoveAction
    var isLocalized : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // robot platform
        // enter your network ip and port
        rootPlatform = DeviceManager.connect("192.168.1.102", 5555)
        isLocalized = rootPlatform.mapLocalization

        val batteryStatus = powerStatus.batteryPercentage
        try {
            if (batteryStatus <= 20) {
                moveToPoint = rootPlatform.goHome()
            } else {
                // robot can do rest of the work
            }
        } catch (e: ConnectionFailException) {
            Log.d(TAG, "Connection Fail");
            e.printStackTrace();
        } catch (e: ParseInvalidException) {
            Log.d(TAG, "Parse Invalid");
            e.printStackTrace();
        } catch (e: InvalidArgumentException) {
            Log.d(TAG, "Invalid Argument");
            e.printStackTrace();
        } catch (e: ConnectionTimeOutException) {
            Log.d(TAG, "Connection TimeOut");
            e.printStackTrace();
        } catch (e: RequestFailException) {
            Log.d(TAG, "Request Fail");
            e.printStackTrace();
        } catch (e: UnauthorizedRequestException) {
            Log.d(TAG, "Unauthorized Request");
            e.printStackTrace();
        } catch (e: UnsupportedCommandException) {
            Log.d(TAG, "Unsupported Command");
            e.printStackTrace();
        }


        //get area
        area = rootPlatform.getKnownArea(MapType.BITMAP_8BIT)
        //get map and set to area
        map = rootPlatform.getMap(MapType.BITMAP_8BIT, MapKind.EXPLORE_MAP, area)
        // set the start point
        startPoint = map.origin

        //move option
        moveOption.isPrecise = true
        moveOption.isMilestone = true

        // dummy locations
        val table1 = Location(0F, 1F, 0F)
        val table2 = Location(0F, 1F, 0F)
        val table3 = Location(0F, 1F, 0F)

        btnAddMap.setOnClickListener {
            moveToPoint = rootPlatform.moveTo(table1, moveOption, 0F)
            moveToPoint = rootPlatform.moveTo(table2, moveOption, 0F)
            moveToPoint = rootPlatform.moveTo(table3, moveOption, 0F)
        }
        moveToPoint.waitUntilDone()


        // dummy tables
//        action = platform.moveTo(table1, moveOption, 0)
//        action.waitUntilDone()
//
//        action = platform.moveTo(table2, moveOption, 0)
//        action.waitUntilDone()
//
//        action = platform.moveTo(table3, moveOption, 0)
//        action.waitUntilDone()

//        /**set List*/
//        userList = ArrayList()
//        /**set find Id*/
//        addsBtn = findViewById(R.id.btnAddMap)
//        recv = findViewById(R.id.rvMaps)
//        /**set Adapter*/
//        userAdapter = MapAdapter(this, userList)
//        /**setRecycler view Adapter*/
//        recv.layoutManager = LinearLayoutManager(this)
//        recv.adapter = userAdapter
//        /**set Dialog*/
//        addsBtn.setOnClickListener {
//
//        }

    }

//        private fun addInfo() {
//            val inflter = LayoutInflater.from(this)
//            val v = inflter.inflate(R.layout.add_imap, null)
//
//            /**set view*/
//            val locationName = v.findViewById<EditText>(R.id.etLocationTitle)
//            val locationId = v.findViewById<EditText>(R.id.etLocationID)
//
//            val addDialog = AlertDialog.Builder(this)
//
//            addDialog.setView(v)
//            addDialog.setPositiveButton("Ok") { dialog, _ ->
//                userList.add(MapData("Location Name: $startPoint", "Table ID. : ${Location()}"))
//                userAdapter.notifyDataSetChanged()
//                Toast.makeText(this, "Adding User Information Success", Toast.LENGTH_SHORT).show()
//                dialog.dismiss()
//            }
//            addDialog.setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
//
//            }
//            addDialog.create()
//            addDialog.show()
//        }


}