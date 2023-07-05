package com.example.streamingapp.mocks

import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.Sound

object DomainModelMocks {

    val soundsList = listOf(
        Sound(
            id = 1,
            name = "name",
            username = "username"
        )
    )
    val detailedSoundMock = DetailedSound(
        id = 1,
        name = "name",
        description = "description",
        username = "username",
        duration = 0f,
        previews = mapOf("" to ""),
        images = mapOf("" to "")
    )
}
