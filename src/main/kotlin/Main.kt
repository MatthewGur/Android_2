import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

fun main() = runBlocking {
    val websites = listOf(
        "https://www.google.com",
        "https://www.facebook.com",
        "https://www.github.com",
        "https://www.twitter.com",
        "https://www.instagram.com",
        "https://www.yahoo.com",
        "https://www.linkedin.com",
        "https://www.stackoverflow.com",
        "https://www.reddit.com",
        "https://www.microsoft.com"
    )

    val jobs = websites.map { url ->
        launch {
            val isAvailable = checkWebsite(url)
            val status = if (isAvailable) "доступен" else "недоступен"
            println("Сайт $url $status")
        }
    }

    jobs.forEach { it.join() }
}

fun checkWebsite(url: String): Boolean {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.responseCode == HttpURLConnection.HTTP_OK
    } catch (e: Exception) {
        false
    }
}
