package com.snn.voc

data class Word(
    val id: Int,
    val name: String,
    val type: String,
    val mean: String,
    val synonym: String,
    val antonym: String,
    val sentence: String,
    val isUserAdd: Int
)
