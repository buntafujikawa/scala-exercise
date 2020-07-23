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

    // practice
    for (a <- 1 to 1000; b <- 1 to 1000; c <- 1 to 1000 if a * 2 == b * 2 + c * 2) {
      println("a = " + a + "b = " + b + "c = " + c)
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
