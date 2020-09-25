//package com.example.powerfulljetpack.util
//
//data class Response1(val message: String?, val responseType: ResponseType1)
//sealed class ResponseType1 {
//
//    class Toast : ResponseType1()
//
//    class Dialog : ResponseType1()
//
//    class None : ResponseType1()
//
//}
//
//class Event<T>(
//    private val content: T
//) {
//
//    var hasBeenHandled: Boolean = false
//
//    fun getDataIfNotHandled(): T? {
//
//        if (hasBeenHandled) {
//            return null
//        } else {
//            hasBeenHandled = true
//            return content
//        }
//    }
//
//    fun peekContent() = content
//
//
//    companion object {
//
//        fun <T> dataEventt(data: T): Event<T>? {
//            data.let {
//                return Event(it)
//            }
//            return null
//        }
//
//
//        fun <T> responseEvent(response: Response1): Event<Response1>? {
//
//            response.let {
//                return Event(it)
//            }
//            return null
//        }
//    }
//}