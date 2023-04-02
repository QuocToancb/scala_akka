package main.scala
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
import java.lang.Exception
import scala.language.postfixOps
object ActorSetup extends App {
  private val actorSys = ActorSystem("HelloActorAkka")
  println(actorSys)

  private val addActor = actorSys.actorOf(Props[AddActor], "addActor")
  println(addActor.path)

  println("Before")
  addActor!4
  addActor!1000
  addActor!6

  println("After")

  //Actor return
  println("\nStart actor return")
  private val addActorReturn = actorSys.actorOf(Props[AddActorReturn], "addActorReturn")
  implicit val timeout: Timeout = Timeout(10 seconds)
  private val futureResult = (addActorReturn ? 4).mapTo[Int]
  private val sumResult = Await.result(futureResult, 10 seconds)
  println(s"sumValue: $sumResult")

  println("Finish actor return")
  (addActorReturn ? 400)
  (addActorReturn ? 400)
  (addActorReturn ? 400)
  (addActorReturn ? 400)
  println("\nStart actor return 2")
  private val futureResult2 = (addActorReturn ? 1).mapTo[Int]
  private val sumResult2 = Await.result(futureResult2, 10 seconds)
  println(s"sumValue: $sumResult2")

  println("Finish actor return 2")

}


class AddActor extends Actor {
  private var sum = 0

  override def receive: Receive = {
    case x:Int => sum = sum +x
      println(s"Current sumValue: $sum added by $x")
    case _ => println("Error!! Bad Data")
  }
}

class AddActorReturn extends Actor {
  private var sum = 0

  override def receive: Receive = {
    case x:Int => sum = sum +x
      println(s"Current sumValue: $sum added by $x")
      sender!sum
    case _ => println("Error!! Bad Data")
  }
}