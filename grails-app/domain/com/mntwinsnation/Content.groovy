package com.mntwinsnation

import org.joda.time.LocalDateTime

class Content {

    String title
    String key
    Boolean published = true
    LocalDateTime datePublished

    LocalDateTime dateCreated
    LocalDateTime lastUpdated

    public static final int KEY_SIZE_LIMIT = 50

    static constraints = {
        key(unique: true)
        datePublished(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
    }

    def beforeInsert() {
        updateDatePublished()
    }

    def beforeUpdate() {
        updateDatePublished()
    }

    private void updateDatePublished() {
        if (published && !datePublished) {
            datePublished = new LocalDateTime()
        } else if (!published) {
            datePublished = null
        }
    }

    public void generateKey(String suggestion = null, Integer tryCount = 0) {
        //Make lower case, remove special characters and replace spaces with '-'
        String keyBase = (suggestion ?: title)
                .replaceAll(/[^a-zA-Z0-9\s\-]/, '')
                .replaceAll(/\s+/, '-')
                .toLowerCase()
        if (keyBase) {
            //Limit the size of the key, making room for the unique tryCount at the end
            int charLimit = tryCount ? KEY_SIZE_LIMIT - (tryCount.toString().length() + 1) : KEY_SIZE_LIMIT
            if (keyBase.length() > charLimit) {
                keyBase = keyBase.substring(0, charLimit)
            }
            String actualKey = tryCount ? "${keyBase}-${tryCount}" : keyBase
            //If the generated key already exists on another piece of content, try to make it unique
            Content match = Content.findByKey(actualKey)
            if(match && match.id != this.id) {
                log.warn("Attempted key ${actualKey} already exists. Will try to make a unique variation")
                generateKey(keyBase, tryCount+1)
            } else {
                this.key = actualKey
            }
        } else {
            //If there's no title or suggestion, just generate a UUID
            this.key = UUID.randomUUID()
        }
    }
}
