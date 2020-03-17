/* gakshintala created on 2/16/20 */
package arrow.typeclasses.oopvstc

sealed class ShapeOOP { // Subtype polymorphism.
    abstract fun areaWithFormula(): Double
    abstract fun circumferenceWithFormula(): Double

    // If all we want is a different behaviour at run time, why are we tying it up with the data?
    data class Circle(val radius: Double) : ShapeOOP() {
        override fun areaWithFormula() = Math.PI * radius * radius // Methods are bound to their types by where they are declared.
        override fun circumferenceWithFormula() = 2 * Math.PI * radius
    }

    data class Rectangle(val width: Double, val height: Double) : ShapeOOP() {
        override fun areaWithFormula() = width * height
        override fun circumferenceWithFormula() = 2 * (width + height)
    }

    data class Line(val length: Double) : ShapeOOP() {
        override fun areaWithFormula(): Nothing = throw UnsupportedOperationException() // Code Smell
        override fun circumferenceWithFormula(): Nothing = throw UnsupportedOperationException() // Code Smell
    }
}

private fun areaOf(shape: ShapeOOP) = shape.areaWithFormula()
private fun circumferenceOf(shape: ShapeOOP) = shape.circumferenceWithFormula()

fun main() {
    println(areaOf(ShapeOOP.Circle(3.0)))
    println(circumferenceOf(ShapeOOP.Circle(3.0)))
    println(areaOf(ShapeOOP.Rectangle(3.0, 3.0)))
    println(circumferenceOf(ShapeOOP.Rectangle(3.0, 3.0)))
}
