package com.github.helgekrueger.fitparser

import com.garmin.fit.MesgBroadcaster
import com.garmin.fit.BufferedRecordMesgListener

class FitParser {

    def parseInputFile(inputFile) {
        def data = []
        def messageBroadcaster = new MesgBroadcaster()
        def listener = new Listener(data)
        messageBroadcaster.addListener(listener as BufferedRecordMesgListener)

        messageBroadcaster.run(inputFile)

        data
    }
}
