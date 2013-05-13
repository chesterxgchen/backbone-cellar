package com.xiaoguangchen.web.site


import spray.routing._


class SprayDustActor(settings: SprayDustConfigs) extends HttpServiceActor
                                                 with CommonTrait
                                                 with WineFormats
                                                 with WineRoutes {
  def receive = runRoute {
    wineRoute
  }

}