package com.github.helgekrueger.osmupload

import org.joda.time.format.ISODateTimeFormat

class DateTools {
    static formatDateForGpx(date) {
        ISODateTimeFormat.dateTimeNoMillis().withZoneUTC().print(date)
    }
}
