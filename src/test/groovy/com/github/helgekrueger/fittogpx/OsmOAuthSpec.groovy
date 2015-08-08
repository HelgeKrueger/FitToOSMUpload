package com.github.helgekrueger.fittogpx

import spock.lang.Specification

class OsmOAuthSpec extends Specification {

    def 'reading file oAuthIsNotConfigured'() {
        setup:
        def tmpFile = File.createTempFile('test', 'properties')
        tmpFile.deleteOnExit()

        when:
        def osm = new OsmOAuth(tmpFile.getAbsolutePath())

        then:
        ! osm.isOauthConfigured()
    }

    def 'reading file oAuth is configured'() {
        setup:
        def tmpFile = File.createTempFile('test', 'properties')
        tmpFile.deleteOnExit()
        
        def props = new Properties()
        ['consumerKey', 'consumerSecret', 'consumerToken', 'consumerTokenSecret'].each{ props.setProperty(it, it) }
        props.store(new FileOutputStream(tmpFile), null)

        when:
        def osm = new OsmOAuth(tmpFile.getAbsolutePath())

        then:
        osm.isOauthConfigured()
    }

    def 'configureHttpBuilder'() {
        setup:
        def tmpFile = File.createTempFile('test', 'properties')
        tmpFile.deleteOnExit()
        
        def props = new Properties()
        ['consumerKey', 'consumerSecret', 'consumerToken', 'consumerTokenSecret'].each{ props.setProperty(it, it) }
        props.store(new FileOutputStream(tmpFile), null)

        def args
        def fakeHttpBuilder = [
            auth: [
                oauth: { a, b, c, d ->
                    args = [a,b,c,d]
                },
            ],
        ]

        when:
        def osm = new OsmOAuth(tmpFile.getAbsolutePath())
        osm.configureHttpBuilder(fakeHttpBuilder)

        then:
        args == ['consumerKey', 'consumerSecret', 'consumerToken', 'consumerTokenSecret']
    }
}
