package ru.liljvrn.biblimbooker.domain.clients

import ru.liljvrn.biblimbooker.domain.models.dto.PdfBookRequest
import ru.liljvrn.biblimbooker.domain.models.dto.ReportRequest

interface RohanClient {

    fun sendPdfBook(pdfBook: PdfBookRequest)

    fun createReport(report: ReportRequest)
}
