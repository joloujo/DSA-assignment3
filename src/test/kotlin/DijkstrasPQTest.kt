import org.example.DijkstrasPQ
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DijkstrasPQTest {

    @Test
    fun testDijkstrasPQ() {
        val priorityQueue = DijkstrasPQ<String>()

        // Make sure the queue starts empty
        assertEquals(true, priorityQueue.isEmpty())

        // Make sure adding works
        priorityQueue.addWithPriority("B", 2.0)
        priorityQueue.addWithPriority("C", 3.0)
        priorityQueue.addWithPriority("A", 1.0)
        priorityQueue.addWithPriority("D", 4.0)

        // Now there should be elements in the queue
        assertEquals(false, priorityQueue.isEmpty())

        // Make sure elements are given back in the right order
        assertEquals("A", priorityQueue.next())
        assertEquals("B", priorityQueue.next())
        assertEquals("C", priorityQueue.next())
        assertEquals("D", priorityQueue.next())

        // Now there should be no more elements in the queue
        assertEquals(true, priorityQueue.isEmpty())

        // Make sure the priority of elements can be adjusted
        priorityQueue.addWithPriority("A", 3.0)
        priorityQueue.addWithPriority("B", 2.0)
        priorityQueue.addWithPriority("C", 1.0)

        priorityQueue.adjustPriority("A", 1.0)
        priorityQueue.adjustPriority("C", 3.0)

        assertEquals("A", priorityQueue.next())
        assertEquals("B", priorityQueue.next())
        assertEquals("C", priorityQueue.next())
    }
}