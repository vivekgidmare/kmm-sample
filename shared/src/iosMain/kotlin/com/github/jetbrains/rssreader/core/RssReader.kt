package com.github.jetbrains.rssreader.core

import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier
import com.github.jetbrains.rssreader.core.datasource.network.FeedLoader
import com.github.jetbrains.rssreader.core.datasource.storage.FeedStorage
import com.russhwolf.settings.AppleSettings
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

fun RssReader.Companion.create(withLog: Boolean) = RssReader(
    FeedLoader(
        IosHttpClient(withLog),
        IosFeedParser()
    ),
    FeedStorage(
        AppleSettings(NSUserDefaults.standardUserDefaults()),
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    )
).also {
    if (withLog) Napier.base(DebugAntilog())
}