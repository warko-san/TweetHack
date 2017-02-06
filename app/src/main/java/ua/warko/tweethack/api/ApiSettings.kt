package ua.warko.tweethack.api

/**
 * Created by dmitry on 2/6/17.
 */
object ApiSettings {

    const val SCHEME = "https://"

    const val HOSTNAME = "api.twitter.com/1.1/"

    const val SERVER = SCHEME + HOSTNAME

    const val STATUSES_PATH = "statuses/"

    const val STATUS = "status="

    const val UPDATE = "update"

    const val UPDATE_STATUS = STATUSES_PATH + UPDATE
}