package bar

class Bar[T]
object Bar {
  implicit val bar = new Bar[Int]
}
