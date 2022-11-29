package com.example.myapplication

interface AppInterface {
    interface AppFeatures {
        fun fetchData()
        fun log(message: String, tag: String = "App")
    }

    interface View {
        fun onResult(result: List<UIDataModel>)
        fun onError(error: Throwable)
    }
}