package com.transform.beacon.sticker.config

import com.softwaremill.macwire.{InstanceLookup, Macwire}


object Global extends play.api.GlobalSettings with Macwire {
  val instanceLookup = InstanceLookup(valsByClass(new ApplicationModule {}))

 override def getControllerInstance[A](controllerClass: Class[A]): A = {
    instanceLookup.lookupSingleOrThrow(controllerClass)
  }



}