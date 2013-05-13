package com.xiaoguangchen.web.site

import com.typesafe.config.{ Config, ConfigFactory }
import akka.actor.ActorSystem

case class SprayDustConfigs(host: String, port: Int,
                       apiHost: String, apiPort:Int) {

  require(host.nonEmpty, "host must be non-empty")
  require(apiHost.nonEmpty, "host must be non-empty")
  require(0 < port && port < 65536, "illegal port")
  require(0 < apiPort && apiPort < 65536, "illegal port")

  val apiBaseURL = s"http://$apiHost:$apiPort"
}

object SprayDustConfigs {
  def apply(system: ActorSystem): SprayDustConfigs =
    apply(system.settings.config getConfig "spraydust")

  def apply(config: Config): SprayDustConfigs = {
    val c = config withFallback ConfigFactory.defaultReference(getClass.getClassLoader)
    SprayDustConfigs( c getString "site.host",
                      c getInt    "site.port",
                      c getString "api.host",
                      c getInt    "api.port")
  }
}