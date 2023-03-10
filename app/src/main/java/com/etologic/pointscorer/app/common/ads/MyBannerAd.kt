package com.etologic.pointscorer.app.common.ads

import android.content.Context
import com.etologic.pointscorer.app.common.ads.base.MyBaseBannerAd
import com.google.android.gms.ads.AdSize

class MyBannerAd private constructor(adUnit: String, context: Context) :
    MyBaseBannerAd(adUnit, context, AdSize.BANNER) {

    companion object {
        fun getNewInstance(adUnit: String, context: Context): MyBannerAd = MyBannerAd(adUnit, context)
    }

}