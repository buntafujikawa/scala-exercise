object PatterMatching {
  // ケースクラスのスーパークラス/トレイトにはsealedを付けるものだと覚えておけば良いでしょう
  sealed abstract class DayOfWeek
  case object Sunday extends DayOfWeek
  case object Monday extends DayOfWeek
  case object Tuesday extends DayOfWeek
  case object Wednesday extends DayOfWeek
  case object Thursday extends DayOfWeek
  case object Friday extends DayOfWeek
  case object Saturday extends DayOfWeek

//  x match {
//    case Sunday => 1
//  }

  sealed abstract class Exp
  case class Add(lhs: Exp, rhs: Exp) extends Exp
  case class Sub(lhs: Exp, rhs: Exp) extends Exp
  case class Mul(lhs: Exp, rhs: Exp) extends Exp
  case class Div(lhs: Exp, rhs: Exp) extends Exp
  case class Lit(value: Int) extends Exp

  val example = Add(Lit(1), Div(Mul(Lit(2), Lit(3)), Lit(2)))
  def eval(exp: Exp): Int = exp match {
    case Add(l, r) => eval(l) + eval(r)
    case Sub(l, r) => eval(l) - eval(r)
    case Mul(l, r) => eval(l) * eval(r)
    case Div(l, r) => eval(l) / eval(r)
    case Lit(v) => v
  }
  eval(example)

  case class Point(x: Int, y: Int)
  val Point(x, y) = Point(10, 20)
  Point(1, 2) == Point(1, 2)

  // practice
  def nextDayOfWeek(d: DayOfWeek): DayOfWeek = d match {
    case Sunday =>  Monday
    case Monday => Tuesday
    case Tuesday => Wednesday
    case Wednesday => Thursday
    case Thursday => Friday
    case Friday => Saturday
    case Saturday => Sunday
  }

  sealed abstract class Tree
  case class Branch(value: Int, left: Tree, right: Tree) extends Tree
  case object Empty extends Tree

  val tree: Tree = Branch(1, Branch(2, Empty, Empty), Branch(3, Empty, Empty))

  def max(tree: Tree): Int = tree match {
    case Branch(value, Empty, Empty) => value
    case Branch(value, Empty, right) =>
      val rightValue = max(right)
      if (rightValue > value) rightValue else value
    case Branch(value, left, Empty) =>
      val leftValue = max(left)
      if (leftValue > value) leftValue else value
    case Branch(value, left, right) =>
      val rightValue = max(right)
      val leftValue = max(left)
      if (rightValue > leftValue) {
        if (value > rightValue) value else rightValue
      }  else {
        if (value > leftValue) value else leftValue
      }
  }
  def min(tree: Tree): Int = tree match {
    case Branch(value, Empty, Empty) => value
    case Branch(value, Empty, right) =>
      val rightValue = min(right)
      if (rightValue < value) rightValue else value
    case Branch(value, left, Empty) =>
      val leftValue = min(left)
      if (leftValue < value) leftValue else value
    case Branch(value, left, right) =>
      val rightValue = min(right)
      val leftValue = min(left)
      if (rightValue < leftValue) {
        if (value < rightValue) value else rightValue
      }  else {
        if (value < leftValue) value else leftValue
      }
  }
  def depth(tree: Tree): Int = tree match {
    case Empty => 0
    case Branch(_, left, right) =>
      val leftValue = depth(left)
      val rightValue = depth(right)
      (if(leftValue < rightValue) rightValue else leftValue) + 1
  }

  List(1,2,3,4,5).collect { case i if i % 2 == 1 => i * 2}
}
