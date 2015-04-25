package com.transform.beacon.sticker.unit.services

import com.transform.beacon.sticker.daos.StickerDao
import com.transform.beacon.sticker.helpers.AwaitHelper
import com.transform.beacon.sticker.models.Sticker
import com.transform.beacon.sticker.services.StickerService
import org.scalatest.mock.MockitoSugar

import org.mockito.Mockito._

import scala.concurrent.Future
import org.scalatestplus.play.PlaySpec
import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest.BeforeAndAfter

class StickerServiceSpec extends PlaySpec with MockitoSugar with BeforeAndAfter with AwaitHelper {

  val mockedStickerDao: StickerDao = mock[StickerDao]

  val EXPECTED_TROLLEY_STICKER_ID: String = "trolley_id"
  val EXPECTED_PATIENT_STICKER_ID: String = "patient_id"

  val trolleySticker = Sticker(Some(EXPECTED_TROLLEY_STICKER_ID), "Trolley", "RSSI -90")
  val patientSticker = Sticker(Some(EXPECTED_PATIENT_STICKER_ID), "Patient", "RSSI -89")

  val stickerService: StickerService = new StickerService(mockedStickerDao)

  after {
    reset(mockedStickerDao)
  }

  "StickerService should" should {

    "create a document" in {
      when(mockedStickerDao.create(trolleySticker)).thenReturn(Future(EXPECTED_TROLLEY_STICKER_ID))

      val productId: String = await(stickerService.create(trolleySticker))

      productId mustBe EXPECTED_TROLLEY_STICKER_ID
      verify(mockedStickerDao).create(trolleySticker)
    }

    "update a document" in {
      when(mockedStickerDao.update(trolleySticker)).thenReturn(Future(true))

      val documentUpdated: Boolean = await(stickerService.update(trolleySticker))

      documentUpdated mustBe true
      verify(mockedStickerDao).update(trolleySticker)
    }

    "delete a document" in {
      when(mockedStickerDao.delete(EXPECTED_TROLLEY_STICKER_ID)).thenReturn(Future(true))

      val documentDeleted: Boolean = await(stickerService.delete(EXPECTED_TROLLEY_STICKER_ID))

      documentDeleted mustBe true
      verify(mockedStickerDao).delete(EXPECTED_TROLLEY_STICKER_ID)
    }

    "read a document" in {
      when(mockedStickerDao.read(EXPECTED_TROLLEY_STICKER_ID)).thenReturn(Future(Some(trolleySticker)))

      val sticker: Option[Sticker] = await(stickerService.read(EXPECTED_TROLLEY_STICKER_ID))

      sticker mustBe Some(trolleySticker)
      verify(mockedStickerDao).read(EXPECTED_TROLLEY_STICKER_ID)
    }

    "list all document" in {
      when(mockedStickerDao.listAll()).thenReturn(Future(Seq(trolleySticker, patientSticker)))

      val stickers: Seq[Sticker] = await(stickerService.listAll())

      stickers mustBe Seq(trolleySticker, patientSticker)
      verify(mockedStickerDao).listAll()
    }

  }

}
