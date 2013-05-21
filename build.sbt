import AssemblyKeys._

scalacOptions ++= Seq("-unchecked", "-deprecation")

seq(assemblySettings: _*)

name := "IRCBalloonJ"

version := "0.1.1"

scalaVersion := "2.9.1"

fork in run := true

compileOrder := CompileOrder.JavaThenScala

mainClass in assembly := Some("org.bone.ircballoon.MainWindow")

mainClass := Some("org.bone.ircballoon.MainWindow")

resolvers ++= Seq(
    "gettext-commons-site" at "http://gettext-commons.googlecode.com/svn/maven-repository"
)

libraryDependencies ++= Seq(
    "org.xnap.commons" % "gettext-commons" % "0.9.6"
)

TaskKey[Unit]("xgettext") <<= (sources in Compile, name) map { (sources, name) =>
    <x>xgettext --from-code=utf-8 -L java -ktrc:1c,2 -ktrnc:1c,2,3 -ktr
       -kmarktr -ktrn:1,2 -o po/{name}.pot {sources.mkString(" ")}</x> !
}

TaskKey[Unit]("msgfmt") <<= (classDirectory in Compile, name) map { (target, name) =>
    import java.io.File
    val poFiles = (PathFinder(new File("po")) ** "*.po").get
    <x>msgfmt --java2 -d {target} -r app.i18n.Messages {poFiles.mkString(" ")}</x> !
}