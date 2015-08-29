package com.github.helgekrueger.osmupload

class OsmUpload {

    def upload(data) {
        def gpxString = getDataAsGpxString(data)

        def httpClient = new HttpClient(
            propertyFileName: System.getProperty('user.home') + '/.config/osmauth.properties',
        )
        httpClient.uploadGpx(gpxString)
    }

    private getDataAsGpxString(data) {
        def writer = new StringWriter()
        def gpxWriter = new GpxWriter(writer)
        def prunedData = data.findAll{ row -> !inBox(row) }
        gpxWriter.write(prunedData)

        writer.toString()
    }

    private inBox(pos) {
        pos.lat <= 48.11 && pos.lat >= 48.10 && pos.lon >= 11.72 && pos.lon <= 11.73
    }
}
