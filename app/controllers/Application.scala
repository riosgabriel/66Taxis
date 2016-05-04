package controllers

import play.api.libs.json._
import play.api.mvc._
import models.{Passenger, Taxi}

object Application extends Controller {

  def addTaxi = Action(BodyParsers.parse.json) { request =>
    ???
  }

  def addPassenger = Action(BodyParsers.parse.json) { request =>
    ???
  }

  def addTime() = Action { request =>
    ???
  }

  def restart = Action { request =>
    ???
  }

  def state = Action { request =>
    ???
  }

//  def listBooks = Action {
//    Ok(Json.toJson(books))
//  }
//
//  def saveBook = Action(BodyParsers.parse.json) { request =>
//    val b = request.body.validate[Book]
//    b.fold(
//      errors => {
//        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
//      },
//      book => {
//        addBook(book)
//        Ok(Json.obj("status" -> "OK"))
//      }
//    )
//  }
}
