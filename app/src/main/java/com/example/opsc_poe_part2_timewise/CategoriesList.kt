package com.example.opsc_poe_part2_timewise

import Category

class CategoriesList private constructor(categories: MutableList<Category>) {
    companion object {
        private val categoriesList = mutableListOf<Category>()

        fun getCategoriesList(): List<Category> {
            return categoriesList.toList()
        }

        fun addCategory(category: Category) {
            categoriesList.add(category)
        }

        fun getCategory(index: Int): Category {
            return categoriesList[index]
        }

        fun init(categories: MutableList<Category>, timesheetEntries: List<TimesheetEntry>) {
            categoriesList.addAll(categories)

            // Calculate total time for each category
            for (category in categoriesList) {
                var totalTime = 0L
                for (entry in timesheetEntries) {
                    if (entry.Category == category) {
                        totalTime += entry.TimeSpent.inWholeSeconds
                    }
                }
                category.totalTimeSeconds = totalTime
            }
        }
    }
}