package net.agriblockchain.data.model

enum class ProductType(text: String) {

    CARROT("CARROT"),
    TOMATO("TOMATO"),
    PINEAPPLE("PINEAPPLE");

    val value: String = text
}