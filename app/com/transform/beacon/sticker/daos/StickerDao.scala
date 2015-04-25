package com.transform.beacon.sticker.daos

import com.transform.beacon.sticker.models
import com.transform.beacon.sticker.models.{Sticker}
import play.api.libs.json.Format

class StickerDao(val connection: MongoConnection, val connector: MongoConnector) extends MongoDao[models.Sticker] {
  override val collectionName: String = "sticker"
  override implicit val formatter: Format[models.Sticker] = Sticker.stickerFormat
}

