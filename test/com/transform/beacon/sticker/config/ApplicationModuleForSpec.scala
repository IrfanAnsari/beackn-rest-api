package com.transform.beacon.sticker.config

import com.softwaremill.macwire.Macwire
import com.transform.beacon.sticker.controllers.StickerController
import com.transform.beacon.sticker.daos.{StickerDao, SingleMongoConnector, MongoConnection}
import com.transform.beacon.sticker.services.StickerService


trait ApplicationModuleForSpec extends Macwire {
  lazy val mongoConnection = new MongoConnection("localhost", 12345, "example_test")
  lazy val connector = wire[SingleMongoConnector]
  lazy val productDao = wire[StickerDao]
  lazy val productService= wire[StickerService]
  lazy val productController = wire[StickerController]

}
