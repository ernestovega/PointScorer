package com.etologic.pointscorer.app.common.ads

import android.content.Context
import com.etologic.pointscorer.app.common.ads.base.MyBaseBannerAd
import com.google.android.gms.ads.AdSize

class MyRobaAd private constructor(adUnit: String, context: Context) :
    MyBaseBannerAd(adUnit, context, AdSize.MEDIUM_RECTANGLE) {

    companion object {
        fun getNewInstance(adUnit: String, context: Context): MyRobaAd = MyRobaAd(adUnit, context)
    }

}