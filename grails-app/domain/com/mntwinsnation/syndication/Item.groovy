package com.mntwinsnation.syndication

import org.joda.time.LocalDateTime

class Item {

    String title
    String url
    String description
    String guid
    LocalDateTime pubDate

    LocalDateTime dateCreated
    LocalDateTime lastUpdated

    static belongsTo = [channel : Channel]

    static constraints = {
        title(maxSize: 500)
        description(maxSize: 5000)
        url(maxSize: 1000)
        guid(maxSize: 1000, unique: true)
        description(nullable: true)
    }
}
