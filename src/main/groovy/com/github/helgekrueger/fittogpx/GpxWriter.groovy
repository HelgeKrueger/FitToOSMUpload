package com.github.helgekrueger.fittogpx

import groovy.xml.MarkupBuilder

class GpxWriter {
    def writer

    GpxWriter(writer) {
        this.writer = writer
    }

    def write(data) {
        def gpxBuilder = new MarkupBuilder(writer)

        gpxBuilder.gpx(version: '1.0') {
            trk {
                trkseg{
                    data.each{ point ->
                        trkpt(lat: point.lat, lon: point.lon) {
                            ele(point.ele)
                            time(DateTools.formatDateForGpx(point.time))
                        }
                    }
                }
            }
        }
    }
}
