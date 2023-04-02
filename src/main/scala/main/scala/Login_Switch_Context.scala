package main.scala

import akka.actor.{Actor, ActorSystem, Props}

object Login extends App {
  val actorSystem = ActorSystem("LoginSystem")
  val loginActor = actorSystem.actorOf(Props[LoginActor], "loginActor")

  loginActor ! "Bob"
  loginActor ! "UserName"
  loginActor ! "Logout"
}
class LoginActor  extends  Actor {
  override def receive: Receive = {

    case x: String => {
      println("receive")
      if (x == "Bob") {
        context.become(isAuth)
      }
    }
  }

  private def isAuth:Receive  = {
    case x:String => {
      println("isAuth")
      if(x == "Logout") {
        println("Logout")
        context.become(notAuth)
      }
      if (x == "UserName") {
        println("Bob")
      }
    }
  }

  private def notAuth: Receive = {
    case x: String => {
      println("notAuth")
      if(x == "Bob") {
        context.become(isAuth)
      }
    }
  }
}
