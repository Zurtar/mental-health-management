package com.zurtar.mhma

import android.graphics.Bitmap
import com.zurtar.mhma.data.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)