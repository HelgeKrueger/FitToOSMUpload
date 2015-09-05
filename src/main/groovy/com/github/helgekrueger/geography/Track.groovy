package com.github.helgekrueger.geography

import java.awt.Color
import java.awt.geom.Path2D.Double as Path2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

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
        dataInUnitBox.collect{ [x: it.x * width, y: it.y * height] }
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
        def bi = new BufferedImage(101, 101, BufferedImage.TYPE_INT_ARGB)
        def graphics = bi.createGraphics()

        graphics.setBackground(Color.white)
        graphics.setColor(Color.black)
        
        graphics.draw(shapeInBox(100, 100))

        def file = new File('out.png')
        ImageIO.write(bi, 'png', file)
    }
}
