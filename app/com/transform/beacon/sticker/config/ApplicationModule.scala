package com.transform.beacon.sticker.config

import com.softwaremill.macwire.Macwire
import com.transform.beacon.sticker.controllers.StickerController
import com.transform.beacon.sticker.daos.{MongoConnection, SingleMongoConnector, StickerDao}
import com.transform.beacon.sticker.services.StickerService

trait ApplicationModule extends Macwire{
  lazy val mongoConnection = new MongoConnection("localhost", 27017, "beacon-sticker-db")
  lazy val connector = wire[SingleMongoConnector]
  lazy val productDao = wire[StickerDao]
  lazy val productService= wire[StickerService]
  lazy val productController = wire[StickerController]

}
