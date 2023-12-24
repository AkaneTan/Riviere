package org.akanework.riviere.logic

import android.content.res.Resources.getSystem

val Int.dp: Int get() = (this.toFloat().dp).toInt()

val Int.px: Int get() = (this.toFloat().px).toInt()

val Float.dp: Float get() = this / getSystem().displayMetrics.density

val Float.px: Float get() = this * getSystem().displayMetrics.density