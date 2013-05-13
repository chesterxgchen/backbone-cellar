package com.tingatech.web.site

import spray.http._
import spray.routing.directives.{DirectoryListing, LogEntry}
import akka.event.Logging._
import spray.http.StatusCodes._
import spray.httpx.marshalling.Marshaller
import spray.http.HttpRequest
import spray.http.HttpResponse
import spray.http.ChunkedResponseStart
import scala.Some



/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/10/13
 */
trait CommonTrait {


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
/*
  implicit val ListingMarshaller: Marshaller[DirectoryListing] =
    Marshaller.delegate(MediaTypes.`text/html`) { (listing: DirectoryListing) ⇒
      listing.copy(
        files = listing.files.filterNot(file ⇒
          file.getName.startsWith(".") || file.getName.startsWith("archetype-catalog")))
    }(DirectoryListing.DefaultMarshaller)*/

}
