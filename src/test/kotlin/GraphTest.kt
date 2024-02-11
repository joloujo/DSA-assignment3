import org.example.GraphAL
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class GraphTest {

    @Test
    fun testGraph() {
        val graph = GraphAL<String>()

        // Make sure adding vertices works
        graph.addVertex("A")
        graph.addVertex("B")
        graph.addVertex("C")
        assertEquals(setOf("A", "B", "C"), graph.getVertices(), "Couldn't add vertices")

        // Make sure adding edges works
        graph.addEdge("A", "B", 1.0)
        graph.addEdge("B", "C", 2.0)
        graph.addEdge("A", "C", 4.0)
        assertEquals(mapOf("B" to 1.0, "C" to 4.0), graph.getEdges("A"), "Couldn't add edges")

        // Make sure vertices can't be added more than once
        graph.addVertex("A")
        assertEquals(setOf("A", "B", "C"), graph.getVertices(), "Adding duplicate vertex breaks vertices")
        assertEquals(mapOf("B" to 1.0, "C" to 4.0), graph.getEdges("A"),
            "Adding duplicate vertex breaks edges")

        // Make sure clearing works
        graph.clear()
        assertEquals(setOf<String>(), graph.getVertices(), "Clearing doesn't remove all vertices")
    }

    @Test
    fun testDijkstras() {
        // Make Graph 1 in graphs.md
        // A --2--> B
        // B --3--> C
        // A --4--> C
        // C --3--> D
        // B --1--> D
        // A --9--> E
        // D --5--> E
        val graph1 = GraphAL<String>()

        graph1.addVertex("A")
        graph1.addVertex("B")
        graph1.addVertex("C")
        graph1.addVertex("D")
        graph1.addVertex("E")

        graph1.addEdge("A", "B", 2.0)
        graph1.addEdge("B", "C", 3.0)
        graph1.addEdge("A", "C", 4.0)
        graph1.addEdge("C", "D", 3.0)
        graph1.addEdge("B", "D", 1.0)
        graph1.addEdge("A", "E", 9.0)
        graph1.addEdge("D", "E", 5.0)

        // Test different paths in Graph 2
        assertEquals(Pair(listOf("A", "B", "D", "E"), 8.0), graph1.dijkstras("A", "E"))
        assertEquals(Pair(listOf("C", "D", "E"), 8.0), graph1.dijkstras("C", "E"))
        assertEquals(Pair(listOf("A", "B", "D"), 3.0), graph1.dijkstras("A", "D"))
        assertEquals(null, graph1.dijkstras("E", "A"))
        assertEquals(null, graph1.dijkstras("D", "C"))

        // Make Graph 2 in graphs.md
        // A --1--> B
        // A --4--> C
        // A --5--> D
        // B --3--> D
        // B --6--> E
        // C --2--> D
        // D --2--> E
        // D --7--> F
        // E --4--> F
        val graph2 = GraphAL<String>()

        graph2.addVertex("A")
        graph2.addVertex("B")
        graph2.addVertex("C")
        graph2.addVertex("D")
        graph2.addVertex("E")
        graph2.addVertex("F")

        graph2.addEdge("A", "B", 1.0)
        graph2.addEdge("A", "C", 4.0)
        graph2.addEdge("A", "D", 5.0)
        graph2.addEdge("B", "D", 3.0)
        graph2.addEdge("B", "E", 6.0)
        graph2.addEdge("C", "D", 2.0)
        graph2.addEdge("D", "E", 2.0)
        graph2.addEdge("D", "F", 7.0)
        graph2.addEdge("E", "F", 4.0)

        // Test different paths in Graph 2
        assertEquals(Pair(listOf("A", "B", "D", "E", "F"), 10.0), graph2.dijkstras("A", "F"))
        assertEquals(Pair(listOf("C", "D", "E", "F"), 8.0), graph2.dijkstras("C", "F"))
        assertEquals(Pair(listOf("B", "D", "E"), 5.0), graph2.dijkstras("B", "E"))
        assertEquals(Pair(listOf("D", "E", "F"), 6.0), graph2.dijkstras("D", "F"))
        assertEquals(null, graph2.dijkstras("B", "A"))
        assertEquals(null, graph2.dijkstras("E", "C"))
    }
}