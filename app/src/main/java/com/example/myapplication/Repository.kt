package com.example.myapplication

class Repository {
    fun fetchData(): List<Model> {
        return listOf(
            Model(1, "A"),
            Model(2, "B"),
            Model(3, "C")
        )
    }
}