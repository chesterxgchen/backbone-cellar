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

import akka.event.Logging._

import spray.routing.directives.{ DirectoryListing, LogEntry }
import spray.httpx.encoding.Gzip
import spray.httpx.marshalling.Marshaller
import spray.http._
import spray.http.HttpRequest
import spray.http.HttpResponse
import spray.http.ChunkedResponseStart
import spray.http.HttpHeaders.RawHeader
import com.tingatech.web.example.{WineFormats, WineResources, Wine}
import spray.routing._
import scala.concurrent.Future
import spray.http.MediaTypes._
import scala._


//import html._
import StatusCodes._

class SiteServiceActor(settings: SiteConfigs) extends HttpServiceActor
                                              with CommonTrait
                                              with WineFormats
                                              with WineRoutes {

  def fromObjectCross(origin: String) = respondWithHeader(RawHeader("Access-Control-Allow-Origin", origin))

  // format: OFF
  def receive = runRoute {
     (get /*& encodeResponse(Gzip)*/) {
          logRequestResponse(showErrorResponses _) {
            getFromResourceDirectory("www") ~
            logRequest(showRequest _) {
              path("") {
                redirect(settings.apiBaseURL)
              } ~
              pathSuffixTest(Slash) {
                path("home") {
                  redirect(settings.apiBaseURL)
                } ~
                path("index") {
                  //complete(page(index()))
                  complete("{ \"hello\":\"world\" }")
                }
              } /*~
              unmatchedPath { ump =>
                println(" un matched " + ump.toString)
                redirect(ump.toString + "/")
              }*/
            }
          }
      } ~ wineRoute
  }
/*
  // format: ON

  def showRequest(request: HttpRequest) = LogEntry(request.uri, InfoLevel)

  def showErrorResponses(request: HttpRequest): Any ⇒ Option[LogEntry] = {
    case HttpResponse(OK, _, _, _)       ⇒ None
    case HttpResponse(NotFound, _, _, _) ⇒ Some(LogEntry("404: " + request.uri, WarningLevel))
    case response ⇒ Some(
      LogEntry("Non-200 response for\n  Request : " + request + "\n  Response: " + response, WarningLevel))
  }

  def showRepoResponses(repo: String)(request: HttpRequest): HttpResponsePart ⇒ Option[LogEntry] = {
    case HttpResponse(OK, _, _, _) ⇒ Some(LogEntry(repo + " 200: " + request.uri, InfoLevel))
    case ChunkedResponseStart(HttpResponse(OK, _, _, _)) ⇒ Some(LogEntry(repo + " 200 (chunked): " + request.uri, InfoLevel))
    case HttpResponse(NotFound, _, _, _) ⇒ Some(LogEntry(repo + " 404: " + request.uri))
    case _ ⇒ None
  }

  implicit val ListingMarshaller: Marshaller[DirectoryListing] =
    Marshaller.delegate(MediaTypes.`text/html`) { (listing: DirectoryListing) ⇒
      listing.copy(
        files = listing.files.filterNot(file ⇒
          file.getName.startsWith(".") || file.getName.startsWith("archetype-catalog")))
    }(DirectoryListing.DefaultMarshaller)*/

}