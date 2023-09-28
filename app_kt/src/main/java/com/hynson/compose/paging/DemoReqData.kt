package com.hynson.compose.paging

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DemoReqData(
    var data: DataBean? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null
)

data class DataBean(
    var curPage: Int = 0,
    var offset: Int = 0,
    var isOver: Boolean = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0,
    var datas: List<DatasBean>? = null
)

@Parcelize
data class DatasBean(
    var apkLink: String? = null,
    var audit: Int = 0,
    var author: String? = null,
    var isCanEdit: Boolean = false,
    var chapterId: Int = 0,
    var chapterName: String? = null,
    var isCollect: Boolean = false,
    var courseId: Int = 0,
    var desc: String? = null,
    var descMd: String? = null,
    var envelopePic: String? = null,
    var isFresh: Boolean = false,
    var id: Int = 0,
    var link: String? = null,
    var niceDate: String? = null,
    var niceShareDate: String? = null,
    var origin: String? = null,
    var prefix: String? = null,
    var projectLink: String? = null,
    var publishTime: Long = 0,
    var realSuperChapterId: Int = 0,
    var selfVisible: Int = 0,
    var shareDate: Long? = null,
    var shareUser: String? = null,
    var superChapterId: Int = 0,
    var superChapterName: String? = null,
    var title: String? = null,
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0,
    var tags: List<TagBean>? = null
) : Parcelable

@Parcelize
data class TagBean(
    var name: String? = null,
    var url: String? = null
) : Parcelable
