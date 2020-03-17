/* gakshintala created on 2/16/20 */
package arrow.typeclasses.oopvstc

// These are independent unrelated types, with different props. 
data class Circle(val radius: Double)
data class Rectangle(val width: Double, val height: Double)
data class Line(val length: Double)

// Both Data and Functions can independently evolve.
interface FormulaeTC<in ShapeT> { // This is not at all tied to Shape Class family. The type is totally arbitrary.
    fun forArea(shape: ShapeT): Double // Nothing to loose in terms of Type Safety, as Methods strictly work on a type.
    fun forCircumference(shape: ShapeT): Double
}

// Because of implementing all methods of a Type Class `FormulaeTC`, 
// `Circle` type is a member of FormulaeTC and `circleFormulae` can be passed wherever FormulaeTC<T> is required.
val circleFormulae = object : FormulaeTC<Circle> {
    override fun forArea(shape: Circle) = Math.PI * shape.radius * shape.radius
    override fun forCircumference(shape: Circle) = 2 * Math.PI * shape.radius
}

val rectangleFormulae = object : FormulaeTC<Rectangle> {
    override fun forArea(shape: Rectangle) = with(shape) { width * height }
    override fun forCircumference(shape: Rectangle) = 2 * with(shape) { width + height }
}

// New behaviors specific to certain types can be modelled as separate Type class.
interface PropsTC<in ShapeT> {
    fun forXCoordinates(shape: ShapeT): Pair<Double, Double>
}

val lineProps = object : PropsTC<Line> {
    override fun forXCoordinates(shape: Line): Pair<Double, Double> = 0.0 to shape.length
}

// These are just util methods, nothing significant.
private fun <ShapeT> areaOf(shape: ShapeT, formulae: FormulaeTC<ShapeT>) = formulae.forArea(shape)
private fun <ShapeT> circumferenceOf(shape: ShapeT, formulae: FormulaeTC<ShapeT>) = formulae.forCircumference(shape)
private fun <ShapeT> xCoordinatesOf(shape: ShapeT, props: PropsTC<ShapeT>) = props.forXCoordinates(shape)

fun main() {
    // Although 2 parameters, both params are restricted to only one type - ShapeT.
    println(areaOf(Circle(3.0), circleFormulae))
    println(circumferenceOf(Circle(3.0), circleFormulae))

    println(areaOf(Rectangle(3.0, 3.0), rectangleFormulae))
    println(circumferenceOf(Rectangle(3.0, 3.0), rectangleFormulae))

    println(xCoordinatesOf(Line(3.0), lineProps))
}
