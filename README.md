# Jakarta EE Meets NoSQL

## Otavio Santana (@otaviojava)

![Docker](https://www.docker.com/sites/default/files/horizontal_large.png)

## Run as Docker-Compose

### Start fresh environment

`docker-compose -f docker-compose.yml up -d`

### Stop and Remove

`docker-compose -f docker-compose.yml down`

###  List services

`docker-compose -f docker-compose.yml ps`

###  Dependencies

Do not change the dependencies(pom.xml) due to the currently undefined (5/2021) of the names of the javax packages to jakarta:

`<groupId>org.jboss.weld.se</groupId>`

`<artifactId>weld-se-shaded</artifactId>`

`<groupId>org.eclipse</groupId>`

`<artifactId>yasson</artifactId>`

microprofile-config.properties:

`keyvalue.provider=org.eclipse.jnosql.communication.redis.keyvalue.RedisConfiguration`

`column.provider=org.eclipse.jnosql.communication.cassandra.column.CassandraConfiguration`

`document.provider=org.eclipse.jnosql.communication.mongodb.document.MongoDBDocumentConfiguration`
