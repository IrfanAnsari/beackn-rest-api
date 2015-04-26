package com.transform.beacon.sticker.services

import com.transform.beacon.sticker.daos.{NPSEventDao, StickerDao, CrudDao}
import com.transform.beacon.sticker.models.Model

import scala.concurrent.Future


abstract class CrudService[M <: Model](val crudDao: CrudDao[M]) {

  def create(model: M): Future[String] = {
    crudDao.create(model)
  }

  def read(id: String): Future[Option[M]]= {
    crudDao.read(id)
  }

  def update(model: M): Future[Boolean] = {
    crudDao.update(model)
  }

  def delete(id: String): Future[Boolean] = {
    crudDao.delete(id)
  }

  def listAll(filters: (String, Any)*): Future[Seq[M]] = {
    crudDao.listAll(filters: _*)
  }
}


class StickerService(override val crudDao: StickerDao) extends CrudService(crudDao)


class NPSEventService(override val crudDao: NPSEventDao) extends CrudService(crudDao)

