package com.transform.beacon.sticker.models

import play.api.libs.json.Json


abstract class Model {
  def _id: Option[String]
}

case class Sticker(_id: Option[String], _type: String, rssi: String ) extends Model


object Sticker {
  implicit val stickerFormat = Json.format[Sticker]
}
