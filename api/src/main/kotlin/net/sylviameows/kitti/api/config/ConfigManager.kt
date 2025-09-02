package net.sylviameows.kitti.api.config

interface ConfigManager {
    /**
     * Discards all cached configs allowing reloading to be possible.
     */
    fun reload();

    fun <T> load(path: String, clazz: Class<T>): KittiConfig<T>
}

inline fun <reified T> ConfigManager.load(path: String): KittiConfig<T> {
    return this.load(path, T::class.java);
}
