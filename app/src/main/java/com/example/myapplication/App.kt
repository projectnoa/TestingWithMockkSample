package com.example.myapplication

class App(
    private val view: AppInterface.View,
    private val dataRepository: Repository
) : AppInterface.AppFeatures {

    override fun fetchData() {
        log("fetchData")

        try {
            val result = dataRepository.fetchData()

            view.onResult(result.map {
                UIDataModel(
                    Utils.generateUUID(),
                    it.id,
                    it.value
                )
            })
        } catch (err: Throwable) {
            view.onError(err)
        }
    }

    override fun log(message: String, tag: String) {
        print("TAG: $tag Message: $message")
    }
}