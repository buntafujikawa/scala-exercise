object TypeParameter {

  // Scala言語では最初から順に、 A、B、…と命名する慣習がある
  class Cell[A](var value: A) {
    def put(newValue: A): Unit = {
      value = newValue
    }

    def get(): A = value
  }

  val cell = new Cell[Int](1)

  val m = 7
  val n = 3
  new Tuple2(m / n, m % n) // (2,1)
  (m / n, m % n) // (2,1)

  /*
  # 非変/共変/反変
  型パラメータの前に+を付けるとその型パラメータは（あるいはそのクラスは）共変になる

  ## 非変
  型パラメータを持ったクラスG、型パラメータAとBがあったとき、A = Bのときにのみ可能
  val : G[A] = G[B]

  ## 共変
  型パラメータを持ったクラスG、型パラメータAとBがあったとき、A が B を継承しているときにのみ可能
  val : G[B] = G[A]
  class G[+A]

  ## 反変
  型パラメータを持ったクラスG、型パラメータAとBがあったとき、A が B を継承しているときにのみ可能
  val : G[A] = G[B]
  class G[-A]

   */

  // val arr: Array[Any] = new Array[String](1) // コンパイルエラー
  class Pair[+A, +B](val a: A, val b: B) {
    override def toString(): String = "(" + a + "," + b + ")"
  }

  // 共変にしないとAnyRefとStringが=ではないのでエラーになる
  val pair: Pair[AnyRef, AnyRef] = new Pair[String, String]("foo", "bar")

  // practice
  trait Stack[+A] {
    // EはAの継承元
    def push[E >: A](e: E): Stack[E]

    def top: A

    def pop: Stack[A]

    def isEmpty: Boolean
  }

  class NonEmptyStack[+A](private val first: A, private val rest: Stack[A]) extends Stack[A] {
    def push[E >: A](e: E): Stack[E] = new NonEmptyStack[E](e, this)

    def top: A = first

    def pop: Stack[A] = rest

    def isEmpty: Boolean = false
  }

  case object EmptyStack extends Stack[Nothing] {
    def push[E >: Nothing](e: E): Stack[E] = new NonEmptyStack[E](e, this)

    def top: Nothing = throw new IllegalArgumentException("empty stack")

    def pop: Nothing = throw new IllegalArgumentException("empty stack")

    def isEmpty: Boolean = true
  }

  object Stack {
    def apply(): Stack[Nothing] = EmptyStack
  }

  /*
  # 型パラメータの境界
  ## 上限境界
  型パラメータがどのような型を継承しているかを指定する上限境界（upper bounds）です
  型パラメータの後に、<:を記述し、それに続いて制約となる型を記述します

  ## 下限境界
  型パラメータがどのような型のスーパータイプであるかを指定する下限境界（lower bounds）です
   */

  abstract class Show {
    def show: String
  }

  class ShowablePair[A <: Show, B <: Show](val a: A, val b: B) extends Show {
    // Showを継承してるので、showを呼び出し可能
    override def show: String = "(" + a.show + "," + b.show + ")"
  }

  abstract class Stack2[+A] {
    //    def push(element: A): Stack[A] // 共変な型パラメータAが反変な位置（反変な型パラメータが出現できる箇所）に出現したというエラー
    def push[E >: A](element: E): Stack[E]

    def top: A

    def pop: Stack2[A]

    def isEmpty: Boolean
  }

}
