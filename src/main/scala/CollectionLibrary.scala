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
}
