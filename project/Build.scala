import sbt._
import Keys._

object BuildSettings {
  import Resolvers._

  val sv = "2.10.0"
  val buildOrganization = "efa.dsa"
  val buildVersion = "1.0.0-SNAPSHOT"
  val buildScalaVersion = sv

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    resolvers ++= repos,
    scalacOptions ++= Seq ("-deprecation", "-feature", "-language:higherKinds",
      "-language:postfixOps"),
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := false
  )
} 

object Resolvers {
 val netbeansRepo = "Netbeans" at "http://bits.netbeans.org/maven2/"
 val scalatoolsRepo = "Scala-Tools Maven2 Repository Releases" at
   "http://scala-tools.org/repo-releases"
 val sonatypeRepo = "releases" at
   "http://oss.sonatype.org/content/repositories/releases"

 val repos = Seq (netbeansRepo, scalatoolsRepo, sonatypeRepo)
}

object Dependencies {
  import BuildSettings.sv  

  val utilVersion = "0.2.0-SNAPSHOT"
  val reactVersion = "0.1.0"
  val util = "efa"
  val react = "efa.react"
  val efaCore = util %% "efa-core" % utilVersion changing

  val efaData = util %% "efa-data" % utilVersion changing

  val efaIo = util %% "efa-io" % utilVersion changing

  val efaNb = util %% "efa-nb" % utilVersion changing

  val efaReact = react %% "react-core" % reactVersion

  val efaReactSwing = react %% "react-swing" % reactVersion

  val rpgVersion = "1.0.0-SNAPSHOT"
  val rpg = "efa.rpg"
  val rpgBeing = rpg %% "rpg-being" % rpgVersion changing

  val rpgCore = rpg %% "rpg-core" % rpgVersion changing

  val rpgItems = rpg %% "rpg-items" % rpgVersion changing

  val rpgRules = rpg %% "rpg-rules" % rpgVersion changing

  val nbV = "RELEASE71"

  val scalaSwing = "org.scala-lang" % "scala-swing" % sv
 
  val nbAnnotations = "org.netbeans.api" % "org-netbeans-api-annotations-common" % nbV
  val nbUtil = "org.netbeans.api" % "org-openide-util" % nbV
  val nbLookup = "org.netbeans.api" % "org-openide-util-lookup" % nbV
  val nbExplorer = "org.netbeans.api" % "org-openide-explorer" % nbV
  val nbWindows = "org.netbeans.api" % "org-openide-windows" % nbV
  val nbNodes = "org.netbeans.api" % "org-openide-nodes" % nbV
  val nbFilesystems = "org.netbeans.api" % "org-openide-filesystems" % nbV
  val nbLoaders = "org.netbeans.api" % "org-openide-loaders" % nbV
  val nbModules = "org.netbeans.api" % "org-openide-modules" % nbV
  val nbAwt = "org.netbeans.api" % "org-openide-awt" % nbV
  val nbSettings = "org.netbeans.api" % "org-netbeans-modules-settings" % nbV
  val nbActions = "org.netbeans.api" % "org-openide-actions" % nbV
  val nbDialogs = "org.netbeans.api" % "org-openide-dialogs" % nbV
  val nbOutline = "org.netbeans.api" % "org-netbeans-swing-outline" % nbV
  val nbAutoupdateUi = "org.netbeans.api" % "org-netbeans-modules-autoupdate-ui" % nbV
  val nbAutoupdateServices = "org.netbeans.api" % "org-netbeans-modules-autoupdate-services" % nbV
  val nbModulesOptions = "org.netbeans.api" % "org-netbeans-modules-options-api" % nbV

  val scalaz_core = "org.scalaz" %% "scalaz-core" % "7.0.0-M7"
  val scalaz_effect = "org.scalaz" %% "scalaz-effect" % "7.0.0-M7"
  val scalaz_scalacheck =
    "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.0-M7"
  val scalaz_scalacheckT = scalaz_scalacheck % "test"

  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.10.0"
  val scalacheckT = scalacheck % "test"
  val scalazCheckT = Seq(scalaz_core, scalaz_scalacheckT, scalacheckT)
  val scalazCheckET = scalazCheckT :+ scalaz_effect
}

object DsaBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  def addDeps (ds: Seq[ModuleID]) =
    BuildSettings.buildSettings ++ Seq (libraryDependencies ++= ds)

  lazy val dsa = Project (
    "dsa",
    file("."),
    settings = buildSettings
  ) aggregate (abilities, abilitiesServices,
               being, beingCalc, beingServices, beingUI,
               equipment, equipmentServices, generation,
               rules, testItems, world)
  
  lazy val abilities = Project (
    "dsa-abilities",
    file("abilities"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, efaData, rpgCore))
  ) dependsOn (world)

  lazy val abilitiesServices = Project (
    "dsa-abilitiesservices",
    file("abilitiesservices"),
    settings = addDeps (scalazCheckET ++ Seq(efaCore, efaData, efaNb, rpgCore, rpgItems))
  ) dependsOn (world, abilities, beingServices)

  lazy val being = Project (
    "dsa-being",
    file("being"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, rpgCore))
  ) dependsOn (world, abilities, equipment, generation)

  lazy val beingCalc = Project (
    "dsa-beingCalc",
    file("beingCalc"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, rpgCore))
  ) dependsOn (world, abilities, equipment, generation, being, testItems)

  lazy val beingServices = Project (
    "dsa-beingServices",
    file("beingServices"),
    settings = addDeps (scalazCheckT ++
      Seq(efaCore, efaData, efaReact, rpgCore, rpgBeing, rpgRules))
  ) dependsOn (being, beingCalc)

  lazy val beingUI = Project (
    "dsa-beingUI",
    file("beingUI"),
    settings = addDeps (scalazCheckT ++
      Seq(efaCore, efaData, efaNb, efaReact, efaReactSwing, rpgCore, rpgBeing))
  ) dependsOn (beingServices)

  lazy val equipment = Project (
    "dsa-equipment",
    file("equipment"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, efaData, rpgCore))
  ) dependsOn (world)

  lazy val equipmentServices = Project (
    "dsa-equipmentservices",
    file("equipmentservices"),
    settings = addDeps (scalazCheckET ++ Seq(efaCore, efaData, efaNb, rpgCore, rpgItems))
  ) dependsOn (world, beingServices, equipment)

  lazy val generation = Project (
    "dsa-generation",
    file("generation"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, efaData, rpgCore))
  ) dependsOn (world)

  lazy val rules = Project (
    "dsa-rules",
    file("rules"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, rpgCore, efaData, rpgRules))
  ) dependsOn (world, abilities, equipment, generation, being,
               beingCalc, testItems, beingServices)

  lazy val testItems = Project (
    "dsa-testItems",
    file("testItems"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, efaData, rpgCore))
  ) dependsOn (world, abilities, equipment, generation, being)

  lazy val world = Project (
    "dsa-world",
    file("world"),
    settings = addDeps (scalazCheckT ++ Seq(efaCore, efaData, rpgCore))
  )
}

// vim: set ts=2 sw=2 et:
