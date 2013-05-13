package com.tingatech.web.example


//import org.json4s.{NoTypeHints, jackson}
import spray.json._
import spray.httpx.SprayJsonSupport


/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/8/13
 *//*
object WineMarshalling {


  import jackson.Serialization.{read => sread, write => swrite}
  implicit val formats = jackson.Serialization.formats(NoTypeHints)

  def write(wine: Wine) :String = swrite(wine)
  def read(json: String): Wine  = sread[Wine](json)
  def write(wines: List[Wine]) : String = swrite(wines)


}

*/
trait WineFormats extends DefaultJsonProtocol with SprayJsonSupport {

 // implicit val wineFormat = jsonFormat8(Wine)

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

    def read(value: JsValue) :Wine =  value.asJsObject.getFields("name", "grapes", "country", "region", "year","picture", "description") match {
    case Seq(JsString(name), JsString(grapes), JsString(country), JsString(region), JsString(year), JsString(picture), JsString(description)) =>
         Wine(0, name, grapes , country, region, year, picture, description)
      case _ => throw new DeserializationException("Color expected")
    }
  }

}