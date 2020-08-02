object FutureAndPromise {
  /*
  非同期プログラミングにおいて、終了しているかどうかわからない処理結果を抽象化した型です。
  Futureは未来の結果を表す型です。
  Promiseは一度だけ、成功あるいは失敗を表す、処理または値を設定することでFutureに変換できる型です
  ECMAScript 6のPromiseとScalaのPromiseは、全く異なる機能であるため注意が必要です
   */

  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Await
  import scala.concurrent.duration._

  object FutureSample extends App {

    val s = "Hello"
    // Futureシングルトンは関数を与えるとその関数を非同期に与えるFuture[+T]を返します
    val f: Future[String] = Future {
      Thread.sleep(1000)
      s + " future!"
    }

    f.foreach { case s: String =>
      println(s)
    }

    println(f.isCompleted) // false

    Thread.sleep(5000)

    println(f.isCompleted) // true

    val f2: Future[String] = Future {
      Thread.sleep(1000)
      throw new RuntimeException("わざと失敗")
    }

    f2.failed.foreach { case e: Throwable =>
      println(e.getMessage)
    }

    println(f2.isCompleted) // false

    Thread.sleep(5000)

    println(f2.isCompleted) // true

    /*
    false
    Hello future!
    true
    false
    わざと失敗
    true
     */
  }

  object FutureSampleThreadSleep extends App {

    val s = "Hello"
    val f: Future[String] = Future {
      Thread.sleep(1000)
      println(s"[ThreadName] In Future: ${Thread.currentThread.getName}") // 別スレッド
      s + " future!"
    }

    f.foreach { case s: String =>
      println(s"[ThreadName] In Success: ${Thread.currentThread.getName}")
      println(s)
    }

    // そのFuture自体の処理を待つ
    println(f.isCompleted) // false

    Await.ready(f, 5000.millisecond) // Hello future!

    println(s"[ThreadName] In App: ${Thread.currentThread.getName}") // メインスレッド
    println(f.isCompleted) // true

    /*
    false
    [ThreadName] In Future: ForkJoinPool-1-worker-5
    [ThreadName] In App: main
    true
    [ThreadName] In Success: ForkJoinPool-1-worker-5
    Hello future!
     */
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  import scala.util.{Failure, Random, Success}

  object FutureOptionUsageSample extends App {
    val random = new Random()
    val waitMaxMilliSec = 3000

    val futureMilliSec: Future[Int] = Future {
      val waitMilliSec = random.nextInt(waitMaxMilliSec)
      if (waitMilliSec < 1000) throw new RuntimeException(s"waitMilliSec is ${waitMilliSec}")
      Thread.sleep(waitMilliSec)
      waitMilliSec
    }

    val futureSec: Future[Double] = futureMilliSec.map(i => i.toDouble / 1000)

    futureSec onComplete {
      case Success(waitSec) => println(s"Success! ${waitSec} sec")
      case Failure(t) => println(s"Failure: ${t.getMessage}")
    }

    Thread.sleep(3000)
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  import scala.util.{Failure, Success, Random}

  object CompositeFutureSample extends App {
    val random = new Random()
    val waitMaxMilliSec = 3000

    def waitRandom(futureName: String): Int = {
      val waitMilliSec = random.nextInt(waitMaxMilliSec)
      if (waitMilliSec < 500) throw new RuntimeException(s"${futureName} waitMilliSec is ${waitMilliSec}")
      Thread.sleep(waitMilliSec)
      waitMilliSec
    }

    val futureFirst: Future[Int] = Future {
      waitRandom("first")
    }
    val futureSecond: Future[Int] = Future {
      waitRandom("second")
    }

    val compositeFuture: Future[(Int, Int)] = for {
      first <- futureFirst
      second <- futureSecond
    } yield (first, second)

    compositeFuture onComplete {
      case Success((first, second)) => println(s"Success! first:${first} second:${second}")
      case Failure(t) => println(s"Failure: ${t.getMessage}")
    }

    Thread.sleep(5000)
  }

  // promise
  // 「Promiseには成功/失敗した時の値を設定できる」「PromiseからFutureを作ることが出来る」という2つの性質を利用して、 callbackをFutureにする

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.{Await, Future, Promise}
  import scala.concurrent.duration._
  import scala.util.{Failure, Random, Success}

  class CallbackSomething {
    val random = new Random()

    def doSomething(onSuccess: Int => Unit, onFailure: Throwable => Unit): Unit = {
      val i = random.nextInt(10)
      if (i < 5) onSuccess(i) else onFailure(new RuntimeException(i.toString))
    }
  }

  class FutureSomething {
    val callbackSomething = new CallbackSomething

    def doSomething(): Future[Int] = {
      val promise = Promise[Int]
      callbackSomething.doSomething(i => promise.success(i), t => promise.failure(t))
      promise.future
    }
  }

  object CallbackFuture extends App {
    val futureSomething = new FutureSomething

    val iFuture = futureSomething.doSomething()
    val jFuture = futureSomething.doSomething()

    val iplusj = for {
      i <- iFuture
      j <- jFuture
    } yield i + j

    val result = Await.result(iplusj, Duration.Inf)
    println(result)
  }

}
