package com.github.helgekrueger.geography

import spock.lang.Specification

class TrackSpec extends Specification {

    def 'can transform simple data to box'() {
        setup:
        def data = [
            [lat: 10, lon: 20],
            [lat: 20, lon: 30],
        ]

        def track = new Track(data: data)

        expect:
        track.dataInUnitBox == [[x: 1, y: 0, lat: 10, lon: 20], [x: 0, y: 1, lat: 20, lon: 30]]
    }

    def 'path'() {
        setup:
        def data = [
            [lat: 10, lon: 20],
            [lat: 20, lon: 30],
        ]

        def track = new Track(data: data)

        when:
        def shape = track.shapeInBox(100, 100)

        println shape
        then:
        def bounds = shape.getBounds2D()
        bounds.height == 100
        bounds.width == 100
    }
}
