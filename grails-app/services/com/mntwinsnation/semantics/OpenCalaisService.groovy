package com.mntwinsnation.semantics

import com.mntwinsnation.retrofit.client.OpenCalaisApi

class OpenCalaisService {

    OpenCalaisApi openCalaisApi

    private static final supportedEntityTypes = ['City', 'Company', 'Facility', 'Holiday',
                                                 'Organization', 'Person', 'ProvinceOrState',
                                                 'RadioProgram', 'RadioStation', 'SportsEvent',
                                                 'SportsGame', 'SportsLeague', 'TVStation']

    List<OpenCalaisEntity> analyzeContent(String content, String contentType = 'text/html') {

        Map resp = openCalaisApi.analyzeContent(content, contentType)

        List<OpenCalaisEntity> entities = []

        resp.each { String key, Map data ->
            if (data.containsKey('_typeGroup') && data.get('_typeGroup') == 'entities') {
                OpenCalaisEntity entity = parseEntity(data)
                if (entity) {
                    entities << parseEntity(data)
                }
            }
        }
        return entities
    }

    private static OpenCalaisEntity parseEntity(Map data) {

        String type = data['_type']
        String name = data['name']
        Double relevance = data['relevance'] as Double

        if (type && name && relevance && supportedEntityTypes.contains(type)) {
            return new OpenCalaisEntity(type: type, name: name, relevance: relevance)
        }
        return null
    }

}
