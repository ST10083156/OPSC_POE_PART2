package com.example.opsc_poe_part2_timewise

class CategoriesList private constructor(categories: MutableList<Category>) {
    companion object {
        private val categoriesList = mutableListOf<Category>()

        fun getCategoriesList(): List<Category> {
            return categoriesList.toList()
        }

        fun addCategory(category: Category) {
            categoriesList.add(category)
        }
        fun getCategory(index:Int) :Category
        {
            return categoriesList[index]
        }
        fun init(categories: MutableList<Category>) {
            categoriesList.addAll(categories)
        }
    }


}

