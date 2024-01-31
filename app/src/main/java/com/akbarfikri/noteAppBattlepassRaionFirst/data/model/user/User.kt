package com.akbarfikri.noteAppBattlepassRaionFirst.data.model.user

data class User(
    val name: String,
    val password: String,
    val nim: String,
    val id: Int = 0,
    val description: String="Halo aku kucing lucu, umurku 100 tahun dan suka makan ikan"
)