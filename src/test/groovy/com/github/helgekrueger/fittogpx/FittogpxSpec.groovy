package com.github.helgekrueger.fittogpx

import spock.lang.Specification

class FittogpxSpec extends Specification {

    def 'command line options - help works'() {
        expect:
        ! Fittogpx.main('-h')
    }

    def 'command line options - no filename given'() {
        expect:
        ! Fittogpx.main('-h')
    }
}
