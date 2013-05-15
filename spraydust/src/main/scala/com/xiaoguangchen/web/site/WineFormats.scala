package com.xiaoguangchen.web.site

import spray.json._
import spray.httpx.SprayJsonSupport
import com.xiaoguangchen.web.model.Wine


trait WineFormats extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val wineFormat = jsonFormat8(Wine)
}