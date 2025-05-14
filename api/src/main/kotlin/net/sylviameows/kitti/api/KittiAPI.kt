package net.sylviameows.kitti.api

import org.jetbrains.annotations.ApiStatus.Internal

interface KittiAPI {
    companion object {
        fun instance(): KittiAPI {
            return Holder.INSTANCE
        }
    }

    @Internal
    object Holder {
        internal lateinit var INSTANCE: KittiAPI;

        fun setInstance(api: KittiAPI) {
            INSTANCE = api
        }
    }
}