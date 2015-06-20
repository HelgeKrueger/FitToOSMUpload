package com.github.helgekrueger.fittogpx

import com.garmin.fit.DateTime as GarminDate
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import spock.lang.Specification

class DateToolsSpec extends Specification {

    def 'test date conversion'() {
        setup:
        def date = new Date()
        def jodaDate = new DateTime(date)
        def garminDate = new GarminDate(date)

        expect:
        DateTools.convertGarminDate(garminDate) == jodaDate
    }

    def 'test  formatDateForGpx'() {
        setup:
        def date = new DateTime(2015, 6, 14, 22, 0, 50, 0, DateTimeZone.UTC)

        expect:
        DateTools.formatDateForGpx(date) == '2015-06-14T22:00:50Z'
    }
}
