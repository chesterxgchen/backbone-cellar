package com.xiaoguangchen.web.site

import akka.actor.{ ActorSystem, Props }
import akka.event.Logging
import akka.io.IO
import spray.can.Http


object Main extends App {
  implicit val system = ActorSystem()
  val log = Logging(system, getClass)
  val settings = SprayDustConfigs(system)

  log.info("Starting service actor and HTTP server...")
  val service = system.actorOf(Props(new SprayDustActor(settings)), "site-service")
  IO(Http) ! Http.Bind(service, settings.host, settings.port)
}

