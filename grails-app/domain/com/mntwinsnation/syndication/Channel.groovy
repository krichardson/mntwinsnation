package com.mntwinsnation.syndication

import org.joda.time.LocalDateTime

class Channel {

    String title
    String description
    String webUrl
    String feedUrl
    Boolean active
    String urlKey
    String contactEmail
    LocalDateTime latestItemTime

    LocalDateTime dateCreated
    LocalDateTime lastUpdated

    static hasMany = [items: Item]

    static constraints = {
        description(nullable: true, maxSize: 5000)
        contactEmail(nullable: true)
        latestItemTime(nullable: true)
        urlKey(unique: true)
    }
}
