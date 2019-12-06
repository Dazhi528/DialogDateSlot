package com.dazhi.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dazhi.dateslot.DialogDateSlot
import com.dazhi.dateslot.InteDateSlot
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        btTimeSlotTest.setOnClickListener {
            DialogDateSlot(this, "2019-11-20",
                    "2019-12-02", InteDateSlot { timeSta, timeEnd ->
                Toast.makeText(this,
                        "开始：$timeSta ~ 结束：$timeEnd",
                        Toast.LENGTH_LONG)
                        .show()
            }).show()
        }
    }
}
