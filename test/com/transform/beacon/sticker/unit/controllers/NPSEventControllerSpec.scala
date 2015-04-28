package com.transform.beacon.sticker.unit.controllers

import com.transform.beacon.sticker.controllers.NPSEventController
import com.transform.beacon.sticker.models.{NPSLocatable, NPSEvent}
import com.transform.beacon.sticker.services.NPSEventService
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.libs.json.Json
import play.api.mvc.{Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import scala.concurrent.ExecutionContext.Implicits.global


import scala.concurrent.Future

class NPSEventControllerSpec  extends PlaySpec with BeforeAndAfter with MockitoSugar with Results with OneAppPerSuite {

  val mockNPSEventService = mock[NPSEventService]

  val npsEventController : NPSEventController = new NPSEventController(mockNPSEventService)

  after {
    reset(mockNPSEventService)
  }

  "The sticker controller" should {
    "retrieve the resource for an existing id with empty NPSLocatables" in {
      //Given
      val npsEvent = NPSEvent(_id=Some("event_id"), identifier =  "Event", npsLocatables =List())
      when(mockNPSEventService.read(npsEvent._id.get)).thenReturn(Future(Some(npsEvent)))
      //When
      val response: Future[Result] = npsEventController.get("event_id").apply(FakeRequest())
      //Then
      status(response) mustEqual OK
      contentType(response) mustEqual Some("application/json")
      charset(response) mustEqual Some("utf-8")
      contentAsString(response) must equal(Json.toJson(npsEvent).toString())
    }

    "retrieve the resource for an existing id with NPSLocatables" in {
      //Given
      val npsLocatables1 = NPSLocatable(_id = Some("locatable_id1"), identifier = "Locatable 1", rssi = 90, _type = "Locatable 1")
      val npsLocatables2 = NPSLocatable(_id = Some("locatable_id2"), identifier = "Locatable 2", rssi = 80, _type = "Locatable 2")
      val npsEvent = NPSEvent(_id=Some("event_id"), identifier =  "Event", npsLocatables =List(npsLocatables1, npsLocatables2))
      when(mockNPSEventService.read(npsEvent._id.get)).thenReturn(Future(Some(npsEvent)))
      //When
      val response: Future[Result] = npsEventController.get("event_id").apply(FakeRequest())
      //Then
      status(response) mustEqual OK
      contentType(response) mustEqual Some("application/json")
      charset(response) mustEqual Some("utf-8")
      contentAsString(response) must equal(Json.toJson(npsEvent).toString())
    }
  }
}
