package com.transform.beacon.sticker.unit.controllers

import com.transform.beacon.sticker.controllers.StickerController
import com.transform.beacon.sticker.models.Sticker
import com.transform.beacon.sticker.services.StickerService
import org.mockito.Mockito._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.libs.json.Json
import org.scalatest.mock.MockitoSugar
import play.api.mvc.Results

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import play.api.mvc.Result

class StickerControllerSpec extends PlaySpec with BeforeAndAfter with MockitoSugar with Results with OneAppPerSuite {

  val mockStickerService = mock[StickerService]
  val trolleySticker = Sticker(Some("trolley_id"), "Trolley", "RSSI -92")
  val patientSticker = Sticker(Some("patient_id"), "James Bond", "RSSI -98")

  val stickerController: StickerController = new StickerController(mockStickerService)

  after {
    reset(mockStickerService)
  }

  "The sticker controller" should {

     "retrieve the resource for an existing id" in {
      //Given
      when(mockStickerService.read(trolleySticker._id.get)).thenReturn(Future(Some(trolleySticker)))
      //When
      val response: Future[Result] = stickerController.get("trolley_id").apply(FakeRequest())
      //Then
      status(response) mustEqual OK
      contentType(response) mustEqual Some("application/json")
      charset(response) mustEqual Some("utf-8")
      contentAsString(response) must equal(Json.toJson(trolleySticker).toString())
    }

     "not retrieve the resource for non existent id" in {
      //Given
      when(mockStickerService.read("non_existent_id")).thenReturn(Future(None))
      val id: String = "non_existent_id"
      //When
      val response: Future[Result] = stickerController.get(id).apply(FakeRequest())
      //Then
      status(response) mustEqual NOT_FOUND
      //contentType(response) mustEqual Some("application/json")
      charset(response) mustEqual Some("utf-8")
      contentAsString(response) must be("product with non_existent_id was not found")
      verify(mockStickerService).read(id)
    }

    "create a resource" in {
      //Given
      val expectedSticker = Sticker(Some("trolley_id"), "Trolley", "RSSI -90")
      val jsonRequestBody = Json.toJson(Map("_id" -> "trolley_id", "_type" -> "Trolley", "rssi" -> "RSSI -90"))
      when(mockStickerService.create(expectedSticker)).thenReturn(Future("test_id"))
      val request = FakeRequest(POST, "/stickers").withJsonBody(jsonRequestBody)
      //When
      val response: Future[Result] = stickerController.create().apply(request)
      //Then
      status(response) mustEqual CREATED
      contentAsString(response) must be("")
      header("Location", response) must equal(Some("/stickers/test_id"))
      verify(mockStickerService).create(expectedSticker)
    }

    "update a resource for a given id" in {
      //Given
      when(mockStickerService.update(trolleySticker)).thenReturn(Future(true))
      val request = FakeRequest(PUT, s"/stickers/${trolleySticker._id.get}").withJsonBody(Json.toJson(trolleySticker))
      //When
      val response = stickerController.update(trolleySticker._id.get).apply(request)
      //Then
      status(response) mustEqual NO_CONTENT
      verify(mockStickerService).update(trolleySticker)
    }

    "delete a resource for a given id" in {
      //Given
      when(mockStickerService.delete(trolleySticker._id.get)).thenReturn(Future(true))
      val request = FakeRequest(DELETE, s"/stickers/${trolleySticker._id.get}")
      //When
      val response = stickerController.delete(trolleySticker._id.get).apply(request)
      //Then
      status(response) mustEqual NO_CONTENT
      verify(mockStickerService).delete(trolleySticker._id.get)
    }

    "list all the resources" in {
      //Given
      val stickers: Seq[Sticker] = Seq(trolleySticker, patientSticker)
      when(mockStickerService.listAll()).thenReturn(Future(stickers))
      val request = FakeRequest(GET, "/stickers")
      //When
      val response = stickerController.listAll().apply(request)
      //Then
      status(response) mustEqual OK
      contentAsJson(response) mustEqual Json.toJson(stickers.toList)
      verify(mockStickerService).listAll()
    }
  }


}
