package com.example.opsc_poe_part2_timewise

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.Date
import kotlin.time.Duration

data class TimesheetEntry(
    val Name: String,
    val Description:String,
    val Category:Category,
    var Date: Date,
    val TimeSpent: Duration,
    val Image: Uri?,
)