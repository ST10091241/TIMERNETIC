package com.example.timernetic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class TimesheetActivity : AppCompatActivity() {

    private lateinit var taskPictureIV: ImageView
    private var pic: Bitmap? = null
    //Task data
    private lateinit var closeGroupPopUpBtn: ImageView
    private lateinit var taskName: EditText
    private lateinit var taskGroupName: EditText
    private lateinit var taskDescription: EditText
    private lateinit var taskStartDate: EditText
    private lateinit var taskEndDate: EditText
    //take pic btn
    private lateinit var taskPicIV: ImageView
    private lateinit var addGroupbtn:ImageView

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            // Handle navigation item clicks here
            when (item.itemId) {
                R.id.nav_option1 -> {
                    // option 1 click
                    // val navController = findNavController(this, R.id.nav_host_fragment)
                    // navController.navigate(R.id.action_splashFragment_to_homeFragment)
                }
                R.id.nav_option2 -> {
                    val intent = Intent(this, GoalsActivity::class.java)
                    startActivity(intent)
                }


                R.id.nav_option3 -> {
                    // Handle option 3 click
                    val intent = Intent(this, ViewTimesheetActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_option4 -> {
                    // Handle option 4 click

                }
                R.id.nav_option5 -> {
                    // Handle option 5 click
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        setContentView(R.layout.activity_timesheet)
        //ADD TO DATABASE
        addGroupbtn= findViewById(R.id.addGroupbtn)

        //close group btn
        closeGroupPopUpBtn= findViewById(R.id.closeGroupPopUpBtn)
        closeGroupPopUpBtn.setOnClickListener{
            val intent = Intent(this@TimesheetActivity, ViewTimesheetActivity::class.java)
            startActivity(intent)
        }
        //loading picture to image view
        taskPictureIV= findViewById(R.id.taskPictureIV)
        taskPictureIV.visibility = View.GONE
        taskPictureIV.isEnabled = false
        //taking picture button
        taskPicIV= findViewById(R.id.taskPicIV)
        taskPicIV.isEnabled = false
        //REQUEST FOR CAMERA PERMISSIONS AND ENABLED BUTTON IF ALLOWED
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 111)
        } else {
            taskPicIV.isEnabled = true
        }
        //OPEN CAMER TAKE PICTURE
        taskPicIV.setOnClickListener {
            var icamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(icamera, 101)
        }


    }
    //ONCE PICTURE TAKEN SAVE PIC AND UPLOAD TO IMAGE VIEW
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            pic = data?.getParcelableExtra<Bitmap>("data")
            taskPictureIV.isEnabled = true
            taskPictureIV.visibility = View.VISIBLE
            taskPictureIV.setImageBitmap(pic)
        }
    }
    //FOR CAMERA PERMISSIONS
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            taskPicIV.isEnabled = true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}