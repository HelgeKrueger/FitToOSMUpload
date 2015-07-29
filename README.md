# FitToOSMUpload
Tool to convert Garmin's FIT format to GPX and censor data not relevant for OpenStreetMap e.g. heartrate or your home location.

# Installation

- Get Garmin's FIT SDK from http://www.thisisant.com/resources/fit/
- Place the contained fit.jar in the project's lib folder
- Edit fittogpx's inBox method to mask your home coordinates.
- Configure oAuth
-- Register an application for OpenStreetMap
-- Run the script script/OSMAuth.groovy to obtain your token and token secret.
-- Edit fittogpx to add the oauth credentials
- Check that everything works with gradle check
- Build the project with gradle installDist
- You can find the executable under build/install/FitToOSMUpload/bin

# Uploading GPX files to OpenStreetMap with curl

The following is no longer necessary as one can upload directly to OSM.

```
#!/bin/bash
CREDENTIALS="username:password"
URL=https://api.openstreetmap.org/api/0.6/gpx/create

curl -v --user "$CREDENTIALS" $URL -F description=Activity -F visibility=identifiable -F file=@$1
```
