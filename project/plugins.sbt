resolvers += Resolver.url("thirstycrow", url("https://raw.githubusercontent.com/thirstycrow/repo/master/releases/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.twitter" % "scrooge-sbt-plugin" % "3.12.3-tc")
