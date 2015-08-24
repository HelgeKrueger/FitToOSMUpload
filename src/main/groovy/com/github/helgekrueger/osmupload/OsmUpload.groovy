package com.github.helgekrueger.osmupload

class OsmUpload {

    def upload() {
        def gpxString = getDataAsGpxString(data)

        def httpClient = new HttpClient(
            propertyFileName: System.getProperty('user.home') + '/.config/osmauth.properties',
        )
        httpClient.uploadGpx(gpxString)
    }

    private getDataAsGpxString(data) {
        def writer = new StringWriter()
        def gpxWriter = new GpxWriter(writer)
        def prunedData = data.findAll{ row -> !fittogpx.inBox(row) }
        gpxWriter.write(prunedData)

        writer.toString()
    }
}
