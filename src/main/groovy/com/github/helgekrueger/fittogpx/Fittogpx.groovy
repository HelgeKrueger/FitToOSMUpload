package com.github.helgekrueger.fittogpx

import com.github.helgekrueger.fitparser.FitParser
import com.github.helgekrueger.osmupload.OsmUpload
import com.github.helgekrueger.overpassgroovy.StreetNameExtractor

class Fittogpx {

    static void main(String[] args) {

        if (!args) {
            usage()
            return
        }

        def filename = args[0]

        if (!FileTools.isValidFitFile(filename)) {
            println "Invalid filename ${filename}."
            return
        }

        def data = getDataFromFile(filename)

        new OsmUpload().upload(data)
        // new StreetNameExtractor().printStreetNames(data)
    }

    static getDataFromFile(filename) {
        def inputFile
        try {
            inputFile = new FileInputStream(filename)
        } catch (ex) {
            println "Failed to open ${filename}."
            return
        }
        new FitParser(inputFile: inputFile).parseInputFile()
    }

    static usage() {
        println 'fittogpx filename'
    }

    static inBox(pos) {
        pos.lat <= 48.11 && pos.lat >= 48.10 && pos.lon >= 11.72 && pos.lon <= 11.73
    }
}

