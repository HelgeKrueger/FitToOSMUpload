package com.github.helgekrueger.fittogpx

class FileTools {

    static isValidFitFile(filename) {
        filename ==~ /.*\.fit/
    }

    static changeFitToGpx(filename) {
        filename.replaceAll(/\.fit$/, /\.gpx/)
    }
}
