package com.transform.beacon.sticker.models

import play.api.libs.json.Json

abstract class Model {
  def _id: Option[String]
}

case class Sticker(_id: Option[String], _type: String, rssi: String) extends Model


object Sticker {
  implicit val stickerFormat = Json.format[Sticker]
}

case class NPSLocatable(_id: Option[String], identifier: String, rssi: Int, _type: String)

object NPSLocatable {

  implicit val npsLocatableWriter = Json.writes[NPSLocatable]
  implicit val npsLocatableReader = Json.reads[NPSLocatable]
}

case class NPSEvent(_id: Option[String], identifier: String, npsLocatables: List[NPSLocatable]) extends Model

object NPSEvent {

  implicit val npsEventWriter = Json.writes[NPSEvent]
  implicit val npsEventReader = Json.reads[NPSEvent]

//
//
//  implicit val npsLocatableFormat = Json.format[NPSLocatable]
  implicit val npsEventFormat = Json.format[NPSEvent]
}


