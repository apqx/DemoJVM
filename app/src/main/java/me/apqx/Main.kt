package me.apqx

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import me.apqx.abs.AbsMan
import me.apqx.bean.Student
import me.apqx.interfaces.ISay
import me.apqx.interfaces.ISayJ

fun main(args: Array<String>) {
    for (i in 0 until 100) {
        println(Student("Tom$i", i))
    }

    val iSay = object : ISay {
        override fun say(str: String) {
            println("str")
        }
    }

    val iSayJ = ISayJ { }

    val absMan = object : AbsMan() {
        override fun say(str: String) {
            println("str")
        }
    }

    Observable.create(ObservableOnSubscribe<String> { println("RxJava") })
            .subscribe()
}
