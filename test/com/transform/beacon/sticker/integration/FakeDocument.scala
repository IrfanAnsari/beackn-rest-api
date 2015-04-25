package com.transform.beacon.sticker.integration

import com.transform.beacon.sticker.models.Model


case class FakeDocument(anInt: Int, aString: String, _id: Option[String] = None) extends Model
