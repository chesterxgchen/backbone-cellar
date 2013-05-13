package com.xiaoguangchen.web.model


import scala.Predef.String
import com.xiaoguangchen.spa.annotation.Column

/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/8/13
 */

case class Wine(@Column("id") id: Int,
                @Column("name") name:String,
                @Column("grapes") grapes:String,
                @Column("country") country:String,
                @Column("region") region:String,
                @Column("year") year:String,
                @Column("picture") picture:String,
                @Column("description") description:String
                )


