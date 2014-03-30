package com.mntwinsnation.syndication

import grails.converters.JSON

class FeedImportController {

    FeedImportService feedImportService

    def latest() {
        List<Item> affectedItems = feedImportService.importLatestItems()

        render affectedItems.sort { it.channel.title }.collect {
            [title: it.title, url: it.url, pubDate: it.pubDate.toDate()]
        } as JSON

    }
}
