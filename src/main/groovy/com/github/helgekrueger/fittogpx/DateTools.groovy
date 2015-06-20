package com.github.helgekrueger.fittogpx

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat

class DateTools {
    private static final format = DateTimeFormat.forPattern('EEE MMM dd HH:mm:ss yyyy')

    static convertGarminDate(date) {
        return new DateTime(date.date)
    }

    static formatDateForGpx(date) {
        ISODateTimeFormat.dateTimeNoMillis().withZoneUTC().print(date)
    }
}
