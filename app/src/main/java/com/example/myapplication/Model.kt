package com.example.myapplication

data class Model(val id: Int, val value: String) {
    override fun toString() = "Model value is: $value"
}