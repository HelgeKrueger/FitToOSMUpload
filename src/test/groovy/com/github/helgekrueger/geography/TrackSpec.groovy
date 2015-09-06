package com.github.helgekrueger.geography

import org.joda.time.DateTime
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
        track.dataInUnitBox == [[x: 0, y: 1, lat: 10, lon: 20], [x: 1, y: 0, lat: 20, lon: 30]]
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
        def bounds = shape.bounds2D
        bounds.height == 100
        bounds.width == 100
    }

    def 'get second increments'() {
        setup:
        def now = new DateTime()
        def step = 5
        def number = 20
        def data = (0..(step * number)).collect{ [lat: it, lon: it, time: now.plusSeconds(it)] }

        def track = new Track(data: data)

        def expected = (0..number).collect{ now.plusSeconds(it * step) }

        expect:
        track.filterBySecondIncrement(1, 1, step)*.time == expected
    }
}
