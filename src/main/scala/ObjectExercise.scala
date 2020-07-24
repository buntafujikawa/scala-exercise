object ObjectExercise {

  case class Point(x: Int, y: Int)

  Point(1, 2).equals(Point(1, 2))

  // コンパニオンオブジェクト
  // クラスと同じファイル内、同じ名前で定義されたシングルトンオブジェクトは、コンパニオンオブジェクトと呼ばれます
  class Person(name: String, age: Int, private val weight: Int)

  object Person {
    val taro = new Person("Taro", 20, 70)
    println(taro.weight)
  }

}
