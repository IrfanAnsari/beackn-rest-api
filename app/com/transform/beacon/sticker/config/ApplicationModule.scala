package com.transform.beacon.sticker.config

import com.softwaremill.macwire.Macwire
import com.transform.beacon.sticker.controllers.StickerController
import com.transform.beacon.sticker.daos.{MongoConnection, SingleMongoConnector, StickerDao}
import com.transform.beacon.sticker.services.StickerService


trait ApplicationModule extends Macwire{
  lazy val mongoConnection = new MongoConnection("heroku_app36253053", "o8ca91jonr7e6hcgc5ih22efeo","ds061238.mongolab.com", 61238, "heroku_app36253053")
  lazy val connector = wire[SingleMongoConnector]
  lazy val productDao = wire[StickerDao]
  lazy val productService= wire[StickerService]
  lazy val productController = wire[StickerController]

}
