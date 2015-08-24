package com.github.helgekrueger.fitparser

import com.garmin.fit.DateTime as GarminDate
import org.joda.time.DateTime
import spock.lang.Specification

class ListenerSpec extends Specification {

    def 'test date conversion'() {
        setup:
        def date = new Date()
        def jodaDate = new DateTime(date)
        def garminDate = new GarminDate(date)

        expect:
        Listener.convertGarminDate(garminDate) == jodaDate
    }
}
