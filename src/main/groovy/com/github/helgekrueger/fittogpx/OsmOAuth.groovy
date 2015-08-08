package com.github.helgekrueger.fittogpx


import oauth.signpost.*
import oauth.signpost.basic.*

class OsmOAuth {
    final requestTokenUrl = 'https://www.openstreetmap.org/oauth/request_token'
    final accessTokenUrl = 'https://www.openstreetmap.org/oauth/access_token'
    final authorizeUrl = 'https://www.openstreetmap.org/oauth/authorize'

    private props
    private propertyFile

    def OsmOAuth(propertyFileName) {
        println propertyFileName
        propertyFile = new File(propertyFileName)
        props = new Properties()
        props.load(new FileInputStream(propertyFile))
    }

    def configureHttpBuilder(httpBuilder) {
        if (!isOauthConfigured()) {
            retrieveToken()
        }
        httpBuilder.auth.oauth(
            props.getProperty('consumerKey'), 
            props.getProperty('consumerSecret'), 
            props.getProperty('consumerToken'), 
            props.getProperty('consumerTokenSecret')
       )
    }

    private isOauthConfigured() {
        ['consumerKey', 'consumerSecret', 'consumerToken', 'consumerTokenSecret'].every{ props.getProperty(it) }
    }

    private retrieveToken() {
        consumerKey = getFromPropertyFileOrUserInput('consumerKey', "Enter your consumer key")
        consumerSecret = getFromPropertyFileOrUserInput('consumerSecret', "Enter your consumer secret")

        consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret)
        provider = new DefaultOAuthProvider(requestTokenUrl, accessTokenUrl, authorizeUrl)

        println "Go to the following URL and then enter the PIN on this page."
        println provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND)
        token = System.console().readLine()

        provider.retrieveAccessToken(consumer, token)

        props.setProperty('consumerToken', consumer.token)
        props.setProperty('consumerTokenSecret', consumer.tokenSecret)

        props.store(new FileOutputStream(propertyFile), null)
    }

    private getFromPropertyFileOrUserInput(key, userQuestion) {
        def value = properies.getProperty(key)

        if (!value) {
            println userQuestion
            value = System.console().readLine()
            props.setProperty(key, value)
        }

        value
    }
}
