package com.gkgio.domain.location

import io.reactivex.Maybe
import io.reactivex.Single

interface LocationRepository {
    fun getLastKnownLocationOrThrow(): Single<Coordinates>
    fun getLastKnownLocationIfPossible(): Maybe<Coordinates>
    fun startUpdateLocationListener(): Single<Coordinates>
}