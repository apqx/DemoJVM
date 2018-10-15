package me.apqx.net

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.Channel
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

class NetTransfer(private val socketChannel: SocketChannel, private val id: String) {
    /**
     * 从Channel读取的Buffer
     */
    private val readBuffer: ByteBuffer = ByteBuffer.allocate(1024)
    /**
     * 控制读取协程
     */
    private val readJob: Job
    /**
     * 读取数据的暂存Channel
     */
    private val readTempChannel: Channel<ByteArray> = Channel(50)

    private lateinit var listener: IOnCmdReceiveListener

    init {
        readJob = startReadCmd()
    }

    private fun startReadCmd() = GlobalScope.launch(Dispatchers.Default) {
        while (isActive && socketChannel.isConnected) {
            // TODO 验证是否是阻塞的
            println("$id ready read")
            val count = socketChannel.read(readBuffer)
            if (count == -1) {
                println("$id read nothing!")
                delay(1000)
                continue
            }
            println("$id count = $count")
            val bytes = ByteArray(count)
            readBuffer.flip()
            // 最多只能从Buffer中获取bytes.size长度的数据
            readBuffer.get(bytes)
            readBuffer.clear()
            println("$id <= ${bytes.toHexString()}")
            if (this@NetTransfer::listener.isInitialized) {
                listener.onCmdReceive(bytes)
            }
            if (readTempChannel.isFull) {
                // 当缓存队列存满时，删除最早的那条数据
                println("$id receiveTempChannel is full, delete first = ${readTempChannel.receive()}")
            }
            readTempChannel.send(bytes)
        }
        println("$id readCoroutine stop")
    }

    /**
     * 获取字节数组的十六进制字符串
     */
    private fun ByteArray.toHexString(): String {
        val strBuilder = StringBuilder()
        for (byte in this) {
            var str = Integer.toHexString(byte.toInt())
            if (str.length == 1) {
                strBuilder.append("0")
            }
            strBuilder.append(str).append(" ")
        }
        val result = strBuilder.toString()
        return if (result.isEmpty()) "" else result.substring(0, result.length - 1)
    }

    /**
     * 发送字节数据
     *
     */
    suspend fun writeCmd(bytes: ByteArray) {
        println("$id => ${bytes.toHexString()}")
        val writeBuffer = ByteBuffer.wrap(bytes)
        // 每次发送指令之前，清空收到的指令缓存
        while (!readTempChannel.isEmpty) {
            println("cause write cmd, delete readTempChannel = " + readTempChannel.poll()?.toHexString())
        }
        // TODO 验证是否阻塞
        socketChannel.write(writeBuffer)
        writeBuffer.clear()
        println("$id send done")
    }

    /**
     * 读取字节数组，超时则返回空数组
     * @overTimeMills 超时时间，默认3s
     */
    suspend fun readCmd(overTimeMills: Long = 3000): ByteArray {
        var bytes = ByteArray(0)
        try {
            withTimeout(overTimeMills) {
                bytes = readTempChannel.receive()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bytes
    }

    fun setOnCmdReceiveListener(listener: IOnCmdReceiveListener) {
        this.listener = listener
    }

    suspend fun close() {
        try {
            readJob.cancel()

            socketChannel.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            println("$id socketChannel closed")
        }
    }

    interface IOnCmdReceiveListener {
        fun onCmdReceive(bytes: ByteArray)
    }
}
