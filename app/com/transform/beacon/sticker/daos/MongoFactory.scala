package com.transform.beacon.sticker.daos

import reactivemongo.api.{MongoDriver, DefaultDB}
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.core.nodeset.Authenticate
import scala.concurrent.ExecutionContext.Implicits.global


trait MongoConnector {
  def getDb(connection: MongoConnection): DefaultDB
  def getCollection(connection: MongoConnection, collectionName: String): JSONCollection
}

class SingleMongoConnector extends MongoConnector {
  override def getDb(connection: MongoConnection): DefaultDB = MongoFactory.getDb(connection)

  override def getCollection(connection: MongoConnection, collectionName: String): JSONCollection = MongoFactory.getCollection(connection, collectionName)
}


object MongoFactory{

  private lazy val driver = new MongoDriver()

  def getDb(connectionConfig: MongoConnection): DefaultDB = {
    val servers = List(s"${connectionConfig.host}:${connectionConfig.port}")

    val credentials = Seq(Authenticate(connectionConfig.dbName, connectionConfig.user, connectionConfig.password))
    val connection = driver.connection(servers, nbChannelsPerNode = 5, authentications = credentials)

    connection(connectionConfig.dbName)

  }

  def getCollection(connection: MongoConnection, collectionName: String): JSONCollection = {
    getDb(connection).collection[JSONCollection](collectionName)
  }


}
