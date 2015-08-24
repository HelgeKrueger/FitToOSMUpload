package com.github.helgekrueger.overpassgroovy

import groovyx.net.http.RESTClient

class OverpassQuery {

    final url = 'http://overpass.osm.rambler.ru/'
    final path = '/cgi/interpreter'
    // final url = 'http://overpass-api.de/'
    // final path = '/api/interpreter'
    final client = new RESTClient(url)

    def queryString(Closure criteria) {
        def parser = new OverpassCriteriaBuilder()
        criteria.delegate = parser
        criteria()
        parser.build()
    }

    def query(Closure criteria) {
        def queryString = queryString(criteria)
        def query = [data: queryString]
        def response = client.get(path: path, query: query)
        response.data
    }
}

class OverpassCriteriaBuilder {

    def queries = []

    def build() {
        def queryParts = []
        queryParts << '[out:json]'
        queryParts += queries
        queryParts << 'out'

        queryParts.join(';') + ';'
    }

    def methodMissing(String name, args) {
        def queryParser = new QueryParser()
        assert args.size() == 1
        Closure queryRestriction = args[0]
        queryRestriction.delegate = queryParser
        queryRestriction.resolveStrategy = Closure.DELEGATE_FIRST
        queryRestriction()

        queries << "${name}${queryParser.query}"
    }
}

class QueryParser {
    def query = ''

    def box(latMin, lonMin, latMax, lonMax) {
        query += "($latMin,$lonMin,$latMax,$lonMax)"
    }

    def exist(key) {
        query += "[\"$key\"]"
    }

    def methodMissing(String name, args) {
        assert args.size() == 1
        query += """["${name}"="${args[0]}"]"""
    }
}
