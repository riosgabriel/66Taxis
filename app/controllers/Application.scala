package controllers

import play.api.libs.json._
import play.api.mvc._
import models.{City, Passenger, Taxi}

object Application extends Controller {

  def addTaxi = Action(BodyParsers.parse.json) { implicit request =>
    val x = (request.body \ "taxi" \ "x").as[Int]
    val y = (request.body \ "taxi" \ "y").as[Int]

    City.addTaxi(Taxi((x, y)))

    Ok
  }

  def addPassenger = Action(BodyParsers.parse.json) { implicit request =>
    val p = request.body.validate[Passenger]

    p.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      passenger => {
        City.addPassenger(passenger)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }

  def doStep() = Action { request =>
    City.moveStep()
    Ok
  }

  def restart = Action { request =>
    City.restart
    Ok
  }

  def state = Action { request =>
    Ok(views.html.city(City.renderHtml)).as(HTML)
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
