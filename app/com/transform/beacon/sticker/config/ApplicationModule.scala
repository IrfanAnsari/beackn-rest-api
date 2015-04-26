package com.transform.beacon.sticker.config

import com.softwaremill.macwire.Macwire
import com.transform.beacon.sticker.controllers.{NPSEventController, StickerController}
import com.transform.beacon.sticker.daos.{NPSEventDao, MongoConnection, SingleMongoConnector, StickerDao}
import com.transform.beacon.sticker.services.{NPSEventService, StickerService}


trait ApplicationModule extends Macwire{
  lazy val mongoConnection = new MongoConnection("user", "password","ds061238.mongolab.com", 61238, "heroku_app36253053")
  lazy val connector = wire[SingleMongoConnector]
  lazy val productDao = wire[StickerDao]
  lazy val productService= wire[StickerService]
  lazy val productController = wire[StickerController]
  lazy val nPSEventController = wire[NPSEventController]
  lazy val nPSEventService = wire[NPSEventService]
  lazy val nPSEventDao = wire[NPSEventDao]

}
