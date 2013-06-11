import sbt._
import Keys._

object BuildSettings {
  val sv = "2.10.2"
  val buildOrganization = "efa.dsa"
  val buildVersion = "1.1.0-SNAPSHOT"
  val buildScalaVersion = sv
  val netbeansRepo = "Netbeans" at "http://bits.netbeans.org/maven2/"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    resolvers += netbeansRepo,
    scalacOptions ++= Seq ("-deprecation", "-feature", "-language:higherKinds",
      "-language:postfixOps"),
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := false
  )
} 

object Dependencies {
  import BuildSettings.sv  

  val utilV = "0.2.2-SNAPSHOT"
  val direV = "0.1.0-SNAPSHOT"
  val efaNbV = "0.3.0-SNAPSHOT"
  val rpgV = "1.1.0-SNAPSHOT"
  val nbV = "RELEASE71"
  val scalazV = "7.0.0"

  val util = "efa"
  val direP = "dire"
  val rpg = "efa.rpg"
  val nb = "org.netbeans.api"
  val scalaz = "org.scalaz"

  val efaCore = util %% "efa-core" % utilV changing

  val efaIo = util %% "efa-io" % utilV changing

  val efaNb = "efa.nb" %% "efa-nb" % efaNbV changing

  val dire = direP %% "dire-core" % direV changing

  val direSwing = direP %% "dire-swing" % direV changing

  val rpgBeing = rpg %% "rpg-being" % rpgV changing

  val rpgCore = rpg %% "rpg-core" % rpgV changing

  val rpgItems = rpg %% "rpg-items" % rpgV changing

  val rpgRules = rpg %% "rpg-rules" % rpgV changing
 
  val nbAnnotations = nb % "org-netbeans-api-annotations-common" % nbV
  val nbUtil = nb % "org-openide-util" % nbV
  val nbLookup = nb % "org-openide-util-lookup" % nbV
  val nbExplorer = nb % "org-openide-explorer" % nbV
  val nbWindows = nb % "org-openide-windows" % nbV
  val nbNodes = nb % "org-openide-nodes" % nbV
  val nbFilesystems = nb % "org-openide-filesystems" % nbV
  val nbLoaders = nb % "org-openide-loaders" % nbV
  val nbModules = nb % "org-openide-modules" % nbV
  val nbAwt = nb % "org-openide-awt" % nbV
  val nbSettings = nb % "org-netbeans-modules-settings" % nbV
  val nbActions = nb % "org-openide-actions" % nbV
  val nbDialogs = nb % "org-openide-dialogs" % nbV
  val nbOutline = nb % "org-netbeans-swing-outline" % nbV
  val nbAutoupdateUi = nb % "org-netbeans-modules-autoupdate-ui" % nbV
  val nbAutoupdateServices = nb % "org-netbeans-modules-autoupdate-services" % nbV
  val nbModulesOptions = nb % "org-netbeans-modules-options-api" % nbV

  val shapeless = "com.chuusai" %% "shapeless" % "1.2.3"
  val scalaz_core = scalaz %% "scalaz-core" % scalazV
  val scalaz_effect = scalaz %% "scalaz-effect" % scalazV
  val scalaz_scalacheck = scalaz %% "scalaz-scalacheck-binding" % scalazV

  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.10.0"

  val coolness = Seq(scalaz_core, scalaz_effect, scalaz_scalacheck,
                     shapeless, scalacheck)
}

object DsaBuild extends Build {
  import Dependencies._
  import BuildSettings._

  def addDeps (ds: ModuleID*) =
    BuildSettings.buildSettings ++ 
    Seq(libraryDependencies ++= (ds ++ coolness))

  lazy val dsa = Project (
    "dsa",
    file("."),
    settings = buildSettings
  ) aggregate (world)
 // ) aggregate (abilities, abilitiesServices,
 //              being, beingCalc, beingServices, beingUI,
 //              equipment, equipmentServices, generation,
 //              rules, testItems, world)
  
  lazy val abilities = Project (
    "dsa-abilities",
    file("abilities"),
    settings = addDeps(efaCore, rpgCore)
  ) dependsOn (world)

  lazy val abilitiesServices = Project (
    "dsa-abilitiesservices",
    file("abilitiesservices"),
    settings = addDeps(efaCore, efaNb, rpgCore, rpgItems)
  ) dependsOn (world, abilities, beingServices)

  lazy val being = Project (
    "dsa-being",
    file("being"),
    settings = addDeps(efaCore, rpgCore)
  ) dependsOn (world, abilities, equipment, generation)

  lazy val beingCalc = Project (
    "dsa-beingCalc",
    file("beingCalc"),
    settings = addDeps(efaCore, rpgCore)
  ) dependsOn (world, abilities, equipment, generation, being, testItems)

  lazy val beingServices = Project (
    "dsa-beingServices",
    file("beingServices"),
    settings = addDeps(efaCore, dire, rpgCore, rpgBeing, rpgRules)
  ) dependsOn (being, beingCalc)

  lazy val beingUI = Project (
    "dsa-beingUI",
    file("beingUI"),
    settings = addDeps(efaCore, efaNb, dire, direSwing, rpgCore, rpgBeing)
  ) dependsOn (beingServices)

  lazy val equipment = Project (
    "dsa-equipment",
    file("equipment"),
    settings = addDeps(efaCore, rpgCore)
  ) dependsOn (world)

  lazy val equipmentServices = Project (
    "dsa-equipmentservices",
    file("equipmentservices"),
    settings = addDeps(efaCore, efaNb, rpgCore, rpgItems)
  ) dependsOn (world, beingServices, equipment)

  lazy val generation = Project (
    "dsa-generation",
    file("generation"),
    settings = addDeps(efaCore, rpgCore)
  ) dependsOn (world)

  lazy val rules = Project (
    "dsa-rules",
    file("rules"),
    settings = addDeps(efaCore, rpgCore, rpgRules)
  ) dependsOn (world, abilities, equipment, generation, being,
               beingCalc, testItems, beingServices)

  lazy val testItems = Project (
    "dsa-testItems",
    file("testItems"),
    settings = addDeps(efaCore, rpgCore)
  ) dependsOn (world, abilities, equipment, generation, being)

  lazy val world = Project (
    "dsa-world",
    file("world"),
    settings = addDeps(efaCore, rpgCore)
  )
}

// vim: set ts=2 sw=2 nowrap et:
