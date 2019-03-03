package me.apqx.algorithm

import java.util.*

fun main(args: Array<String>) {
    val array = intArrayOf(3,2,7,8)
    println(Arrays.toString(array))
    selectSort(array = array)
    println(Arrays.toString(array))
}

/**
 * 冒泡排序
 */
fun bubbleSort(array: IntArray) {
    for (i in 0 until array.size) {
        var swapped = false
        for (j in 0..(array.size - 2 - i)) {
            if (array[j] > array[j + 1] ) {
                val temp = array[j]
                array[j] = array[j + 1]
                array[j + 1] = temp
                swapped = true
            }
        }
        if (!swapped) {
            return
        }
    }
}

/**
 * 选择排序
 */
fun selectSort(array: IntArray) {
    for (i in 0 until array.size) {
        for (j in 1 + i until  array.size) {
            if (array[i] > array[j]) {
                val temp = array[i]
                array[i] = array[j]
                array[j] = temp
            }
        }
    }
}

/**
 * 归并排序
 */
fun sort(array: IntArray) {

}