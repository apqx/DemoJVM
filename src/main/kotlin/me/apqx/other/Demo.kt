package me.apqx.other

fun main(args: Array<String>) {
    val name: String
    var name_1: String

}

fun doSth(human: Human) {
    when(human) {
        is Human.Man -> {}
        is Human.Women -> {}
        else -> {}
    }
}


sealed class Human {
    data class Man(val name: String): Human()
    data class Women(val name: String): Human()
}