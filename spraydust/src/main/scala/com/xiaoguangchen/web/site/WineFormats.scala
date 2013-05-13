package com.xiaoguangchen.web.site

import spray.json._
import spray.httpx.SprayJsonSupport
import com.xiaoguangchen.web.model.Wine


trait WineFormats extends DefaultJsonProtocol with SprayJsonSupport {

 // implicit val wineFormat = jsonFormat8(Wine)

  // I have to manually handle this instead of using jsonFormat8(Wine)
  // as for create the id is passed in as null. The case class Wine() doesn't like null value as it is expecting
  // a Number.

  implicit object wineFormat extends RootJsonFormat[Wine] {
    def write(w: Wine) =JsObject(
      "id" -> JsNumber(w.id),
      "name" -> JsString(w.name),
      "grapes" -> JsString(w.grapes),
      "country" -> JsString(w.country),
      "region" -> JsString(w.region),
      "year" -> JsString(w.year),
      "picture" -> JsString(w.picture),
      "description" -> JsString(w.description)
    )

    def read(value: JsValue) :Wine =  value.asJsObject.getFields("id", "name", "grapes", "country", "region", "year","picture", "description") match {
    case Seq(JsNumber(id),JsString(name), JsString(grapes), JsString(country), JsString(region), JsString(year), JsString(picture), JsString(description)) => {
      val wineId = if (id == null) 0 else id.toInt
      Wine(wineId, name, grapes , country, region, year, picture, description)
    }

      case _ => throw new DeserializationException("Color expected")
    }
  }

}