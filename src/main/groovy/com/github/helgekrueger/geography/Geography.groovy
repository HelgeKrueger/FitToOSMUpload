package com.github.helgekrueger.geography

import com.spatial4j.core.context.SpatialContext

class Geography {
    def spatialContext = SpatialContext.GEO

    def makePoint(lat, lon) {
        spatialContext.makePoint(lon, lat)
    }

    def calcDistanceInKilometer(from, to) {
        degreeToKilometer(spatialContext.calcDistance(from, to))
    }

    def makeBoundingBox(point, distance) {
        spatialContext.makeCircle(point,  kilometerToDegree(distance)).boundingBox
    }

    def calcBoundingBoxCoordinates(point, distance) {
        def boundingBox = makeBoundingBox(point, distance)
        [boundingBox.maxY, boundingBox.maxX, boundingBox.minY, boundingBox.minX]
    }

    private kilometerToDegree(kilometer) {
        kilometer * 360 / 40000
    }

    private degreeToKilometer(degree) {
        degree / 360 * 40000
    }
}
