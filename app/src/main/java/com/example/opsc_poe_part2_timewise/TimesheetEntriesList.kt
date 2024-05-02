package com.example.opsc_poe_part2_timewise

class TimesheetEntriesList private constructor(entries: MutableList<TimesheetEntry>) {
    companion object {
        private val entriesList = mutableListOf<TimesheetEntry>()

        fun getEntriesList(): List<TimesheetEntry> {
            return entriesList.toList()
        }

        fun addEntry(entry: TimesheetEntry) {
            entriesList.add(entry)
        }

        fun getEntry(index: Int): TimesheetEntry {
            return entriesList[index]
        }

        fun init(entries: MutableList<TimesheetEntry>) {
            entriesList.addAll(entries)
        }
    }
}
