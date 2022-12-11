import org.junit.Ignore
import org.junit.Test
import redis.clients.jedis.JedisPooled
import kotlin.test.assertEquals


class RedisTest {

    @Test
    fun redisVal() {
        val jedis = JedisPooled("localhost", 6379)

        jedis.set("clientName", "Jedis")

        val cn: String? = jedis.get("clientName")
        assertEquals("Jedis", cn)
    }

    @Test
    @Ignore
    fun redisSet() {
        val jedis = JedisPooled("localhost", 6379)

        jedis.sadd("planets", "Venus")
        jedis.sadd("planets", "Mars")

        val pl = jedis.smembers("planets").sorted()
        assertEquals(listOf("Mars", "Venus"), pl)
    }

    @Test
    @Ignore
    fun redisHash() {
        val jedis = JedisPooled("localhost", 6379)

        jedis.hset("fruits", "apple","yes")
        jedis.hset("fruits", "potato","no")

        val fr = jedis.hgetAll("fruits")
        assertEquals(mapOf("apple" to "yes", "potato" to "no"), fr)
    }
}
