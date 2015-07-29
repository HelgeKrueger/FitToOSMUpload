@Grab('oauth.signpost:signpost-core:1.2.1.2')
import oauth.signpost.*
import oauth.signpost.basic.*

requestTokenUrl = 'https://www.openstreetmap.org/oauth/request_token'
accessTokenUrl = 'https://www.openstreetmap.org/oauth/access_token'
authorizeUrl = 'https://www.openstreetmap.org/oauth/authorize'

println """
Script to retrieve oAuth tokens for OpenStreetMap
For this you first need to register an application in
your OpenStreetMap settings.
"""

println "Enter your consumer key"
consumerKey = System.console().readLine()
println "Enter your consumer secret"
consumerSecret = System.console().readLine()

consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret)
provider = new DefaultOAuthProvider(requestTokenUrl, accessTokenUrl, authorizeUrl)

println "Go to the following URL and then enter the PIN on this page."
println provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND)

token = System.console().readLine()

provider.retrieveAccessToken(consumer, token)

println "Your token: ${consumer.token}"
println "Your token secret: ${consumer.tokenSecret}"
