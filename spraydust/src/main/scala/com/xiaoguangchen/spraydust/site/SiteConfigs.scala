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

import com.typesafe.config.{ Config, ConfigFactory }
import akka.actor.ActorSystem

case class SiteConfigs(host: String, port: Int,
                       apiHost: String, apiPort:Int) {
  require(host.nonEmpty, "host must be non-empty")
  require(apiHost.nonEmpty, "host must be non-empty")
  require(0 < port && port < 65536, "illegal port")
  require(0 < apiPort && apiPort < 65536, "illegal port")

  require (port != apiPort, "Rest Service API port and site port must be different")

  val apiBaseURL = s"http://$apiHost:$apiPort"
}

object SiteConfigs {
  def apply(system: ActorSystem): SiteConfigs =
    apply(system.settings.config getConfig "tinga")

  def apply(config: Config): SiteConfigs = {
    val c = config withFallback ConfigFactory.defaultReference(getClass.getClassLoader)
    SiteConfigs( c getString "site.host",
                 c getInt    "site.port",
                 c getString "api.host",
                 c getInt    "api.port")
  }
}