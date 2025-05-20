package ru.liljvrn.biblimbooker.infrastructure.http

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import ru.liljvrn.biblimbooker.domain.clients.RohanClient
import ru.liljvrn.biblimbooker.domain.models.dto.PdfBookRequest
import ru.liljvrn.biblimbooker.domain.models.dto.ReportRequest
import ru.liljvrn.biblimbooker.support.factories.WebClientFactory

@Component
class RohanHttpClient(factory: WebClientFactory) : RohanClient {

    val webClient by lazy {
        factory.createWebClient("rohan")
    }

    override fun sendPdfBook(pdfBook: PdfBookRequest) {
        val body = buildMultipartFile(pdfBook)
        webClient.post()
            .uri("/api/v1/internal/pdfbook")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(body))
            .retrieve()
            .toBodilessEntity()
            .block()
    }

    override fun createReport(report: ReportRequest) {
        webClient.post()
            .uri("/api/v1/internal/reports")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(report)
            .retrieve()
            .toBodilessEntity()
            .block()
    }

    private fun buildMultipartFile(pdfBook: PdfBookRequest): MultiValueMap<String, HttpEntity<*>> {
        val builder = MultipartBodyBuilder().apply {
            part("bookId", pdfBook.bookId)
            part("book", InputStreamResource(pdfBook.book)).apply {
                filename(pdfBook.bookId.toString())
                contentType(MediaType.APPLICATION_PDF)
            }
        }
        return builder.build()
    }
}
