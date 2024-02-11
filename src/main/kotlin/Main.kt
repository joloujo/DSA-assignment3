package org.example

import java.io.File

/**
 * Find the solution to [Project Euler problem 83](https://projecteuler.net/problem=83) for a matrix text file in csv
 * format
 * @param matrixFilepath the filepath for the matrix file
 * @return the cost of the shortest path
 */
fun euler83(matrixFilepath: String): Int {
    /** The text from the matrix file */
    val text = File(matrixFilepath).readLines()
    /** The matrix of integers */ // from the lines of text
    val matrix = createMatrix(text)
    /** The graph to represent all legal moves */ // with vertices being locations in the matrix and edge costs being
    // the value of the matrix entry they point to
    val graph = createGraph(matrix)
    /** The bottom right vertex in the graph */
    val bottomRight = Pair(matrix.count()-1, matrix[0].count()-1)
    /** The path with the least cost from the top left to bottom right, and it's cost*/
    val shortestPath = graph.dijkstras(Pair(0, 0), bottomRight)!!

    /** The final cost of the path */ // because Dijkstra's didn't include the cost of the source vertex
    val cost = shortestPath.second.toInt() + matrix[0][0]
    return cost
}

fun createMatrix(text: List<String>): List<List<Int>> {
    /** The matrix of integers from the lines of text */
    val matrix: MutableList<List<Int>> = mutableListOf()

    // For each line of text
    for (line in text) {
        // Make sure it doesn't have leading or trailing whitespace
        val trimmedLine = line.trim()

        // If it's empty, ignore it
        if (trimmedLine == "") {
            continue
        }

        // Get the individual numbers and convert them to integers
        val words = trimmedLine.split(Regex(","))
        val row = words.map {it.toInt()}

        // Add the row of integers to the matrix
        matrix.add(row)
    }

    return matrix
}

fun createGraph(matrix: List<List<Int>>): GraphAL<Pair<Int, Int>> {
    /** The graph to represent all legal moves. Vertices are locations in the matrix and are identified by their row and
     * column. Edge costs are the value of the matrix entry they point to*/
    val graph = GraphAL<Pair<Int, Int>>()

    // For each value in the matrix add a vertex to the graph
    for (row in 0..<matrix.count()) {
        for (col in 0..<matrix[row].count()) {
            graph.addVertex(Pair(row, col))
        }
    }

    /** The possible directions to go, down, up, right, and left */
    val directions = setOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))

    // For each value in the matrix add a vertex to the graph
    for (row in 0..<matrix.count()) {
        for (col in 0..<matrix[row].count()) {
            // For each possible direction to travel
            for (direction in directions) {
                // Add an edge if possible, with the cost being the value of the entry it points to
                val toRow = row + direction.first
                val toCol = col + direction.second
                val cost = matrix.getOrNull(toRow)?.getOrNull(toCol)
                if (cost != null) {
                    graph.addEdge(Pair(row, col), Pair(toRow, toCol), cost.toDouble())
                }
            }
        }
    }

    return graph
}

fun main() {
    // Solve Project Euler problem 83
    val filepath = "src/main/kotlin/0083_matrix.txt"
    print(euler83(filepath))
}