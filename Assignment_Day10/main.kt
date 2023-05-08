package com.example.delegationdemo
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

import kotlin.properties.ReadWriteProperty

//lazy
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun lazyExample() {
    println(lazyValue)
    println(lazyValue)
}

//Obersoervable properties
class User {
    var name: String by Delegates.observable("<no name>") {
            prop, old, new ->
        println("$old -> $new")
    }
}

fun oberservablePropertiesExample() {
    val user = User()
    user.name = "first"
    user.name = "second"
}

//Delegating to another property
class MyClass {
    var newName: Int = 0
    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
    var oldName: Int by this::newName
}
fun delegatingToAnotherProperty() {
    val myClass = MyClass()
    // Notification: 'oldName: Int' is deprecated.
    // Use 'newName' instead
    myClass.oldName = 42
    println(myClass.newName) // 42
}

//Storing properties in a map
class UserS(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

fun storingPropertiesInAMap() {
    val user = UserS(mapOf(
        "name" to "John Doe",
        "age"  to 25
    ))
    println(user.name) // Prints "John Doe"
    println(user.age)  // Prints 25
}

fun main(){
    println("Lazy Example:")
    lazyExample()
    println("Oberservable Example:")
    oberservablePropertiesExample()
    println("Delegating to another property:")
    delegatingToAnotherProperty()
    println("Storing properties in a map:")
    storingPropertiesInAMap()

}


