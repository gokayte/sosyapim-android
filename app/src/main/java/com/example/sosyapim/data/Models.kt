package com.example.sosyapim.data

data class MusicClip(
    val id: String,
    val title: String,
    val artist: String,
    val highlight: String? = null,
    val isLiked: Boolean = false
) {
    val thumbnailUrl: String
        get() = "https://img.youtube.com/vi/$id/hqdefault.jpg"

    val youtubeUrl: String
        get() = "https://www.youtube.com/watch?v=$id"
}

data class Artist(
    val name: String,
    val clipCount: Int,
    val topClipId: String,
    val sampleClipIndex: Int
) {
    val avatarUrl: String
        get() = "https://img.youtube.com/vi/$topClipId/hqdefault.jpg"
}

data class ServiceItem(
    val icon: String,
    val title: String,
    val description: String
)

data class DemoSubmission(
    val name: String,
    val email: String,
    val phone: String,
    val url: String,
    val note: String,
    val submittedAt: String
)

data class AnalyticsData(
    val visits: Int = 1240,
    val uniqueVisitors: Int = 890,
    val totalClicks: Int = 412,
    val youtubeClicks: Int = 215,
    val whatsappClicks: Int = 98,
    val twitterClicks: Int = 44,
    val copyClicks: Int = 55
) {
    val ctrPercent: Int
        get() = if (visits > 0) ((totalClicks.toDouble() / visits.toDouble()) * 100).toInt() else 0
}

enum class AppScreen(val label: String) {
    CLIPS("Klipler"),
    ARTISTS("Sanatçılar"),
    YERALTI("Yeraltı"),
    SERVICES("Hizmetler & Demo")
}
