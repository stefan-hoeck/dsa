import scala.language.postfixOps
import sbt._
import Keys._

object BuildSettings {
  val sv                = "2.11.2"
  val buildOrganization = "efa.dsa"
  val buildVersion      = "1.2.0-SNAPSHOT"
  val buildScalaVersion = sv
  val netbeansRepo      = "Netbeans" at "http://bits.netbeans.org/maven2/"

  val manifest          = SettingKey[File]("manifest", "Location of the Manifest.mf file")
  val removeManifest    = TaskKey[Unit]("remove-manifest", "Removes manifest file")

  val buildSettings = Seq (
    organization       := buildOrganization,
    version            := buildVersion,
    scalaVersion       := buildScalaVersion,
    resolvers          += netbeansRepo,
    publishTo          := Some(Resolver.file("file", 
      new File(Path.userHome.absolutePath+"/.m2/repository"))),
    manifest           <<= classDirectory in Compile apply (_ / "META-INF/MANIFEST.MF"),
    removeManifest     <<= manifest map (f ⇒ f.delete),
    fork               := true,
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := false,

    scalacOptions      ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-language:higherKinds"
    )
  )
} 

object Dependencies {
  import BuildSettings.sv  

  val direV                = "0.2.0-SNAPSHOT"
  val efa_nbV               = "0.3.2-SNAPSHOT"
  val nbV                  = "RELEASE80"
  val rpgV                 = "1.2.0-SNAPSHOT"
  val scalacheckV          = "1.11.4"
  val scalazV              = "7.1.0-RC2"
  val shapelessV           = "2.0.0"
  val utilV                = "0.2.3-SNAPSHOT"

  val dire                 = "dire"
  val nb                   = "org.netbeans.api"
  val rpg                  = "efa.rpg"
  val scalaz               = "org.scalaz"
  val util                 = "efa"

  val efa_core             = (util %% "efa-core" % utilV).changing
  val efa_io               = (util %% "efa-io" % utilV).changing
  val efa_nb                = ("efa.nb" %% "efa-nb" % efa_nbV).changing
  val dire_core            = (dire %% "dire-core" % direV).changing
  val dire_swing           = (dire %% "dire-swing" % direV).changing

  val rpg_being             = (rpg %% "rpg-being" % rpgV).changing
  val rpg_core              = (rpg %% "rpg-core" % rpgV).changing
  val rpg_items             = (rpg %% "rpg-items" % rpgV).changing
  val rpg_rules             = (rpg %% "rpg-rules" % rpgV).changing

  val nbActions            = nb % "org-openide-actions" % nbV
  val nbAnnotations        = nb % "org-netbeans-api-annotations-common" % nbV
  val nbAutoupdateServices = nb % "org-netbeans-modules-autoupdate-services" % nbV
  val nbAutoupdateUi       = nb % "org-netbeans-modules-autoupdate-ui" % nbV
  val nbAwt                = nb % "org-openide-awt" % nbV
  val nbDialogs            = nb % "org-openide-dialogs" % nbV
  val nbExplorer           = nb % "org-openide-explorer" % nbV
  val nbFilesystems        = nb % "org-openide-filesystems" % nbV
  val nbLoaders            = nb % "org-openide-loaders" % nbV
  val nbLookup             = nb % "org-openide-util-lookup" % nbV
  val nbModules            = nb % "org-openide-modules" % nbV
  val nbModulesOptions     = nb % "org-netbeans-modules-options-api" % nbV
  val nbMultiview          = nb % "org-netbeans-core-multiview" % nbV
  val nbNodes              = nb % "org-openide-nodes" % nbV
  val nbOptions            = nb % "org-netbeans-modules-options-api" % nbV
  val nbOutline            = nb % "org-netbeans-swing-outline" % nbV
  val nbSettings           = nb % "org-netbeans-modules-settings" % nbV
  val nbUtil               = nb % "org-openide-util" % nbV
  val nbWindows            = nb % "org-openide-windows" % nbV

  val scalacheck           = "org.scalacheck" %% "scalacheck" % scalacheckV
  val scalaz_core          = scalaz %% "scalaz-core" % scalazV
  val scalaz_effect        = scalaz %% "scalaz-effect" % scalazV
  val scalaz_scalacheck    = scalaz %% "scalaz-scalacheck-binding" % scalazV
  val shapeless            = "com.chuusai" %% "shapeless" % shapelessV

  val deps                 = Seq(scalaz_core, scalaz_effect, scalaz_scalacheck,
                                 shapeless, scalacheck)
}

object DsaBuild extends Build {
  import Dependencies._
  import BuildSettings._

  def addDeps (ds: ModuleID*) =
    BuildSettings.buildSettings ++ 
    Seq(libraryDependencies ++= (ds ++ deps))

  lazy val dsa = Project (
    "dsa",
    file("."),
    settings = buildSettings
  ) aggregate (world)
  //) aggregate (abilities, abilitiesServices,
  //             being, beingCalc, beingServices, beingUI,
  //             equipment, equipmentServices, generation, rules,
  //             testItems, world)
  
  lazy val abilities = Project (
    "dsa-abilities",
    file("abilities"),
    settings = addDeps(efa_core, rpg_core)
  ) dependsOn (world)

  lazy val abilitiesServices = Project (
    "dsa-abilitiesservices",
    file("abilitiesservices"),
    settings = addDeps(efa_core, efa_nb, rpg_core, rpg_items) ++ Seq(fork := true)
  ) dependsOn (world, abilities, beingServices)

  lazy val being = Project (
    "dsa-being",
    file("being"),
    settings = addDeps(efa_core, rpg_core)
  ) dependsOn (world, abilities, equipment, generation)

  lazy val beingCalc = Project (
    "dsa-beingCalc",
    file("beingCalc"),
    settings = addDeps(efa_core, rpg_core)
  ) dependsOn (world, abilities, equipment, generation, being, testItems)

  lazy val beingServices = Project (
    "dsa-beingServices",
    file("beingServices"),
    settings = addDeps(efa_core, dire_core, rpg_core, rpg_being, rpg_rules)
  ) dependsOn (being, beingCalc)

  lazy val beingUI = Project (
    "dsa-beingUI",
    file("beingUI"),
    settings = addDeps(efa_core, efa_nb, dire_core, dire_swing, rpg_core, rpg_being, rpg_items)
  ) dependsOn (beingServices)

  lazy val equipment = Project (
    "dsa-equipment",
    file("equipment"),
    settings = addDeps(efa_core, rpg_core)
  ) dependsOn (world)

  lazy val equipmentServices = Project (
    "dsa-equipmentservices",
    file("equipmentservices"),
    settings = addDeps(efa_core, efa_nb, rpg_core, rpg_items) ++ Seq(fork := true)
  ) dependsOn (world, beingServices, equipment)

  lazy val generation = Project (
    "dsa-generation",
    file("generation"),
    settings = addDeps(efa_core, rpg_core)
  ) dependsOn (world)

  lazy val rules = Project (
    "dsa-rules",
    file("rules"),
    settings = addDeps(efa_core, rpg_core, rpg_rules)
  ) dependsOn (world, abilities, equipment, generation, being,
               beingCalc, testItems, beingServices)

  lazy val testItems = Project (
    "dsa-testItems",
    file("testItems"),
    settings = addDeps(efa_core, rpg_core)
  ) dependsOn (world, abilities, equipment, generation, being)

  lazy val world = Project (
    "dsa-world",
    file("world"),
    settings = addDeps(efa_core, rpg_core)
  )
}

// vim: set ts=2 sw=2 nowrap et:
