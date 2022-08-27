package com.yaman.library_store

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.yaman.library_store.databinding.ActivityMainBinding
import com.yaman.library_store.realm_db.RealmDb
import com.yaman.library_store.room_db.RoomDatabaseBuilder
import com.yaman.library_store.room_db.User
import com.yaman.library_store.room_db.User2
import com.yaman.library_tools.BuildConfig
import com.yaman.library_tools.app_utils.core_utils.LogUtils
import com.yaman.library_tools.app_utils.generic_services.ServiceReceiver
import kotlinx.coroutines.*
import kotlin.also
import kotlin.apply


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Log.e("TAG", "<<<<<<<<<<<onCreate: MainActivity INIT>>>>>>>>>>>")

        Intent(this, MyService::class.java).also { intent ->
            startService(intent)
        }

        Intent(this, MyService::class.java).also { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            }
        }

        registerReceiver(ServiceReceiver {

            // Get extra data included in the Intent
            val message = it?.getBooleanExtra("Status", false)
            val location = it?.getStringExtra("Location")

            Log.e("mMessageReceiver- ", "onReceive: $message -> $location")

        }, IntentFilter("GPSLocationUpdates"))


        CoroutineScope(Dispatchers.IO).launch {

            val database = RoomDatabaseBuilder.initBuilder(applicationContext, "mydatabase")
            val d = database.databaseDao()

            d.insertAll(
                User(101, "Yaman", "Aswal"),
                User(102, "Manish", "Aswal"),
                User(103, "Test", "Kumar"),
            )

            d.insertAll(
                User(101, "Yaman", "Aswal"),
                User(102, "Manish", "Aswal"),
                User(103, "Test", "Kumar"),
            )

            Log.e("TAG", "onCreate: " + d.getAllUsers())
            Log.e("TAG", "onCreate: " + d.getAllTests())


            RealmDb.getRealmInstance().executeTransactionAsync {
                val user = User2().apply {
                    firstName = "Yaman Aswal"
                    lastName = "Tse Aswal"
                }
                it.insert(user)
            }

            RealmDb.getRealmInstance().executeTransactionAsync {
                val users = RealmDb.getRealmInstance().where(User2::class.java).findAll()
                Log.e("RealmDb: ADD: ", "onCreate: $users")
            }

            RealmDb.getRealmInstance().executeTransactionAsync {
                val user = RealmDb.getRealmInstance().where(User2::class.java).findFirst()
                user?.deleteFromRealm()
                Log.e("RealmDb: Delete", "onCreate: $user")
            }


        }


        // LOG INIT.
        LogUtils.isDebuggable = BuildConfig.DEBUG
        // LOG USE.
        LogUtils.e("TAG", "MESSAGE")

        binding.openDialog.setOnClickListener {
//            val timePickerMaterial = TimePickerMaterial(this, inputMode = MaterialTimePicker.INPUT_MODE_CLOCK)
//            { positive, negative ->
//                Log.e("TimePickerMaterial: ", "onCreate: $positive  - $negative " )
//            }
//            timePickerMaterial.show(supportFragmentManager,"timePickerMaterial")

//            val datePickerMaterial = DatePickerMaterial {
//                    positive, negative ->    Log.e("TimePickerMaterial: ", "onCreate: $positive  - $negative " )
//            }
//
//            datePickerMaterial.show(supportFragmentManager, "timePickerMaterial")

//            val contextView = findViewById<View>(R.id.openDialog)
//            SnackBarMaterial(view = contextView, "TEST").apply {
//                this.setAction("Close Button") {
//                    Log.e("SnackBarMaterial", "setAction: " )
//                }
//                this.showLong()
//            }

        }


        binding.editTextId.doOnTextChanged { text, start, before, count ->
            Log.e("doOnTextChanged: ", "onCreate: $text")
        }

//        binding.closeDialog.updateLayoutParams {
//            height = 200
//            width = 200
//        }

    }



}