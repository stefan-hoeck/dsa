package efa.dsa.world.spi

import efa.core.Localization

trait WorldLoc {

  def invalidEbe: String
  def invalidRaisingCost: String
  def invalidAttribute: String
  def unknownRangedAttackDistance: String
  def unknownBodyPart: String
  def unknownDistanceClass: String
  def invalidWm: String
  def invalidTpKk: String

  final def chLoc = Localization ("ch", nameCh, shortCh, nameCh)
  final def ffLoc = Localization ("ff", nameFf, shortFf, nameFf)
  final def geLoc = Localization ("ge", nameGe, shortGe, nameGe)
  final def inLoc = Localization ("in", nameIn, shortIn, nameIn)
  final def kkLoc = Localization ("kk", nameKk, shortKk, nameKk)
  final def klLoc = Localization ("kl", nameKl, shortKl, nameKl)
  final def koLoc = Localization ("ko", nameKo, shortKo, nameKo)
  final def muLoc = Localization ("mu", nameMu, shortMu, nameMu)
  def nameCh: String
  def shortCh: String
  def nameFf: String
  def shortFf: String
  def nameGe: String
  def shortGe: String
  def nameIn: String
  def shortIn: String
  def nameKk: String
  def shortKk: String
  def nameKl: String
  def shortKl: String
  def nameKo: String
  def shortKo: String
  def nameMu: String
  def shortMu: String

  final def headLoc = new Localization ("head", nameHead)
  final def chestLoc = new Localization ("chest", nameChest)
  final def backLoc = new Localization ("back", nameBack)
  final def stomachLoc = new Localization ("stomach", nameStomach)
  final def armlLoc = new Localization ("armL", nameArmL)
  final def armrLoc = new Localization ("armR", nameArmR)
  final def leglLoc = new Localization ("legL", nameLegL)
  final def legrLoc = new Localization ("legR", nameLegR)
  def nameHead: String
  def nameChest: String
  def nameBack: String
  def nameStomach: String
  def nameArmL: String
  def nameArmR: String
  def nameLegL: String
  def nameLegR: String

  final def dcHLoc = Localization ("h", nameH, shortH, nameH)
  final def dcHNLoc = Localization ("hn", nameHN, shortHN, nameHN)
  final def dcNLoc = Localization ("n", nameN, shortN, nameN)
  final def dcNSLoc = Localization ("ns", nameNS, shortNS, nameNS)
  final def dcSLoc = Localization ("s", nameS, shortS, nameS)
  final def dcSPLoc = Localization ("sp", nameSP, shortSP, nameSP)
  final def dcPLoc = Localization ("p", nameP, shortP, nameP)
  final def dcHNSLoc = Localization ("hns", nameHNS, shortHNS, nameHNS)
  final def dcNSPLoc = Localization ("nsp", nameNSP, shortNSP, nameNSP)
  final def dcHNSPLoc = Localization ("hnsp", nameHNSP, shortHNSP, nameHNSP)
  def nameH: String
  def nameHN: String
  def nameN: String
  def nameNS: String
  def nameS: String
  def nameSP: String
  def nameP: String
  def nameHNS: String
  def nameNSP: String
  def nameHNSP: String
  def shortH: String
  def shortHN: String
  def shortN: String
  def shortNS: String
  def shortS: String
  def shortSP: String
  def shortP: String
  def shortHNS: String
  def shortNSP: String
  def shortHNSP: String

  final def rdVeryCloseLoc = new Localization ("very close", nameVeryClose)
  final def rdCloseLoc = new Localization ("close", nameClose)
  final def rdMediumLoc = new Localization ("medium", nameMedium)
  final def rdFarLoc = new Localization ("far", nameFar)
  final def rdVeryFarLoc = new Localization ("very far", nameVeryFar)
  def nameVeryClose: String
  def nameClose: String
  def nameMedium: String
  def nameFar: String
  def nameVeryFar: String

  final def ssVeryLargeLoc = new Localization ("very large", nameSsVeryLarge)
  final def ssLargeLoc = new Localization ("large", nameSsLarge)
  final def ssSmallLoc = new Localization ("small", nameSsSmall)
  final def ssParierLoc = new Localization ("parier", nameSsParier)
  def nameSsVeryLarge: String
  def nameSsLarge: String
  def nameSsSmall: String
  def nameSsParier: String

