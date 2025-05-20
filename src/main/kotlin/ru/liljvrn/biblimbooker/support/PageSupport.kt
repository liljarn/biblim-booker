package ru.liljvrn.biblimbooker.support

import org.springframework.data.domain.PageRequest

const val PAGE_SIZE = 20

private fun getPageRequest(page: Int) = PageRequest.of(page, PAGE_SIZE)

fun <T> pageRequest(page: Int, body: (PageRequest) -> T) = body(getPageRequest(page))
