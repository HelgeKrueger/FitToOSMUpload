package com.github.helgekrueger.geography

import spock.lang.Specification

class GeographySpec extends Specification {

    def 'distance between two points'() {
        setup:
        def geo = new Geography()
        def munich = geo.makePoint(48.139126, 11.580186)
        def berlin = geo.makePoint(52.523403, 13.411400)
        def london = geo.makePoint(51.500153, -0.126236)

        expect:
        Math.abs(geo.calcDistanceInKilometer(munich, berlin) - 504) < 1
        Math.abs(geo.calcDistanceInKilometer(munich, london) - 918) < 1
    }

    def 'bounding box'() {
        setup:
        def geo = new Geography()
        def munich = geo.makePoint(48.139126, 11.580186)

        when:
        def box = geo.makeBoundingBox(munich, 1)
        def topLeft = geo.makePoint(box.maxY, box.minX)
        def topRight = geo.makePoint(box.maxY, box.maxX)
        def bottomRight = geo.makePoint(box.minY, box.maxX)

        then:
        Math.abs(geo.calcDistanceInKilometer(topLeft, topRight) - 2) < 0.001
        Math.abs(geo.calcDistanceInKilometer(topLeft, bottomRight) ** 2 - 8) < 0.001
    }

    def 'calcBoundingBoxCoordinates'() {
        setup:
        def geo = new Geography()
        def munich = geo.makePoint(48.139126, 11.580186)

        when:
        def (north, east, south, west) = geo.calcBoundingBoxCoordinates(munich, 1)

        then:
        Math.abs(geo.calcDistanceInKilometer(geo.makePoint(north, east), geo.makePoint(north, west)) - 2) < 0.001
        Math.abs(geo.calcDistanceInKilometer(geo.makePoint(north, east), geo.makePoint(south, west)) ** 2 - 8) < 0.001
    }
}
