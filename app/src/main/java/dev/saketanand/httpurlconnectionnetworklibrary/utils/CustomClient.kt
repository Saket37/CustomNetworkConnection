package dev.saketanand.httpurlconnectionnetworklibrary.utils

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class CustomClient {
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader, arrayOf(service), NetworkInvocationHandler()
        ) as T
    }
}

class NetworkInvocationHandler : InvocationHandler {
    override fun invoke(
        proxy: Any?,
        method: Method?,
        args: Array<out Any?>?
    ): Any? {


        return null
    }

}