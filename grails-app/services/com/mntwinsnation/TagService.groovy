package com.mntwinsnation

import com.mntwinsnation.semantics.OpenCalaisEntity
import com.mntwinsnation.semantics.OpenCalaisService
import grails.transaction.Transactional

@Transactional
class TagService {

    OpenCalaisService openCalaisService

    void createTags(Content content, String text) {
        List<OpenCalaisEntity> entities = openCalaisService.analyzeContent(text)
        entities.each { OpenCalaisEntity entity ->
            Tag tag = new Tag(content: content, type: entity.type, name: entity.name, relevance: entity.relevance)
            tag.save()
        }
    }
}
