package com.eugrepmac.catalog.bounding

import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a Compass. This will be used to get actual and up to date values of current device
 * orientation, and current device position.
 *
 * The position indicates how the device has been tilted and if the actual axes may have been
 * switched. It is different than the current screen orientation. ie : A PORTRAIT position doesn't
 * necessarily mean that the current app is displayed in portrait mode.
 */
interface ICompass {
    /**
     * The different possible positions for this compass. This will be used to indicate if we noticed
     * a tilt on the device.
     *
     * Note that this position is different from screen orientation, and a PORTRAIT position doesn't
     * necessarily mean that the application is currently being displayed in portrait mode.
     * It will be used to indicate a potential switch in device axes.
     *
     * Note that the compass cannot be in PORTRAIT and LANDSCAPE position at the same time.
     */
    enum class Position {
        /**
         * The device is currently being held in a flatish way. ie: pitch and roll are both small
         * enough to consider the device being flat.
         */
        FLAT,

        /**
         * The device is being held in "portrait" mode, meaning that the current pitch (rotation
         * along the x-axis) is big enough so that we considered the device tilted and might have
         * switched axis.
         *
         * Note that this is different from a portrait screen orientation and nothing guarantees
         * that the screen is currently oriented in portrait mode when the compass is in this
         * position.
         */
        PORTRAIT,

        /**
         * The device is being held in "landscape" mode, meaning that the current roll (rotation
         * along the y-axis) is big enough so that we considered the device tilted and might have
         * switched axis.
         *
         * Note that this is different from a landscape screen orientation and nothing guarantees
         * that the screen is currently oriented in landscape mode when the compass is in this
         * position.
         */
        LANDSCAPE,
    }

    /**
     * A state flow representing the current orientation of the compass in degrees. It will be a
     * Float in the [0; 360[ range.
     *
     * Note that 0 means due North and 90 means due East.
     *
     * Defaults to 0 and keeps the last received value while stopped.
     */
    val currentOrientation: StateFlow<Float?>

    /**
     * A state flow representing the current [Position] of the compass.
     *
     * Defaults to FLAT and keeps the last value while stopped.
     */
    val currentPosition: StateFlow<Position?>

    /**
     * Starts the sensors and start receiving new orientation and position values.
     *
     * The compass should be stopped whenever it is not in use because sensors activation drains a
     * lot of battery on the device.
     */
    fun startCompass()

    /**
     * Stops the sensors and stop receiving orientation and position values.
     */
    fun stopCompass()
}
