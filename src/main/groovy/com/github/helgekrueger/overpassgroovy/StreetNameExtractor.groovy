package com.github.helgekrueger.overpassgroovy

class StreetNameExtractor {

    private static final OFFSET = 0.0003

    def overpassQuery = new OverpassQuery()

    def retrieveStreetNames(data) {
        def names = []
        data.eachWithIndex{ point, idx ->
            if (idx % 100 == 0) {
                names += getStreetNames(point.lat, point.lon)
            }
        }
        names.groupBy().collect{ key, value ->
            [key, value.size()]
        }.sort{ it[1] }
    }

    private getStreetNames(lat, lon) {
        def result = overpassQuery.query{
            way{
                box lat - OFFSET, lon - OFFSET, lat + OFFSET, lon + OFFSET
                exist 'highway'
            }
        }
        def values = result.elements*.tags.findAll()
        values.findResults{ it.name } + values.findResults{ it.ref }
    }
}
