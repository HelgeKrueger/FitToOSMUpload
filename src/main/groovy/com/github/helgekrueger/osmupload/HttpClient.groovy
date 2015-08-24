package com.github.helgekrueger.osmupload

import static groovyx.net.http.Method.POST

import groovyx.net.http.HTTPBuilder

import org.apache.commons.io.IOUtils
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.HttpMultipartMode

class HttpClient {

    def location = 'https://api.openstreetmap.org/'
    def createEndpoint = '/api/0.6/gpx/create'

    def propertyFileName

    def uploadGpx(gpxString) {
        def http = new HTTPBuilder(location)
        def oauth = new OsmOAuth(propertyFileName)
        oauth.configureHttpBuilder(http)
        http.request(POST) { request ->
            uri.path = createEndpoint

            MultipartEntityBuilder multipartRequest = MultipartEntityBuilder.create()
            multipartRequest.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            multipartRequest.addTextBody('description', 'Activity', ContentType.TEXT_XML)
            multipartRequest.addTextBody('visibility', 'identifiable', ContentType.TEXT_XML)
            multipartRequest.addBinaryBody(
                'file',
                IOUtils.toInputStream(gpxString),
                ContentType.TEXT_XML,
                'activity.gpx')

            requestContentType = 'multipart/form-data'
            request.entity = multipartRequest.build()

            response.failure = { resp ->
                println 'Something went wrong'
            }
        }
    }
}
