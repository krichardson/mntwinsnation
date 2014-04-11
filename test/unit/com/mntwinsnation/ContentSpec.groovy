package com.mntwinsnation

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Content)
@Mock(Content)
class ContentSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def 'generate a key based on content title'() {

        given:
        Content content = new Content(title: "This is my title")

        when:
        content.generateKey()

        then:
        assert content.key == 'this-is-my-title'

    }

    @Unroll
    def 'generate a key for #suggestion with tryCount #tryCount'() {

        given:
        Content content = new Content()

        when:
        content.generateKey(suggestion, tryCount)

        then:
        assert content.key == expectedKey

        where:
        suggestion          | tryCount || expectedKey
        'Test Title'        | null     || 'test-title'
        'Test Title'        | 0        || 'test-title'
        'Test Title'        | 1        || 'test-title-1'
        'Test Title'        | 2        || 'test-title-2'
        'test-title'        | null     || 'test-title'
        'test  title'       | null     || 'test-title'
        'test title-test-1' | 2        || 'test-title-test-1-2'
        'a'*51              | null     || 'a'*Content.KEY_SIZE_LIMIT
        'a'*51              | 1        || 'a'*(Content.KEY_SIZE_LIMIT-2) + '-1'

    }
}
