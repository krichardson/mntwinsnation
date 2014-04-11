package com.mntwinsnation.syndication

import com.mntwinsnation.Content
import org.joda.time.LocalDateTime

class Item extends Content {

    String url
    String description
    String guid
    LocalDateTime pubDate

    static belongsTo = [channel : Channel]

    static mapping = {
        //no idea why this one isn't getting created correctly like all the other LocalDateTimes
        //pubDate sqlType: 'timestamp'
    }

    static constraints = {
        title(maxSize: 500)
        description(maxSize: 5000)
        url(maxSize: 1000)
        guid(maxSize: 1000, unique: true)
        pubDate(nullable: true)
        description(nullable: true)
    }
}