  final def stSLoc = Localization ("s", nameStS, shortStS, nameStS)
  final def stSPLoc = Localization ("sp", nameStSP, shortStSP, nameStSP)
  final def stPLoc = Localization ("p", nameStP, shortStP, nameStP)
  def nameStS: String
  def nameStSP: String
  def nameStP: String
  def shortStS: String
  def shortStSP: String
  def shortStP: String

  final def coinDLoc = Localization ("d", nameCoinD, shortCoinD, nameCoinD)
  final def coinSLoc = Localization ("s", nameCoinS, shortCoinS, nameCoinS)
  final def coinHLoc = Localization ("h", nameCoinH, shortCoinH, nameCoinH)
  final def coinKLoc = Localization ("k", nameCoinK, shortCoinK, nameCoinK)
  def shortCoinD: String
  def shortCoinS: String
  def shortCoinH: String
  def shortCoinK: String
  def nameCoinD: String
  def nameCoinS: String
  def nameCoinH: String
  def nameCoinK: String
  def pluralCoinD: String
  def pluralCoinS: String
  def pluralCoinH: String
  def pluralCoinK: String
  
  final def weightQLoc = Localization ("q", nameWeightQ, shortWeightQ, nameWeightQ)
  final def weightSTLoc = Localization ("st", nameWeightST, shortWeightST, nameWeightST)
  final def weightULoc = Localization ("u", nameWeightU, shortWeightU, nameWeightU)
  final def weightSKLoc = Localization ("sk", nameWeightSK, shortWeightSK, nameWeightSK)
  final def weightKLoc = Localization ("k", nameWeightK, shortWeightK, nameWeightK)
  final def weightGLoc = Localization ("g", nameWeightG, shortWeightG, nameWeightG)
  def shortWeightQ: String
  def shortWeightST: String
  def shortWeightU: String
  def shortWeightSK: String
  def shortWeightK: String
  def shortWeightG: String
  def nameWeightQ: String
  def nameWeightST: String
  def nameWeightU: String
  def nameWeightSK: String
  def nameWeightK: String
  def nameWeightG: String
  def pluralWeightQ: String
  def pluralWeightST: String
  def pluralWeightU: String
  def pluralWeightSK: String
  def pluralWeightK: String
  def pluralWeightG: String

  final def distanceMLoc = Localization (
    "m", nameDistanceM, shortDistanceM, nameDistanceM)
  final def distanceSLoc = Localization (
    "s", nameDistanceS, shortDistanceS, nameDistanceS)
  final def distanceSPLoc = Localization (
    "sp", nameDistanceSP, shortDistanceSP, nameDistanceSP)
  final def distanceFLoc = Localization (
    "f", nameDistanceF, shortDistanceF, nameDistanceF)
  final def distanceHFLoc = Localization (
    "hf", nameDistanceHF, shortDistanceHF, nameDistanceHF)
  def shortDistanceM: String
  def shortDistanceS: String
  def shortDistanceSP: String
  def shortDistanceF: String
  def shortDistanceHF: String
  def nameDistanceM: String
  def nameDistanceS: String
  def nameDistanceSP: String
  def nameDistanceF: String
  def nameDistanceHF: String
  def pluralDistanceM: String
  def pluralDistanceS: String
  def pluralDistanceSP: String
  def pluralDistanceF: String
  def pluralDistanceHF: String
}

object WorldLoc extends WorldLoc {

  def invalidEbe = "Unbekanntes Format für EBE. Mögliche Eingaben: -, -1, 0, x2, x3"
  def invalidRaisingCost =
    "Unbekannte Steigerungskosten. Mögliche Eingaben: A* oder A - H"
  def invalidAttribute = "Unbekannte Eigenschaft"
  def unknownRangedAttackDistance = "Unbekannte Fernkampfdistanz"
  def unknownBodyPart = "Unbekanntes Körperteil"
  def unknownDistanceClass = "Unbekannte Distanzklasse"
  def invalidWm = "Ungültige Eingabe für At/Pa."
  def invalidTpKk = "Ungültige Eingabe fücr Tp/Kk."

