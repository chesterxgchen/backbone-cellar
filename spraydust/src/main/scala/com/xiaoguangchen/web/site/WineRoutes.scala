package com.xiaoguangchen.web.site

import spray.routing._
import spray.http.MediaTypes._
import scala._
import spray.httpx.encoding.Gzip
import spray.http.HttpHeaders.RawHeader
import com.xiaoguangchen.web.model.{WineResources, Wine}

/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/10/13
 */
trait WineRoutes  extends HttpService  {
  this: WineFormats with CommonTrait =>

  //CORS support
  def fromObjectCross(origin: String) = respondWithHeader(RawHeader("Access-Control-Allow-Origin", origin))

  def wineRoute =  {
    logRequestResponse(showErrorResponses _) {
      fromObjectCross("*") {
        put  {
          pathPrefix("wines" / IntNumber) { id: Int =>
            respondWithMediaType(`application/json`) {
              entity(as[Wine])  { wine: Wine =>
                complete {
                  WineResources.update(wine)
                }
              }
            }
          }
        } ~
          post  {
            path("wines") {
              respondWithMediaType(`application/json`) {
                entity(as[Wine])  { wine: Wine =>
                  complete {
                    WineResources.create(wine)
                  }
                }
              }
            }
          } ~
          delete  {
            pathPrefix("wines" / IntNumber) { id: Int =>
            //complete ( WineResources.remove(id) )
              ctx =>  WineResources.remove(id)
            }
          } ~
          (get & encodeResponse(Gzip)) {
            getFromResourceDirectory("www/sample") ~
              path("wines") {
                respondWithMediaType(`application/json`) {
                  complete(WineResources.findAll)
                }
              } ~
              pathPrefix("wines"/"search" / Rest) {  rest:String =>
                respondWithMediaType(`application/json`) {
                  complete(WineResources.findByName(stripTailingSlash(rest)))
                }
              } ~
              pathPrefix("wines" / IntNumber) { id: Int =>
                respondWithMediaType(`application/json`) {
                  complete(WineResources.findById(id))
                }
              }
          }
      }
    }
  }


  def stripTailingSlash(rest: String): String = {
    val name = if (rest.endsWith("/")) rest.stripSuffix("/") else rest
    name
  }
}
