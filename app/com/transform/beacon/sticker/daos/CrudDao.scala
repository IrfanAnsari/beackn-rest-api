package com.transform.beacon.sticker.daos

import com.transform.beacon.sticker.models.Model

import scala.concurrent.Future


trait CrudDao[M <: Model] {

  def create(model: M): Future[String]

  def read(id: String): Future[Option[M]]

  def update(model: M): Future[Boolean]

  def delete(id: String): Future[Boolean]

  def listAll(filters: (String, Any)*): Future[Seq[M]]

  def exists(id: String): Future[Boolean]

}