  def nameCh = "Charisma"
  def shortCh = "CH"
  def nameFf = "Fingerfertigkeit"
  def shortFf = "FF"
  def nameGe = "Gewandtheit"
  def shortGe = "GE"
  def nameIn = "Intuition"
  def shortIn = "IN"
  def nameKk = "Körperkraft"
  def shortKk = "KK"
  def nameKl = "Klugheit"
  def shortKl = "KL"
  def nameKo = "Konstitution"
  def shortKo = "KO"
  def nameMu = "Mut"
  def shortMu = "MU"

  def nameHead = "Kopf"
  def nameChest = "Brust"
  def nameBack = "Rücken"
  def nameStomach = "Bauch"
  def nameArmL = "Arm links"
  def nameArmR = "Arm rechts"
  def nameLegL = "Bein links"
  def nameLegR = "Bein rechts"

  def nameH = "Handgemenge"
  def nameHN = "Hand/Nah"
  def nameN = "Nahkampf"
  def nameNS = "Nah/Stange"
  def nameS = "Stangenwaffe"
  def nameSP = "Stange/Pike"
  def nameP = "Pike"
  def nameHNS = "Hand/Nah/Stange"
  def nameNSP = "Nah/Stange/Pike"
  def nameHNSP = "Alle"
  def shortH = "H"
  def shortHN = "HN"
  def shortN = "N"
  def shortNS = "NS"
  def shortS = "S"
  def shortSP = "SP"
  def shortP = "P"
  def shortHNS = "HNS"
  def shortNSP = "NSP"
  def shortHNSP = "HNSP"

  def nameVeryClose = "sehr nah"
  def nameClose = "nah"
  def nameMedium = "mittel"
  def nameFar = "weit"
  def nameVeryFar = "sehr weit"

  def nameSsVeryLarge = "sehr gross"
  def nameSsLarge = "gross"
  def nameSsSmall = "klein"
  def nameSsParier = "-"

  def nameStS = "Schild"
  def nameStSP = "Schild/Parierwaffe"
  def nameStP = "Parierwaffe"
  def shortStS = "S"
  def shortStSP = "SP"
  def shortStP = "P"

  def shortCoinD = "D"
  def shortCoinS = "S"
  def shortCoinH = "H"
  def shortCoinK = "K"
  def nameCoinD = "Dukat"
  def nameCoinS = "Silbertaler"
  def nameCoinH = "Heller"
  def nameCoinK = "Kreuzer"
  def pluralCoinD = "Dukaten"
  def pluralCoinS = "Silbertaler"
  def pluralCoinH = "Heller"
  def pluralCoinK = "Kreuzer"
  
  def shortWeightQ = "q"
  def shortWeightST = "st"
  def shortWeightU = "u"
  def shortWeightSK = "sk"
  def shortWeightK = "k"
  def shortWeightG = "gr"
  def nameWeightQ = "Quader"
  def nameWeightST = "Stein"
  def nameWeightU = "Unze"
  def nameWeightSK = "Skrupel"
  def nameWeightK = "Karat"
  def nameWeightG = "Gran"
  def pluralWeightQ = "Quader"
  def pluralWeightST = "Stein"
  def pluralWeightU = "Unzen"
  def pluralWeightSK = "Skrupel"
  def pluralWeightK = "Karat"
  def pluralWeightG = "Gran"

  def shortDistanceM = "m"
  def shortDistanceS = "s"
  def shortDistanceSP = "sp"
  def shortDistanceF = "f"
  def shortDistanceHF = "hf"
  def nameDistanceM = "Meile"
  def nameDistanceS = "Schritt"
  def nameDistanceSP = "Spann"
  def nameDistanceF = "Finger"
  def nameDistanceHF = "Halbfinger"
  def pluralDistanceM = "Meilen"
  def pluralDistanceS = "Schritte"
  def pluralDistanceSP = "Spann"
  def pluralDistanceF = "Finger"
  def pluralDistanceHF = "Halbfinger"
}

// vim: set ts=2 sw=2 et:
