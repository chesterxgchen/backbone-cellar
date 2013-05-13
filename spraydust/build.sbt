organization := "com.xiaoguangchen"

scalaVersion  := "2.10.0"

unmanagedBase := file( "lib" ).getAbsoluteFile

libraryDependencies += "org.testng" % "testng" % "6.8"

seq(testNGSettings:_*)

testNGVersion         := "6.4"

testNGOutputDirectory := "target/testng"

testNGParameters      := Seq()

testNGSuites          := Seq(  "test/resources/testng.xml")

seq(dustSettings: _*)

seq(jsSettings : _*)

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"  ,
  "sbt-plugin-releases" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"
)


libraryDependencies ++= Seq(
//scala-2.10.0
  "io.spray"             %   "spray-can"             % "1.1-M8-SNAPSHOT"
  ,"io.spray"            %   "spray-routing"         % "1.1-M8-SNAPSHOT"
  ,"io.spray"            %%  "spray-json"            % "1.2.3"
  ,"com.typesafe.akka"   %%  "akka-actor"             % "2.1.2"
  ,"org.testng"          %   "testng"              % "6.8"
  , "org.joda"           %   "joda-convert"        % "1.3"
  ,"joda-time"           %   "joda-time"           % "2.1"
  ,"mysql"               %   "mysql-connector-java" % "5.1.23"
)


(resourceManaged in (Compile, DustKeys.dust)) <<= (sourceDirectory in Compile) ( _ / "resources" / "web" / "js"/"dust")

//don't do this, this will cause all src code being deleted with sbt clean
//(resourceManaged in (Compile, JsKeys.js)) <<= (sourceDirectory in Compile)( _ /"." )

(resourceGenerators in Compile) <+= (JsKeys.js in Compile)
