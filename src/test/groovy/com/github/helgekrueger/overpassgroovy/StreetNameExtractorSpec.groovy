package com.github.helgekrueger.overpassgroovy

import spock.lang.Specification

class StreetNameExtractorSpec extends Specification {
    
    def 'retrieveStreetNames - returns an empty dataset on empty input'() {
        setup:
        def overpassQuery = Mock(OverpassQuery)
        def extractor = new StreetNameExtractor(overpassQuery: overpassQuery)

        expect:
        extractor.retrieveStreetNames([]) == []
    }

    def 'retrieveStreetNames - calls overpassQuery'() {
        setup:
        def overpassQuery = Mock(OverpassQuery)
        def extractor = new StreetNameExtractor(overpassQuery: overpassQuery)

        def point = [lat: 1, lon: 2]

        when:
        extractor.retrieveStreetNames([point])

        then:
        1 * overpassQuery.query(_) >> [elements: []]
    }
}
