package com.github.helgekrueger.fitparser

import com.garmin.fit.BufferedRecordMesg
import com.garmin.fit.BufferedRecordMesgListener
import org.joda.time.DateTime

class Listener implements BufferedRecordMesgListener {

    def data

    Listener(data) {
        this.data = data
    }

    void onMesg(BufferedRecordMesg mesg) {

        if (mesg.positionLat && mesg.positionLong) {
            data << [
                lat: convert(mesg.positionLat),
                lon: convert(mesg.positionLong),
                ele: mesg.altitude,
                time: convertGarminDate(mesg.timestamp),
            ]
        }
    }

    static convertGarminDate(date) {
        new DateTime(date.date)
    }

    def convert(num) {
        num / 2 ** 31 * 180
    }
}
