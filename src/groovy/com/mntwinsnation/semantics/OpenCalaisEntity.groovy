package com.mntwinsnation.semantics

class OpenCalaisEntity {

    String type
    String name
    Double relevance

    String toString() {
        "${type}: ${name} (${relevance})"
    }

}
