object ClassExercise {
  def main(args: Array[String]): Unit = {
    // プライマリコンストラクタの引数にval/varをつけるとそのフィールドは公開され、外部からアクセスできるようになります
    class Point(val x: Int, val y: Int) {
      def +(p: Point): Point = {
        new Point(x + p.x, y + p.y)
      }

      override def toString(): String = "(" + x + "," + y + ")"

      // private[this] をつけると、同じオブジェクトからのみアクセス可能になる
      // private[パッケージ名] を付けると同一パッケージに所属しているものからのみ
      // protected[パッケージ名] をつけると、派生クラスに加えて追加で同じパッケージに所属しているもの全てからアクセスできるように
    }


    val p1 = new Point(1, 1)
    val p2 = new Point(2, 2)
    p1 + p2

    class Adder {
      def add(x: Int)(y: Int): Int = x + y

      def add2(x: Int, y: Int): Int = x + y
    }

    val adder = new Adder()
    adder.add(2)(3) // 5
    adder.add2(2, 3) // 5

    val fun = adder.add(2) _ // 部分適用
    fun(3) // 5

    val fun2 = adder.add2(2, _)
    fun2(3) // 5

    // 抽象メンバー
    abstract class XY {
      def x: Int

      def y: Int
    }

    // 継承
    class APrinter() {
      def print(): Unit = {
        println("A")
      }
    }

    class BPrinter() extends APrinter {
      override def print(): Unit = {
        println("B")
      }
    }

    new APrinter().print

    new BPrinter().print

    // practice
    abstract class Shape {
      def area: Double
    }

    class Rectangle(val w: Double, val h: Double) extends Shape {
      override def area: Double = {
        w * h
      }
    }

    class Circle(val r: Double) extends Shape {
      override def area: Double = {
        r * r * math.Pi
      }
    }

    var shape: Shape = new Rectangle(10.0, 20.0)
    println(shape.area)
    shape = new Circle(2.0)
    println(shape.area)
  }
}

