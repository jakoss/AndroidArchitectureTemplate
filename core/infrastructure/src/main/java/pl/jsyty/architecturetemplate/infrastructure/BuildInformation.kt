package pl.jsyty.architecturetemplate.infrastructure

/**
 * Since we turned off generating BuildConfig for all modules apart from app - we have this interface to pass anything we need to all modules
 *
 * This can be extended with fields live version, version code, build type and so on.
 */
interface BuildInformation {
    val apiUrl: String
}
