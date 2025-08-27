package net.sylviameows.kitti.api.config

import com.akuleshov7.ktoml.file.TomlFileWriter
import kotlinx.serialization.serializer

class KittiConfig<T> (val values: T, private val path: String) {
    fun save() {
        TomlFileWriter().encodeToFile(serializer(values!!::class.java), values, path);
    }

    fun values(): T {
        return values;
    }
}