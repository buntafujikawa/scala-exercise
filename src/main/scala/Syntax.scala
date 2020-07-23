object Syntax {
  def main(args: Array[String]): Unit = {
    (3950000 * (0.023 / 12 * 8)).floor.toInt

    val age: Int = 5
    val isSchoolStarted: Boolean = false

    // if
    if (1 <= age && age <= 6 && !isSchoolStarted) {
      println("幼児です")
    } else {
      println("幼児ではありません")
    }

    // while
    var i = 1
    // i: Int = 1

    while (i <= 10) {
      println("i = " + i)
      i = i + 1
    }

    loopFrom0To9()

    // for
    for (x <- 1 to 5; y <- 1 until 5 if x != y) {
      println("x = " + x + " y = " + y)
    }

    for (e <- List("A", "B", "C")) println(e)

    for (e <- List("A", "B", "C")) yield {
      "Pre" + println(e)
    }

    for (a <- 1 to 1000; b <- 1 to 1000; c <- 1 to 1000 if a * 2 == b * 2 + c * 2) {
      println("a = " + a + "b = " + b + "c = " + c)
    }

    // match
    val taro = "Taro"

    taro match {
      case "abc" | "def" => "abcdef"
      case "Taro" => "Male"
      case _ => "other"
    }

    val lst = List("A", "B", "C")
    lst match {
      case List("A", b, c) if b != "B" =>
        println("b != B")
        println("b = " + b)
        println("c = " + c)
      case List("A", b, c) =>
        println("b = " + b)
        println("c = " + c)
      case "A" :: b :: c :: _ =>
        /*
        中置パターン
        ::の前の要素がリストの最初の要素を、後ろの要素がリストの残り全てを指すことになります。リストの末尾を無視する場合、上記のようにパターンの最後に _ を挿入するといったことが必要になります。
         */
        println("b = " + b)
        println("c = " + c)
      case _ =>
        println("nothing")
    }

    val lst2 = List(List("A"), List("B", "C"))
    lst2 match {
      // @の後に続くパターンにマッチする式を @ の前の変数（ここではa）に束縛します
      case List(a@List("A"), x) =>
        println(a) // List(A)
        println(x) // List(B,C)
    }

    import java.util.Locale
    val obj: AnyRef = "String Literal"

    obj match {
      case v:java.lang.Integer => {
        println("Integer!")
      }
      case v:String => {
        println(v.toUpperCase(Locale.ENGLISH))
      }
    }

    for (i <- 1 to 1000) {
      val list = new scala.util.Random(new java.security.SecureRandom()).alphanumeric.take(5).toList match {
        case List(a, b, c, d, _) => List(a, b, c, d, a).mkString("")
      }
      println(list)
    }
  }

  def loopFrom0To9(): Unit = {
    var i = 0
    do {
      println(i)
      i += 1
    } while (i < 10)
  }
}
