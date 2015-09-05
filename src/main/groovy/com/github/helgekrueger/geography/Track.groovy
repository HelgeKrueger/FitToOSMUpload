package com.github.helgekrueger.geography

import java.awt.Color
import java.awt.geom.Path2D.Double as Path2D
import java.awt.geom.Ellipse2D.Double as Ellipse
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.joda.time.Duration

class Track {

    def data

    def getDataInUnitBox() {
        def boundingBox = computeBounds()
        def lengthLon = boundingBox.maxLon - boundingBox.minLon
        def lengthLat = boundingBox.maxLat - boundingBox.minLat

        data.collect{ point ->
            point + [
                x: (boundingBox.maxLon - point.lon) / lengthLon,
                y: (point.lat - boundingBox.minLat) / lengthLat,
            ]
        }
    }

    private computeBounds() {
        def initialValue = [
            maxLon: data[0].lon,
            minLon: data[0].lon,
            maxLat: data[0].lat,
            minLat: data[0].lat,
        ]
        data.inject(initialValue) { bounds, element ->
            [
                maxLon: Math.max(element.lon, bounds.maxLon),
                minLon: Math.min(element.lon, bounds.minLon),
                maxLat: Math.max(element.lat, bounds.maxLat),
                minLat: Math.min(element.lat, bounds.minLat),
            ]
        }
    }

    def pointsInBox(width, height) {
        dataInUnitBox.collect{ it + [x: it.x * width, y: it.y * height] }
    }

    def shapeInBox(width, height) {
        def points = pointsInBox(width, height)
        def path = new Path2D()
        path.moveTo(points.first().x, points.first().y)

        points.each{ point ->
            path.lineTo(point.x, point.y)
        }

        path
    }

    def writeToPng() {
        def width = 100
        def height = 100
        def radius = 5
        def shape = shapeInBox(width, height)
        def filteredData = filterBySecondIncrement(100, 100, 5).reverse()

        filteredData.eachWithIndex{ point, index ->
            def bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            def graphics = bi.createGraphics()

            graphics.setBackground(Color.white)
            graphics.clearRect(0, 0, width, height)

            graphics.setColor(Color.black)
            graphics.draw(shape)

            graphics.setColor(Color.red)
            def cornerX = point.x - radius
            def cornerY = point.y - radius
            graphics.draw(new Ellipse(cornerX, cornerY, 2 * radius, 2 * radius))

            def file = new File(filenameForIndex(index))
            ImageIO.write(bi, 'png', file)
        }
    }

    private filenameForIndex(index) {
        sprintf('out%06d.png', index)
    }

    def filterBySecondIncrement(width, height, step) {
        def startTime = data.first().time
        pointsInBox(width, height).findAll{ differenceInSeconds(it.time, startTime) % step == 0 }
    }

    private differenceInSeconds(time1, time2) {
        new Duration(time2, time1).standardSeconds as Integer
    }
}
