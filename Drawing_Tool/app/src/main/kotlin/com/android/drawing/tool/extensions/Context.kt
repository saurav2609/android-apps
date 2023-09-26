package com.android.drawing.tool.extensions

import android.content.Context
import com.android.drawing.tool.helpers.Config

val Context.config: Config get() = Config.newInstance(applicationContext)
