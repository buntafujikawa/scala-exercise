object Functions {
  val add = new Function2[Int, Int, Int] {
    def apply(x: Int, y: Int): Int = x + y
  }

  add.apply(100, 200)
  // applyメソッドはScalaコンパイラから特別扱いされ、x.apply(y)ではなく、x(y)と書くのが一般的
  add(100, 200)

  // 上記のシンタックスシュガーとして
  val add2 = (x: Int, y: Int) => x + y
  val addCurried = (x: Int) => (y: Int) => x + y

  // メソッドはdefで始まる構文で定義されたものであり、それを関数と呼ぶのはあくまで説明の便宜上であるということです

  // 高階関数
  def around(init: () => Unit, body: () => Any, fin: () => Unit): Any = {
    init()
    try {
      body()
    } finally {
      fin()
    }
  }

  around(
    () => println("a"),
    () => println("b"),
    () => println("c")
  )

  // practice

  import scala.io.Source

  def withFile[A](filename: String)(f: Source => A): A = {
    val s = Source.fromFile(filename)
    try {
      f(s)
    } finally {
      s.close()
    }
  }

  def printFile(filename: String): Unit = {
    withFile(filename) { file =>
      file.getLines().foreach(println)
    }
  }
}
