package com.etologic.pointscorer.app.common.ads

import android.content.Context
import com.etologic.pointscorer.app.common.ads.base.MyBaseBannerAd
import com.google.android.gms.ads.AdSize

class MyBannerAd(adUnit: String, context: Context) :
    MyBaseBannerAd(adUnit, context, AdSize.BANNER)