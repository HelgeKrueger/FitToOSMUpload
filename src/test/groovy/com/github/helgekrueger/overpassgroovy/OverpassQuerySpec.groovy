package com.github.helgekrueger.overpassgroovy

import spock.lang.Specification

class OverpassQuerySpec extends Specification {

    def 'can init'() {
        expect:
        new OverpassQuery()
    }

    def 'can formulate query by name'() {
        setup:
        def overpass = new OverpassQuery()

        expect:
        overpass.queryString{
            node {
                name 'Haar'
            }
        } == '[out:json];node["name"="Haar"];out;'
    }

    def 'can use bounding box'() {
        setup:
        def overpass = new OverpassQuery()

        expect:
        overpass.queryString{
            node {
                highway 'bus_stop'
                shelter 'yes'
                box 50.7, 7.1, 50.8, 7.25
            }
        } == '[out:json];node["highway"="bus_stop"]["shelter"="yes"](50.7,7.1,50.8,7.25);out;'
    }

    def 'exist condition'() {
        setup:
        def overpass = new OverpassQuery()

        expect:
        overpass.queryString{
            node {
                exist 'highway'
            }
        } == '[out:json];node["highway"];out;'
    }

    @spock.lang.Ignore
    def 'can query'() {
        setup:
        def overpass = new OverpassQuery()

        when:
        def result = overpass.query{
            way {
                name 'Jagdfeldring'
            }
        }

        then:
        result.elements.size()
    }
}
