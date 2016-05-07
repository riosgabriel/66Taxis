package controllers

import play.api.libs.json._
import play.api.mvc._
import models.{City, Passenger, Taxi}

object Application extends Controller {

  def addTaxi = Action(BodyParsers.parse.json) { implicit request =>
    val p = request.body.validate[Taxi]

    p.fold(
      errors => {
        BadRequest(Json.obj("status" -> "BadRequest", "message" -> JsError.toFlatJson(errors)))
      },
      taxi => {
        City.addTaxi(taxi) match {
          case Left(msg) => BadRequest(Json.obj("status" -> "BadRequest", "message" -> msg))
          case Right(msg) => Ok(Json.obj("status" -> "OK"))
        }
      }
    )
  }

  def addPassenger = Action(BodyParsers.parse.json) { implicit request =>
    val p = request.body.validate[Passenger]

    p.fold(
      errors => {
        BadRequest(Json.obj("status" -> "BadRequest", "message" -> JsError.toFlatJson(errors)))
      },
      passenger => {
        City.addPassenger(passenger) match {
          case Left(msg) => BadRequest(Json.obj("status" -> "BadRequest", "message" -> msg))
          case Right(msg) => Ok(Json.obj("status" -> "OK"))
        }
      }
    )
  }

  def doStep() = Action { request =>
    City.moveStep()
    Ok(Json.obj("status" -> "OK"))
  }

  def restart = Action { request =>
    City.restart
    Ok(Json.obj("status" -> "OK"))
  }

  def state = Action { request =>
    request match {
      case Accepts.Json() => Ok(views.html.city(City.renderHtml)).as(HTML)
      case Accepts.Html() => Ok(views.html.city(City.renderHtml)).as(HTML)
      case _ => Ok(views.html.city(City.renderHtml)).as(HTML)
    }
  }
}
