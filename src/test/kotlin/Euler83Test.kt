import org.example.euler83
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Euler83Test {

    @Test
    fun testEuler83() {
        // Test the test case from Project Euler problem 83
        val filepath = "src/test/kotlin/test_matrix.txt"
        assertEquals(2297, euler83(filepath))
    }

}