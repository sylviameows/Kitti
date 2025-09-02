package net.sylviameows.kitti.api.config

import com.akuleshov7.ktoml.TomlInputConfig
import com.akuleshov7.ktoml.TomlOutputConfig

final object TomlConfig {
    val input = TomlInputConfig(
        ignoreUnknownNames = true
    )
    val output = TomlOutputConfig()
}