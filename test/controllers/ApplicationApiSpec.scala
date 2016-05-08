package controllers

import play.api._
import play.api.test._
import org.specs2.specification._
import org.specs2.mutable._
import play.api.libs.json.{JsObject, JsString}

/**
  * Created by gabriel on 5/8/16.
  */
class ApplicationApiSpec extends PlaySpecification {

  "return a 404 GET non-existent resource" in {
    running(FakeApplication()) {
      val home = route(FakeRequest(GET, "/uber"))
      home must beNone
    }
  }

  "render city map" in new WithApplication {
    val home = route(FakeRequest(GET, "/state")).get
    status(home) must equalTo(OK)
    contentType(home) must beSome.which(_ == "text/html")
  }
}
