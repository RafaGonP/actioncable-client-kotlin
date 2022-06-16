# actioncable-client-kotlin

Ruby [Action Cable](http://guides.rubyonrails.org/action_cable_overview.html) client library for Kotlin.

## About RafaGonP's fork

This is a fork of original library: https://github.com/vinted/actioncable-client-kotlin

Main changes compared to original version:

* Introduced stable version of Kotlin coroutines
* Updated okhttp 2.x ---> okhttp 3.x
* Changed serializer klaxon ---> gson

# Usage

## Requirements

* Kotlin 1.3 or later
* [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) 1.2.1 or later
* [Gson](https://github.com/google/gson) 2.x 
* [okhttp](https://github.com/square/okhttp) 3.x

## Download

```groovy
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.vinted:actioncable-client-kotlin:0.2.1'
}
```

## Basic

```kotlin
// 1. Setup
val uri = URI("ws://cable.example.com")
val consumer = ActionCable.createConsumer(uri)

// 2. Create subscription
val appearanceChannel = Channel("AppearanceChannel")
val subscription = consumer.subscriptions.create(appearanceChannel)

subscription.onConnected = {
    // Called when the subscription has been successfully completed
}

subscription.onRejected = {
    // Called when the subscription is rejected by the server
}

subscription.onReceived = { data: Any? ->
    // Called when the subscription receives data from the server
    // Possible types...
    when (data) {
        is Int -> { }
        is Long -> { }
        is BigInteger -> { }
        is String -> { }
        is Double -> { }
        is Boolean -> { }
        is JsonObject -> { }
        is JsonArray<*> -> { }
    }
}

subscription.onDisconnected = {
    // Called when the subscription has been closed
}

subscription.onFailed = { error ->
    // Called when the subscription encounters any error
}

// 3. Establish connection
consumer.connect()

// 4. Perform any action
subscription.perform("away")

// 5. Perform any action with params
subscription.perform("hello", mapOf("name" to "world"))
```

## Passing Parameters to Channel

```kotlin
val chatChannel = Channel("ChatChannel", mapOf("room_id" to 1))
```

The parameter container is `Map<String, Any?>` and is converted to `JsonObject(Gson)` internally.
To know what type of value can be passed, please read [Gson user guide](https://github.com/google/gson).

## Passing Parameters to Subscription#perform

```kotlin
subscription.perform("send", mapOf(
    "comment" to mapOf(
        "text" to "This is string.",
        "private" to true,
        "images" to arrayOf(
            "http://example.com/image1.jpg",
            "http://example.com/image2.jpg"
        )
    )
))
```

The parameter container is `Map<String, Any?>` and is converted to `JsonObject(Gson)` internally.
To know what type of value can be passed, please read [Gson user guide](https://github.com/google/gson).

## Options

```kotlin
val uri = URI("ws://cable.example.com")
val options = Consumer.Options()
options.connection.reconnection = true

val consumer = ActionCable.createConsumer(uri, options)
```

Below is a list of available options.

* sslContext
    
    ```kotlin
    options.connection.sslContext = yourSSLContextInstance
    ```
    
* hostnameVerifier
    
    ```kotlin
    options.connection.hostnameVerifier = yourHostnameVerifier
    ```
    
* query
    
    ```kotlin
    options.connection.query = mapOf("user_id" to "1")
    ```
    
* headers
    
    ```kotlin
    options.connection.headers = mapOf("X-Foo" to "Bar")
    ```
    
* reconnection
    * If reconnection is true, the client attempts to reconnect to the server when underlying connection is stale.
    * Default is `false`.
    
    ```kotlin
    options.connection.reconnection = false
    ```
    
* reconnectionMaxAttempts
    * The maximum number of attempts to reconnect.
    * Default is `30`.
    
    ```kotlin
    options.connection.reconnectionMaxAttempts = 30
    ```

* okHttpClientFactory
    * Factory instance to create your own OkHttpClient.
    * If `okHttpClientFactory` is not set, just create OkHttpClient by `OkHttpClient()`.
    
    ```kotlin
    options.connection.okHttpClientFactory = {
        OkHttpClient().also {
            it.networkInterceptors().add(StethoInterceptor())
        }
    }
    ```

## Authentication

How to authenticate a request depends on the architecture you choose.

### Authenticate by HTTP Header

```kotlin
val options = Consumer.Options()
options.connection.headers = mapOf("Authorization" to "Bearer xxxxxxxxxxx")

val consumer = ActionCable.createConsumer(uri, options)
```

### Authenticate by Query Params

```kotlin
val options = Consumer.Options()
options.connection.query = mapOf("access_token" to "xxxxxxxxxxx")

val consumer = ActionCable.createConsumer(uri, options)
```

### License

```
MIT License

Copyright (c) 2019 RafaGonP UAB

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
