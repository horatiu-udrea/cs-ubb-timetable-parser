package ro.horatiu_udrea.cs_ubb_timetable_parser.scraper

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import it.skrape.core.htmlDocument
import it.skrape.selects.DocElement
import ro.horatiu_udrea.cs_ubb_timetable_parser.TimetableSet
import ro.horatiu_udrea.cs_ubb_timetable_parser.models.*
import java.net.URI

context(ro.horatiu_udrea.cs_ubb_timetable_parser.ProgramConfiguration)
class WebsiteTimetableScraper(private val httpClient: HttpClient) : TimetableScraper {

    override suspend fun scrapeTimetableIndex(timetableSet: TimetableSet): ScrapedIndex? {
        val url = getTimetableSetIndexUrl(timetableSet)
        val response = httpClient.get(url)
        if (response.status != HttpStatusCode.OK) return null

        // The charset from the response is wrong or nonexistent, so we're overriding it here
        val decoder = charset("ISO-8859-2").newDecoder()
        val webpage = httpClient.get(url).body<ByteReadPacket>().let(decoder::decode)

        return htmlDocument(webpage) {
            val bachelors = findFirst("table") { scrapeTableItems() }
            val masters = findSecond("table") { scrapeTableItems() }

            ScrapedIndex(bachelors, masters)
        }
    }

    override suspend fun scrapeTimetable(timetableSet: TimetableSet, link: String): ScrapedTimetable {
        val url = URI(getTimetableSetIndexUrl(timetableSet)).resolve(link).toString()
        // The charset from the response is wrong or nonexistent, so we're overriding it here
        val decoder = charset("ISO-8859-2").newDecoder()
        val webpage = httpClient.get(url).body<ByteReadPacket>().let(decoder::decode)

        return htmlDocument(webpage) {
            val groups = findAll("h1").asSequence().drop(1).map { it.text }
            val timetables = findAll("table").asSequence().map { table ->
                runCatching { table.findAll("tr:not(:has(th))") }.getOrDefault(emptyList()).map { row ->
                    val cells = row.findAll("td")
                    val day = cells[0].text
                    val time: String = cells[1].text
                    val frequency = cells[2].text
                    val (room, roomLink) = cells[3].getLinkFromElement()
                    val formation = cells[4].text
                    val type = cells[5].text
                    val (subject, subjectLink) = cells[6].getLinkFromElement()
                    val (teacher, teacherLink) = cells[7].getLinkFromElement()

                    ScrapedClassInfo(
                        day,
                        time,
                        frequency,
                        room,
                        roomLink,
                        formation,
                        type,
                        subject,
                        subjectLink,
                        teacher,
                        teacherLink
                    )
                }

            }
            val allGroupTimetables = groups.zip(timetables) { groupName, timetable ->
                ScrapedGroupTimetable(groupName, timetable)
            }.toList()

            ScrapedTimetable(allGroupTimetables)
        }
    }

    private fun DocElement.scrapeTableItems() = findAll("tr:not(:has(th))").map { row ->
        val cells = row.findAll("td")
        val name = cells.first().text
        val scrapedYears = cells.asSequence()
            .drop(1)
            .filter { it.text.isNotEmpty() }
            .map { cell ->
                val (linkText, link) = cell.getLinkFromElement()
                ScrapedYear(linkText, link)
            }.toList()

        ScrapedTableItem(name, scrapedYears)
    }

    private fun DocElement.getLinkFromElement(): Pair<String, String> =
        findFirst("a") {
            text to attribute("href")
        }
}
