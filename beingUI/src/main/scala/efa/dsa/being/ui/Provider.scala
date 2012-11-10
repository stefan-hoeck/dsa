package efa.dsa.being.ui

import efa.dsa.being.{Hero, HeroData}
import efa.dsa.being.services.HeroInfo
import efa.dsa.being.services.spi.UIProvider
import efa.rpg.being.uiInfo
import scalaz.DList

class Provider extends UIProvider {
  import hero._

  def get = DList(
    uiInfo(HeroMainPanel.create),
    uiInfo(HeroTalentPanel.create)
  )
}

// vim: set ts=2 sw=2 et:
