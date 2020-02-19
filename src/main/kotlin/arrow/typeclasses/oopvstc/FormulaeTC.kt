/* gakshintala created on 2/16/20 */
package arrow.typeclasses.oopvstc

sealed class Shape { // These are independent unrelated types, just put under an family of sealed classes.
    data class Circle(val radius: Double) : Shape()
    data class Rectangle(val width: Double, val height: Double) : Shape()
    data class Line(val length: Double) : Shape()
}

// Both Data and Functions can independently evolve.
interface FormulaeTC<in ShapeT> { // This is not at all tied to Shape Class family. The type is totally arbitrary.
    fun forArea(shape: ShapeT): Double // Nothing to loose in terms of Type Safety, as Methods strictly work on a type.
    fun forCircumference(shape: ShapeT): Double
}

interface PropsTC<in ShapeT> {
    fun forXCoordinates(shape: ShapeT): Pair<Double, Double>
}

// Because of implementing all methods of a Type Class `FormulaeTC`, 
// `Circle` type belongs to this TC and `circleFormulae` can be passed wherever FormulaeTC<T> is required.
val circleFormulae = object : FormulaeTC<Shape.Circle> {  
    override fun forArea(shape: Shape.Circle) = Math.PI * shape.radius * shape.radius
    override fun forCircumference(shape: Shape.Circle) = 2 * Math.PI * shape.radius
}

val rectangleFormulae = object : FormulaeTC<Shape.Rectangle> {
    override fun forArea(shape: Shape.Rectangle) = with(shape) { width * height }
    override fun forCircumference(shape: Shape.Rectangle) = 2 * with(shape) { width + height }
}

// New behaviors specific to certain types can be modelled as separate Type class.
val lineProps = object : PropsTC<Shape.Line> {
    override fun forXCoordinates(shape: Shape.Line): Pair<Double, Double> = 0.0 to shape.length
}

private fun <ShapeT> areaOf(shape: ShapeT, formulae: FormulaeTC<ShapeT>) = formulae.forArea(shape)
private fun <ShapeT> circumferenceOf(shape: ShapeT, formulae: FormulaeTC<ShapeT>) = formulae.forCircumference(shape)
private fun <ShapeT> xCoordinatesOf(shape: ShapeT, props: PropsTC<ShapeT>) = props.forXCoordinates(shape)

fun main() {
    println(areaOf(Shape.Circle(3.0), circleFormulae)) // Although 2 parameters, this is still polymorphic only on one type - ShapeT. 
    println(circumferenceOf(Shape.Circle(3.0), circleFormulae))
    
    println(areaOf(Shape.Rectangle(3.0, 3.0), rectangleFormulae))
    println(circumferenceOf(Shape.Rectangle(3.0, 3.0), rectangleFormulae))
    
    println(xCoordinatesOf(Shape.Line(3.0), lineProps))
}
