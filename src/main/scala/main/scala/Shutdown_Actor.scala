package main.scala
import akka.actor.{Props, ActorSystem, Actor, PoisonPill}
class Shutdown_Actor extends Actor {
  override def receive: Receive = {
    case msg: String => println(s"value is: $msg")
    case Stop =>
      println("self stop")
      context.stop(self)
  }
}

case object Stop

object Shutdown_Actor extends App {
  val actorSystem = ActorSystem("shutdownSystem")
  val shutdown_Actor = actorSystem.actorOf(Props[Shutdown_Actor], "shutdownActor")

  shutdown_Actor ! "this is value"
  shutdown_Actor ! PoisonPill //== shutdown_Actor ! Stop
//  shutdown_Actor ! Stop
  shutdown_Actor ! "value after shutdow"
//    [INFO] [04/02/2023 00:40:56.108]
  //    [shutdownSystem-akka.actor.default-dispatcher-5] [akka://shutdownSystem/user/shutdownActor]
  //    Message [java.lang.String] without sender to Actor[akka://shutdownSystem/user/shutdownActor#-661586202] was not delivered.
  //    [1] dead letters encountered.
  //    This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.

}