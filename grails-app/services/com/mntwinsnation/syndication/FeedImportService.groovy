package com.mntwinsnation.syndication

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader
import grails.transaction.Transactional
import org.joda.time.LocalDateTime

@Transactional
class FeedImportService {

    List<Item> importLatestItems() {
        log.info("Importing latest feed items")
        List<Item> allAffectedItems = []
        getChannelsToCheck().each { Channel channel ->
            List<Item> affectedItems = importChannelItems(channel)
            log.info("Imported/Updated ${affectedItems.size()} items for ${channel.title} (${channel.id})")
            allAffectedItems.addAll(affectedItems)
        }
        return allAffectedItems
    }

    private List<Channel> getChannelsToCheck() {
        return Channel.findAllByActive(true)
    }

    private SyndFeed fetchFeed(Channel channel) {
        log.info("Fetching feed for ${channel.feedUrl}")
        SyndFeedInput input = new SyndFeedInput()
        URL feedUrl = new URL(channel.feedUrl)
        SyndFeed feed = input.build(new XmlReader(feedUrl))
        return feed
    }

    private List<Item> importChannelItems(Channel channel) {

        SyndFeed feed = fetchFeed(channel)
        List<Item> affectedItems = []
        LocalDateTime newLatestItemTime = null

        for (SyndEntry entry : feed.entries as List<SyndEntry>) {
            LocalDateTime pubDate = getAPubDate(entry, feed)
            if (pubDate && pubDate > channel.latestItemTime) {
                newLatestItemTime = newLatestItemTime ?: pubDate
                log.info("Importing channel ${channel.id} item ${entry.uri}")
                Item item = Item.findByGuid(entry.uri)
                if (!item) {
                    item = new Item(channel: channel, guid: entry.uri)
                } else {
                    log.info("${item.guid} already exists, so will update")
                }
                item.with {
                    it.pubDate = pubDate
                    it.title = entry.title
                    it.description = entry.description.value
                    it.url = entry.link
                }
                if(item.save()) {
                    affectedItems << item
                } else {
                    log.warn("Unable to save item ${item.guid}")
                    item.errors.each { Error error ->
                        log.warn(error.localizedMessage)
                    }
                }

            } else {
                log.info("Stopping entry check for channel ${channel.id} " +
                        "because ${pubDate} <= ${channel.latestItemTime} or no pubDate could be found")
                break
            }
        }
        if (newLatestItemTime) {
            channel.latestItemTime = newLatestItemTime
            channel.save()
        }
        return affectedItems
    }

    private LocalDateTime getAPubDate(SyndEntry entry, SyndFeed feed) {
        if (entry.publishedDate) {
            return new LocalDateTime(entry.publishedDate.time)
        } else if (entry.updatedDate) {
            return new LocalDateTime(entry.updatedDate.time)
        } else if (feed.publishedDate) {
            return new LocalDateTime(feed.publishedDate.time)
        }
        log.warn("Unable to parse a pubDate for feed: ${feed.uri}; entry: ${entry.uri}")
        return null
    }
}