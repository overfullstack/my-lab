interface ParentInterface {
    fun printName(name: String = "parentInterface")
}

interface ChildInterface : ParentInterface {
    fun printChildName(name: String = "childInterface") = printName(name)
}

val parentInstance = object : ParentInterface {
    override fun printName(name: String) = println("$name: parentInstance")
}

val childInstance: ChildInterface = object : ChildInterface, ParentInterface by parentInstance {}

fun main() {
    childInstance.printChildName()
    childInstance.printName()
}
