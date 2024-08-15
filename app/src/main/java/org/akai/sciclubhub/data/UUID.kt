package org.akai.sciclubhub.data

import kotlin.random.Random

data class UUID(
    val value: String
)
{
    override fun toString(): String = value

    companion object {
        private val random = Random(System.currentTimeMillis())
        fun randomUUID(): UUID {
            var randomUUID = ""
            for (i in 0..random.nextInt(
                from = 3,
                until = 10
            )) {
                randomUUID += random.nextInt(from = 0, until = 9)
            }

            return UUID(randomUUID)
        }
    }
}
