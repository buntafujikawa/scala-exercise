object CollectionLibrary {
  /*
  # Array
  要素が同じでもequalsの結果がtrueにならない, 生成する際にClassTagというものが必要 などのいくつかの罠があるので、Arrayはパフォーマンス上必要になる場合以外はあまり積極的に使うものではありません
   */
  val arr = Array[Int](1, 2, 3)
  arr(0) = 7 // []じゃないんで注意
  arr.length

  val arr2 = Array(1, 2, "a") // Array[Any]

  // practice
  def swapArray[T](arr: Array[T])(i: Int, j: Int): Unit = {
    val tmp = arr(i)
    arr(i) = arr(j)
    arr(j) = tmp
  }

  /*
  # Range
   */
  1 to 5 // range
  (1 to 5).toList

  /*
  # List
  一度作成したら中身を変更できない（immutable）

  ## Nil
  からのListで単にobject

  :: コンスと読む
   */
  val lst = List(1, 2, 3)
  1 :: Nil
  Nil.::(1)
  //  lst(0) = 7

  // 連結
  List(1, 2) ++ List(3, 4)
  List(1, 2).++(List(3, 4))

  // 文字列
  List(1, 2, 3, 4).mkString
  List(1, 2, 3, 4).mkString(",")
  List(1, 2, 3, 4).mkString("[", ",", "]")

  // practice
  def joinByComma(start: Int, end: Int): String = {
    (start to end).mkString(",")
  }

  List(1, 2, 3).foldLeft(0)((x, y) => x + y)
  List(List(1), List(2, 3), List(4)).foldLeft(Nil: List[Int])(_ ++ _)

  // practice
  def reverse[T](list: List[T]): List[T] = {
    list.foldLeft(Nil: List[T])((x, y) => y :: x)
  }

  def sum(list: List[Int]): Int = {
    list.foldRight(0)((x, y) => x + y)
  }

  def mul(list: List[Int]): Int = {
    list.foldRight(1)((x, y) => x * y)
  }

  def mkString[T](list: List[T]) (sep: String): String = list match {
    case Nil => ""
    case x::xs => xs.foldLeft(x.toString()){(x, y) => x + sep + y}
  }

  def mkString[T](list: List[T])(sep: String): String = list match {
    case Nil => ""
    case x::xs => xs.foldLeft(x.toString){(x, y) => x + sep + y}
  }

  List(1,2,3,4,5).map(x => x * 2)

  def map[T, U](list: List[T])(f: T => U): List[U] = {
    list.foldLeft(Nil:List[U]){(x,y) => f(y) :: x}.reverse
  }
  def map2[T, U](list: List[T])(f: T => U): List[U] = {
    list.foldLeft(Nil:List[U]){(x,y) => f(y) :: x}
  }
  assert(List(2,3,4) == map(List(1,2,3))(x => x + 1))

  def filter[T](list: List[T])(f: T => Boolean): List[T] = {
    list.foldLeft(Nil:List[T]){(x,y) => if(f(y)) y::x else x}.reverse
  }

  def find[T](list: List[T])(f: T => Boolean): Option[T] = list match {
    case x::xs if f(x) => Some(x)
    case x::xs => find(xs)(f)
    case _ => None
  }

  def find[T](list: List[T])(f: T => Boolean): Option[T] = {
    list.foldLeft(None){(x, y) =>
      if (f(y)) return Some(y)
      None
    }
  }
  assert(Some(2) == find(List(1, 2, 3))(x => x == 2))
  assert(None == find(List(1, 2, 3))(x => x > 3))
  assert(Some(1) == find(List(1))(x => x == 1))
  assert(None == find(List(1))(x => false))
  assert(None == find(List[Int]())(x => x == 1))

  def takeWhile[T](list: List[T])(f: T => Boolean): List[T] = {
    list.foldLeft(Nil:List[T]){(x, y) => if(f(y)) y::x else x }.reverse
  }
  assert(List(1, 2, 3) == takeWhile(List(1, 2, 3, 4, 5))(x => x <= 3))
  assert(List(1) == takeWhile(List(1, 2, 3, 3, 4, 5))(x => x == 1))
  assert(List(1, 2, 3, 4)  == takeWhile(List(1, 2, 3, 4, 5))(x => x < 5))
  assert(Nil == takeWhile(List(1, 2, 3, 3, 2, 2))(x => false))

  def count[T](list: List[T])(f: T => Boolean): Int = {
    list.foldLeft(0){(x,y) => if(f(y)) x + 1 else x }
  }
  assert(3 == count(List(1, 2, 3, 3, 2, 2))(x => x == 2))
  assert(1 == count(List(1, 2, 3, 3, 2, 2))(x => x == 1))
  assert(2 == count(List(1, 2, 3, 3, 2, 2))(x => x == 3))
  assert(0 == count(List(1, 2, 3, 3, 2, 2))(x => x == 5))

  def flatMap[T, U](list: List[T])(f: T => List[U]): List[U] = list match {
    case Nil => Nil
    case x::xs => f(x) ::: flatMap(xs)(f)
  }
  assert(List(1, 2, 3) == flatMap(List(1, 2, 3))(x => List(x)))
  assert(
    List(3, 4, 6, 8) == flatMap(List(1, 2))(x =>
      map(List(3, 4))(y => x * y)
    )
  )

  5 :: List(1, 2, 3, 4)
  List(1, 2, 3, 4) :+ 5 // 末尾への追加は、Listの要素数分かかる

  /*
  # vector
  Vectorは一度データ構造を構築したら変更できないimmutableなデータ構造
   */
  Vector(1, 2, 3, 4, 5) //どの操作も「ほぼ」一定の時間で終わる
  6 +: Vector(1, 2, 3, 4, 5)
  Vector(1, 2, 3, 4, 5) :+ 6
  Vector(1, 2, 3, 4, 5).updated(2, 5) // Vector(1, 2, 5, 4, 5)

  /*
  # Map (連想配列)
  Mapと書いた場合、scala.collection.immutable.Mapが使われます。その名の通り、一度作成すると変更することはできません
   */
  val m = Map("A" -> 1, "B" -> 2, "C" -> 3)
  m.updated("B", 4)
  m // そのまま

  import scala.collection.mutable

  val mutableMap = mutable.Map("A" -> 1, "B" -> 2, "C" -> 3)
  mutableMap("B") = 5
  m

  /*
  # Set
  値の集合を提供するデータ構造です。Setの中では同じ値が2つ以上存在しません
   */

  val s = Set(1, 1, 2, 3, 4) // Set(1, 2, 3, 4)
  s - 4
  s

  val mutableSet = mutable.Set(1, 2, 3, 4, 5)
  mutableSet -= 5
  mutableSet
}
