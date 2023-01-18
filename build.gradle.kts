plugins {
	id("com.github.johnrengelman.shadow") version("7.1.2")
	id("net.minecrell.plugin-yml.bukkit") version("0.5.2")
	`java-library`
}

val directory = property("group") as String
val release = property("version") as String

repositories {
	mavenLocal()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://oss.sonatype.org/content/repositories/snapshots/")
	mavenCentral()
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
	
	implementation("org.mongodb:mongo-java-driver:4.8.0")
}

bukkit {
	name = "MongoDB-Test"
	main = "$directory.MongoDBTest"
	authors = listOf("InitSync")
	
	apiVersion = "1.13"
	version = release
	
	commands {
		register("test") {
			description = "-> Command to manage the player stats."
			aliases = listOf("playerstats")
		}
	}
}

tasks {
	shadowJar {
		archiveFileName.set("MongoDB-Test-$release.jar")
		destinationDirectory.set(file("$rootDir/bin/"))
		minimize()
		
		relocate("com.mongodb", "$directory.libs.mongodb")
		relocate("org.bson", "$directory.libs.bson")
	}
	
	clean {
		delete("$rootDir/bin/")
	}
}
