namespace java  sbt.tut.thrift.java
namespace scala sbt.tut.thrift.scala

service SbtTutService {

  string echo(1: required string message);
}
