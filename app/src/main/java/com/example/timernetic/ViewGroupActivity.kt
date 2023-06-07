package com.example.timernetic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timernetic.utils.groupAdapter
import com.example.timernetic.utils.groupData
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewGroupActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    //recycler view adapter
    private lateinit var recyclerViewtask: RecyclerView
    private lateinit var taskAdapter: groupAdapter
    private val taskList: MutableList<groupData> = mutableListOf()

    private lateinit var auth: FirebaseAuth
    private lateinit var dataReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group)

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

        //recycler view code
        recyclerViewtask = findViewById(R.id.recyclerView)

        // Create the adapter with an empty task list
        taskAdapter = groupAdapter(taskList)

        // Set the adapter for the RecyclerView
        recyclerViewtask.adapter = taskAdapter

        // Set the layout manager for the RecyclerView
        recyclerViewtask.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            // Load task data from Firebase Realtime Database
            val dataReference = FirebaseDatabase.getInstance().reference
                .child("task")
                .child(userId)

            val query = dataReference.orderByChild("task").equalTo(userId)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    taskList.clear()

                    // Iterate through the data snapshot and add tasks to the list
                    for (taskSnapshot in dataSnapshot.children) {
                        val task =
                            taskSnapshot.child("task").getValue(String::class.java)

                        task?.let {
                            val tasks = groupData(it)
                            taskList.add(tasks)
                        }
                    }

                    // Notify the adapter that the data has changed
                    taskAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error if needed
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
