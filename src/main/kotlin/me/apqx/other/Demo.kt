package me.apqx.other

import javax.print.attribute.IntegerSyntax

fun main(args: Array<String>) {
    val threadLocal = ThreadLocal<Int>()
    Thread {
        var i = 0
        while (true) {
            threadLocal.set(i++)
            Thread.sleep(500)
            println("1 get " + threadLocal.get())
        }
    }.start()
    Thread {
        var i = 0
        while (true) {
            Thread.sleep(500)
            println("2 get " + threadLocal.get())
        }
    }.start()

}

fun doSth() {
    println("emmm")
}


sealed class Human {
    data class Man(val name: String) : Human()
    data class Women(val name: String) : Human()
}