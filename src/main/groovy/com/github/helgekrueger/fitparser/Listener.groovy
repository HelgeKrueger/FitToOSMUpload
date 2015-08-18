package com.github.helgekrueger.fitparser

import com.garmin.fit.BufferedRecordMesg
import com.garmin.fit.BufferedRecordMesgListener
import com.github.helgekrueger.fittogpx.DateTools

class Listener implements BufferedRecordMesgListener {

    def data

    def Listener(data) {
        this.data = data
    }

    void onMesg(BufferedRecordMesg mesg) {

        if (mesg.positionLat && mesg.positionLong) {
            data << [
                lat: convert(mesg.positionLat),
                lon: convert(mesg.positionLong),
                ele: mesg.altitude,
                time: DateTools.convertGarminDate(mesg.timestamp),
            ]
        }
    }

    def convert(num) {
        num / 2**31 * 180
    }
}
