package generics

import reified.SomeJava
import reified.SomeOther

/*class SomeClass<T>(val someOther: SomeOther<T>) {
}*/

class SomeClass<out T>(val someOther: SomeOther<out T>) {
}

inline fun <reified T> body(p: () -> SomeClass<T>) {
    SomeJava.fromPublisher(p().someOther, T::class.java)
}

    
