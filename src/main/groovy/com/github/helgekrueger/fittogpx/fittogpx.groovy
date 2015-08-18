package com.github.helgekrueger.fittogpx

import com.github.helgekrueger.fitparser.FitParser

class fittogpx {

    public static void main(String[] args) {

        if (!args) {
            usage()
            return
        }

        def filename = args[0]

        if (!FileTools.isValidFitFile(filename)) {
            println "Invalid filename ${filename}."
            return
        }

        def inputFile
        def outFilename = FileTools.changeFitToGpx(filename)
        try {
            inputFile = new FileInputStream(filename)
        } catch (ex) {
            println "Failed to open ${filename}."
            return
        }

        def data = new FitParser(inputFile: inputFile).parseInputFile()

        println "Writing to file ${outFilename}."
        def writer = new StringWriter()

        def gpxWriter = new GpxWriter(writer)
        data = data.findAll{ row -> !fittogpx.inBox(row) }

        gpxWriter.write(data)

        def httpClient = new HttpClient(
            propertyFileName: System.getProperty('user.home') + '/.config/osmauth.properties',
        )
        httpClient.uploadGpx(writer.toString())
    }

    static usage() {
        println """
        fittogpx filename
        """
    }

    static inBox(pos) {
        pos.lat <= 48.11 && pos.lat >= 48.10 && pos.lon >= 11.72 && pos.lon <= 11.73
    }
}

