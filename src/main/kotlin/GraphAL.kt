package org.example

class GraphAL<VertexType>: Graph<VertexType> {
    private val adjacencyList: MutableMap<VertexType, MutableMap<VertexType, Double>> = mutableMapOf()

    override fun addVertex(vertex: VertexType) {
        if (vertex in adjacencyList) return
        adjacencyList[vertex] = mutableMapOf()
    }

    override fun getVertices(): Set<VertexType> {
        return adjacencyList.keys
    }

    override fun addEdge(from: VertexType, to: VertexType, cost: Double) {
        if (to !in adjacencyList) return
        adjacencyList[from]?.put(to, cost)
    }

    override fun getEdges(from: VertexType): Map<VertexType, Double> {
        return adjacencyList[from]?.toMap() ?: mapOf()
    }

    override fun clear() {
        adjacencyList.clear()
    }

    /**
     * Finds the shortest path from the [source] vertex to the [goal] vertex using Dijkstra's algorithm
     * @param source the vertex to start from
     * @param goal the vertex to search for
     * @return a [Pair] of the shortest path ([List]) and the shortest distance ([Double]), or `null` if the goal is
     * unreachable
     */
    fun dijkstras(source: VertexType, goal: VertexType): Pair<List<VertexType>, Double>? {
        // If the goal or source aren't in the graph, then there is no path between them
        if (!adjacencyList.contains(source) || !adjacencyList.contains(goal)) {
            return null
        }

        /** The queue of vertices to visit, with priority based on distance */
        val queue = DijkstrasPQ<VertexType>()
        /** The previous vertex for the shortest path for every vertex in the graph */
        val prev: MutableMap<VertexType, VertexType?> = mutableMapOf()
        /** The distance from the source vertex for every vertex in the graph */
        val dist: MutableMap<VertexType, Double> = mutableMapOf()

        // The previous vertex for every vertex is null, and the distance to every vertex is infinite, because we
        // haven't gotten to it yet
        adjacencyList.keys.forEach {
            prev[it] = null
            dist[it] = Double.POSITIVE_INFINITY
        }

        // The distance to the source is 0 and the vertices connected to the source need to be checked
        dist[source] = 0.0
        queue.addWithPriority(source, 0.0)

        while (!queue.isEmpty()) {
            /** The next vertex to check */
            val u = queue.next()!!

            // If the next vertex to check is the goal, the algorithm is finished
            if (u == goal) {
                break
            }

            // For each vertex connected to the next vertex to check
            getEdges(u).forEach {
                /** The distance to the connected vertex */ // is the distance to the current vertex plus the cost of
                // the edge
                val alt = dist[u]!! + it.value

                // If this is a shorter path than the current shortest path to the connected vertex
                if (alt < dist[it.key]!!) {
                    // update the distance, queue priority (or add to queue), and previous vertex
                    dist[it.key] = alt
                    queue.adjustPriority(it.key, alt)
                    prev[it.key] = u
                }
            }
        }

        /** The shortest distance to the goal */ // is the distance to the goal vertex when the algorithm is finished
        val shortestDist = dist[goal]!!
        // If the shortest distance to the goal is infinity, then the goal is unreachable
        if (shortestDist == Double.POSITIVE_INFINITY) {
            return null
        }

        /** The shortest path to the goal */ // starts at the goal
        val shortestPath: MutableList<VertexType> = mutableListOf(goal)
        var next = goal

        // Traverse backwards using prev to find the shortest path. Since the goal has a non-infinite distance, we know
        // we will reach the source.
        while (next != source) {
            // the next vertex to traverse is the previous vertex of the current vertex
            next = prev[next]!!
            // Add vertices to the start of the shortest path as we go
            shortestPath.add(0, next)
        }

        return Pair(shortestPath, shortestDist)
    }
}