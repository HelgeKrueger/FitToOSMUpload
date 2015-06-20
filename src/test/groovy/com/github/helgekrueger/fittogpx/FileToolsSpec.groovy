package com.github.helgekrueger.fittogpx

import spock.lang.Specification
import spock.lang.Unroll

class FileToolsSpec extends Specification {

    @Unroll
    def 'test - is valid fit file'() {
        expect:
        FileTools.isValidFitFile(filename) == valid

        where:
        filename      | valid
        'test.fit'    | true
        'testfit'     | false
        'test.gpx'    | false
        'test.fit.gz' | false
    }

    def 'test - change fit to gpx'() {
        expect:
        FileTools.changeFitToGpx('test.fit') == 'test.gpx'
    }
}
