package com.github.helgekrueger.fittogpx

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import spock.lang.Specification

class GpxWriterSpec extends Specification {

    def 'test - one point of gpx writer'() {
        setup:
        def writer = new StringWriter()
        def now = new DateTime(2015, 6, 14, 22, 0, 50, 0, DateTimeZone.UTC)
        def data = [
            [lat: 10, lon: 20, ele: 100, time: now],
        ]

        def gpxWriter = new GpxWriter(writer)

        when:
        gpxWriter.write(data)

        then:
        writer.toString() .replaceAll(' |\n', '') == """
            <gpx version='1.0'>
            <trk>
            <trkseg>
            <trkpt lat='10' lon='20'>
            <ele>100</ele>
            <time>2015-06-14T22:00:50Z</time>
            </trkpt>
            </trkseg>
            </trk>
            </gpx>
        """.replaceAll(' |\n', '')
    }
}
