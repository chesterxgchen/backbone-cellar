/*
 * Copyright (C) 2011-2013 spray.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tingatech.web.site

import akka.actor.{ ActorSystem, Props }
import akka.event.Logging
import akka.io.IO
import spray.can.Http


object Main extends App {
  implicit val system = ActorSystem()
  val log = Logging(system, getClass)
  val settings = SiteConfigs(system)

  log.info("Starting service actor and HTTP server...")
  val service = system.actorOf(Props(new SiteServiceActor(settings)), "site-service")
  IO(Http) ! Http.Bind(service, settings.host, settings.port)
}

