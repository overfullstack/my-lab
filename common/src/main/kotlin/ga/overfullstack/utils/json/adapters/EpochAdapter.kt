package org.revcloud.loki.common.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant
import java.util.Date

class EpochAdapter {
  @ToJson
  fun toJson(date: Date): String {
    return date.toString()
  }

  @FromJson
  fun fromJson(epoch: String): Date {
    return Date.from(Instant.ofEpochSecond(epoch.toLong()))
  }
}
