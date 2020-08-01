object ImplicitConversion {
  /*
  # Implicit Conversion
  暗黙の型変換機能をユーザが定義できるようにする機能
   */
  // 新しく定義したユーザ定義の型などを既存の型に当てはめたい場合 (この例の使い方はよくない)
  implicit def intToBoolean(arg: Int): Boolean = arg != 0
  if(1) {
    println("1はtrue")
  }

  // pimp my libraryパターンと呼ばれ、既存のクラスにメソッドを追加して拡張する（ようにみせかける）使い方
  // 1 to 5がまさにこれ
  class RichString(val src: String) {
    def smile: String = src + ":-)"
  }

  implicit def enrichString(arg: String): RichString = new RichString(arg)
  "Hi,".smile // val res78: String = Hi,:-)

  implicit class RichString2(val src: String) {
    def smile2: String = src + ":-)"
  }
  "Hi,".smile2

  implicit def stringToBoolean(arg: String): Boolean = arg.length > 0

  /*
  # Implicit Parameter
  2つの目的で使われます
  1. あちこちのメソッドに共通で引き渡されるオブジェクト（たとえば、ソケットやデータベースのコネクションなど）を明示的に引き渡すのを省略するために使うもの
  2.
   */

//  def useDatabase1(...., conn: Connection)
//  def useDatabase2(...., conn: Connection)
//  def useDatabase3(...., conn: Connection)
//
//  def useDatabase1(....)(implicit conn: Connection)
//  def useDatabase2(....)(implicit conn: Connection)
//  def useDatabase3(....)(implicit conn: Connection)
//
//  implicit val connection: Connection = connectDatabase(....)

  trait Additive[A] {
    def plus(a: A, b: A): A
    def zero: A
  }

  implicit object StringAdditive extends Additive[String] {
    def plus(a: String, b: String): String = a + b
    def zero: String = ""
  }

  implicit object IntAdditive extends Additive[Int] {
    def plus(a: Int, b: Int): Int = a + b
    def zero: Int = 0
  }

  def sum[A](lst: List[A])(implicit m: Additive[A]) = lst.foldLeft(m.zero)((x, y) => m.plus(x, y))

  sum(List(1, 2, 3))
  sum(List("A", "B", "C"))
}
