object ErrorHandling {
  /*
  # Option
  Some, None
   */
  val o: Option[String] = Option("hoge")
  o.get
  o.isEmpty
  o.getOrElse("default")
  o.getOrElse(throw new RuntimeException("nullは受け入れられません"))

  val s: Option[String] = Some("hoge")
  val result = s match {
    case Some(str) => str
    case None => "not matched"
  }

  Some(3).map(_ * 3)
  val n: Option[Int] = None
  n.map(_ * 3) // Noneになるので判定する必要がない

  val v1: Option[Int] = Some(3)
  val v2: Option[Int] = Some(5)

  // val res72: Option[Option[Int]] = Some(Some(15))
  // val res73: Option[Int] = Some(15)
  v1.map(i1 => v2.map(i2 => i1 * i2)).flatten

  // practice

  val pv1: Option[Int] = Some(2)
  val pv2: Option[Int] = Some(3)
  val pv3: Option[Int] = Some(5)
  val pv4: Option[Int] = Some(7)
  val pv5: Option[Int] = Some(11)

  pv1.map(i1 =>
    pv2.map(i2 =>
      pv3.map(i3 =>
        pv4.map(i4 =>
          pv5.map(i5 =>
            i1 * i2 * i3 * i4 * i5
          )
        ).flatten
      ).flatten
    ).flatten
  ).flatten

  v1.flatMap(i1 => v2.map(i2 => i1 * i2))

  pv1.flatMap(i1 =>
    pv2.flatMap(i2 =>
      pv3.flatMap(i3 =>
        pv4.flatMap(i4 =>
          pv5.map(i5 =>
            i1 * i2 * i3 * i4 * i5
          )
        )
      )
    )
  )

  // forの方がシンプルにかける
  for { i1 <- pv1
    i2 <- pv2
    i3 <- pv3 } yield i1 * i2 * i3

  // practice
  for { i1 <- pv1
        i2 <- pv2
        i3 <- pv3
        i4 <- pv4
        i5 <- pv5 } yield i1 * i2 * i3 * i4 * i5

  /*
  # Either
  エラー時にエラーの種類まで取得できるのがEither
  OptionではSomeとNoneの2つの値を持ちましたが、EitherはRightとLeftの2つの値を持ちます

  一般的にEitherを使う場合、Left値をエラー値、Right値を正常な値とみなすことが多いです
   */
  val ev1: Either[String, Int] = Right(123)
  val ev2: Either[String, Int] = Left("abc")

  ev1 match {
    case Right(i) => println(i)
    case Left(s) => println(s)
  }

  sealed trait LoginError
  case object InvalidPassword extends LoginError
  case object UserNotFound extends LoginError
  case object PasswordLocked extends LoginError

  case class User(id: Long, name: String, password: String)
  object LoginService {
    def login(name: String, password: String): Either[LoginError, User] = ???
  }

  LoginService.login(name = "dwango", password = "password") match {
    case Right(user) => println(s"id: ${user.id}")
    case Left(InvalidPassword) => println(s"Invalid Password!")
  }

  ev1.map(_ * 2)
  ev2.map(_ * 2)

  /*
  # 名前渡しパラメータ
  名前渡しパラメータを使うと、 変数が実際に使用される箇所まで評価を遅延させる ことができます
  引数の式が例外を投げるかもしれないので、try-finally構文の中で引数を評価したい
  引数の式がものすごく計算コストが高いかもしれないが、計算結果を本当に使うかわからない。使われる箇所で計算させたい
   */
//  def f(x: Any): Unit = println("f")
//  def g(): Unit = println("g")
//  f(g())

  def g(): Unit = println("g")
  def f(g: => Unit): Unit = {
    println("prologue f")
    g
    println("epilogue f")
  }
  f(g())

  /*
  # Try
  Eitherと同じように正常な値とエラー値のどちらかを表現するデータ型
  Success/Failure

  使い分け
  基本的にJavaでnullを使うような場面はOptionを使う
  Optionを使うのでは情報が不足しており、かつ、エラー状態が代数的データ型としてちゃんと定められるものにはEitherを使う
  TryはJavaの例外をどうしても値として扱いたい場合に用いる
   */
  import scala.util.Try
  val v: Try[Int] = Try(throw new RuntimeException("to be caught"))

  val t1 = Try(3) // val t1: scala.util.Try[Int] = Success(3)
  val t2 = Try(5)
  val t3 = Try(7)

  for {
    i1 <- t1
    i2 <- t2
    i3 <- t3
  } yield i1 * i2 * i3 // val res77: scala.util.Try[Int] = Success(105)

  import scala.util.control.NonFatal
  try {
    ???
  } catch {
    case NonFatal(e) => ??? // 例外の処理
  }

  object MainRefactored {

    case class Address(id: Int, name: String, postalCode: Option[String])
    case class User(id: Int, name: String, addressId: Option[Int])

    val userDatabase: Map[Int, User] = Map (
      1 -> User(1, "太郎", Some(1)),
      2 -> User(2, "二郎", Some(2)),
      3 -> User(3, "プー太郎", None)
    )

    val addressDatabase: Map[Int, Address] = Map (
      1 -> Address(1, "渋谷", Some("150-0002")),
      2 -> Address(2, "国際宇宙ステーション", None)
    )

    sealed abstract class PostalCodeResult
    case class Success(postalCode: String) extends PostalCodeResult
    abstract class Failure extends PostalCodeResult
    case object UserNotFound extends Failure
    case object UserNotHasAddress extends Failure
    case object AddressNotFound extends Failure
    case object AddressNotHasPostalCode extends Failure

    // 本質的に何をしているかわかりやすくリファクタリング
    def getPostalCodeResult(userId: Int): PostalCodeResult = {
      (for {
        user <- findUser(userId)
        address <- findAddress(user)
        postalCode <- findPostalCode(address)
      } yield Success(postalCode)).merge
    }

    def findUser(userId: Int): Either[Failure, User] = {
      userDatabase.get(userId).toRight(UserNotFound)
    }

    def findAddress(user: User): Either[Failure, Address] = {
      for {
        addressId <- user.addressId.toRight(UserNotHasAddress)
        address <- addressDatabase.get(addressId).toRight(AddressNotFound)
      } yield address
    }

    def findPostalCode(address: Address): Either[Failure, String] = {
      address.postalCode.toRight(AddressNotHasPostalCode)
    }

    def main(args: Array[String]): Unit = {
      println(getPostalCodeResult(1)) // Success(150-0002)
      println(getPostalCodeResult(2)) // AddressNotHasPostalCode
      println(getPostalCodeResult(3)) // UserNotHasAddress
      println(getPostalCodeResult(4)) // UserNotFound
    }
  }
}
