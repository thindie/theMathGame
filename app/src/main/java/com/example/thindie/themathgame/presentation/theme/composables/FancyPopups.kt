package com.example.thindie.themathgame.presentation.theme.composables

fun fancyPopups(): String{
   val list = mutableListOf<String>()
    list.add("<3")
    list.add("perfect!")
    list.add("WOOOOOW")
    list.add("NICE")
    list.add("=o^-^o=")
    list.add(":)")
    list.add(":]")
    list.add("?:~,")
    list.shuffle()
    return list[0]
}