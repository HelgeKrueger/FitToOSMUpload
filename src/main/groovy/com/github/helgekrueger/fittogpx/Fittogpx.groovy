package com.github.helgekrueger.fittogpx

import com.github.helgekrueger.fitparser.FitParser
import com.github.helgekrueger.osmupload.OsmUpload
import com.github.helgekrueger.overpassgroovy.StreetNameExtractor
import com.github.helgekrueger.geography.Track

class Fittogpx {

    static void main(String[] args) {
        def cli = new CliBuilder(usage: 'use me')
        cli.with{
            h longOpt: 'help', 'print help'
            s longOpt: 'street-name', 'requests street name from overpass api'
            m longOpt: 'movie', 'generate pngs to make movie'
        }

        def options = cli.parse(args)
        if (!options) {
            return
        }
        if (options.h) {
            cli.usage()
            return
        }
        def filename = options.arguments().first()
        if (!FileTools.isValidFitFile(filename)) {
            println "Invalid filename ${filename}."
            return
        }

        def data = getDataFromFile(filename)
        if (options.s) {
            new StreetNameExtractor().printStreetNames(data)
        } else if (options.m) {
            new Track(data: data).writeToPng()
        } else {
            new OsmUpload().upload(data)
        }
    }

    static getDataFromFile(filename) {
        def inputFile
        try {
            inputFile = new FileInputStream(filename)
        } catch (ex) {
            println "Failed to open ${filename}."
            return
        }
        new FitParser().parseInputFile(inputFile)
    }
}

