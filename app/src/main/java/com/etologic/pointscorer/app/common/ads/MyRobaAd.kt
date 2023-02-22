package com.etologic.pointscorer.app.common.ads

import android.content.Context
import android.widget.LinearLayout
import com.etologic.pointscorer.app.common.ads.base.MyBaseAd
import com.etologic.pointscorer.app.common.ads.base.MyBaseBannerAd
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class MyRobaAd(adUnit: String, context: Context):
    MyBaseBannerAd(adUnit, context, AdSize.MEDIUM_RECTANGLE)