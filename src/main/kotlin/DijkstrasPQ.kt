package org.example

class DijkstrasPQ<T>: MinPriorityQueue<T> {
    private val heap = MinHeap<T>()

    override fun isEmpty(): Boolean {
        return heap.isEmpty()
    }

    override fun next(): T? {
        return heap.getMin()
    }

    /**
     * Adjust the priority of the given element or add it if it isn't in the queue
     * @param elem whose priority should change
     * @param newPriority the priority to use for the element the lower the priority the earlier the element int the
     * order.
     */
    override fun adjustPriority(elem: T, newPriority: Double) {
        if (heap.contains(elem)) {
            heap.adjustHeapNumber(elem, newPriority)
        } else {
            addWithPriority(elem, newPriority)
        }

    }

    override fun addWithPriority(elem: T, priority: Double) {
        heap.insert(elem, priority)
    }
}