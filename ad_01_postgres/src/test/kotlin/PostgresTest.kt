import org.junit.Test
import java.sql.DriverManager
import java.util.*

class PostgresTest {

    @Test
    fun pg() {
        java.lang.Class.forName("org.postgresql.Driver")

        val props = Properties().apply {
            setProperty("user", "root")
            setProperty("password", "root_root")
            setProperty("ssl", "false")
        }

        DriverManager.getConnection("jdbc:postgresql://localhost/test", props).use { conn ->
            conn.createStatement().use { st ->
                st.executeQuery("create vertex type Hero")
                st.executeQuery("create vertex Hero set name = 'Jay', lastName = 'Miner'")
                conn.prepareStatement("create vertex Hero set name = ?, lastName = ?").apply {
                    setString(1, "Rocky")
                    setString(2, "Balboa")
                    execute()
                    close()
                }
                st.executeQuery("SELECT * FROM Hero").use { rs ->  // Type and property names are case sensitive!
                    while (rs.next()) {
                        println(("First Name: " + rs.getString(1)).toString() + " - Last Name: " + rs.getString(2))
                    }
                }
            }
        }
    }
}
