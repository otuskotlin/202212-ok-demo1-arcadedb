import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty
import org.junit.Test
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as i


class GremlinTest {

    @Test
    fun gremlin() {
        val cluster = Cluster.build()
            .addContactPoints("localhost")
            .port(8182)
            .credentials("root", "root_root")
            .create()

        AnonymousTraversalSource
            .traversal()
            .withRemote(DriverRemoteConnection.using(cluster, "g"))
            .use { g ->
                val userId = g
                    .addV("User")
                    .property(VertexProperty.Cardinality.single, "name", "Evan")
                    .next()
                    .id()
                println("UserID: $userId")

                // Add all other vertices and edges
                g
                    .V().has("name", "Evan").`as`("Evan")
                    .addV("User").property(VertexProperty.Cardinality.single, "name", "Peter").`as`("Peter")
                    .addV("User").property(VertexProperty.Cardinality.single, "name", "John").`as`("John")
                    .addV("User").property(VertexProperty.Cardinality.single, "name", "Frank").`as`("Frank")
                    .addE("Friend").from("Evan").to("Peter")
                    .property(VertexProperty.Cardinality.single, "level", "best")
                    .addE("Friend").from("Evan").to("John")
                    .property(VertexProperty.Cardinality.single, "level", "close")
                    .addE("Friend").from("John").to("Frank")
                    .property(VertexProperty.Cardinality.single, "level", "best")
                    .iterate()

                // Search for vertex and other complimentary data
                val res = g.V().has("name", "Evan")
                    .project<Vertex>("id", "name", "friendIds")
                    .by(i.id<Vertex>())
                    .by("name")
                    .by(i.outE("Friend").inV().id().fold())
                    .toList()
                println("res: $res")

                // Search for paths between two vertices
                val path = g
                    .V().has("name", "Evan").`as`("Evan")
                    .repeat(i.bothE().otherV().simplePath())
                    .times(5)
                    .emit(i.has<Vertex>("name", "Frank"))
                    .path()
                    .toList()
                path.forEach {
                    println("path: $it")
                }
            }
    }
}
