package com.github.helgekrueger.fittogpx

import com.github.helgekrueger.fitparser.FitParser
import com.github.helgekrueger.osmupload.OsmUpload
import com.github.helgekrueger.overpassgroovy.StreetNameExtractor
import com.github.helgekrueger.geography.Track

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
        // new OsmUpload().upload(data)
        // new StreetNameExtractor().printStreetNames(data)
        new Track(data: data).writeToPng()
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
}

