package me.apqx.other

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import javax.print.attribute.IntegerSyntax

fun main(args: Array<String>) {
    Thread {
//        listen()
    }.start()
    Thread {
        connect()
    }.start()
}

fun listen() {
    println("server listen...")
    val serverSocket = ServerSocket(1335)
    val socket = serverSocket.accept()
    println("server connected")
    val printStream = PrintStream(socket.getOutputStream())
    val str = "123456789"
    println("server => $str")
    printStream.println(str)
    Thread.sleep(1000)
    printStream.close()
    println("server closing")
    socket.close()
    println("server closed")
}

fun connect() {
    val socket = Socket(InetAddress.getByName("google.com"), 80)
    println("client connected")
    val printStream = PrintStream(socket.getOutputStream())
    val reqHeader = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: en-US,en;q=0.9,zh-CN;q=0.8,zh-TW;q=0.7,zh;q=0.6\n" +
            "Connection: keep-alive\n" +
            "Host: www.baidu.com\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36"
    printStream.println(reqHeader)
    println("client => $reqHeader")
    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
    while (true) {
        val str = reader.readLine() ?: break
        println("client <= $str")
    }
    Thread.sleep(1000)
    println("client closing")
    socket.close()
    println("client closed")
}