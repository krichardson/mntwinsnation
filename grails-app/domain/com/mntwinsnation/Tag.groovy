package com.mntwinsnation

class Tag {

    String type
    String name
    Double relevance

    static belongsTo = [content : Content]

    static constraints = {
    }
}
