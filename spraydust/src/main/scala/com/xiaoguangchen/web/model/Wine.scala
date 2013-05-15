package com.xiaoguangchen.web.model


import scala.Predef.String
import com.xiaoguangchen.spa.annotation.Column

/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/8/13
 */

case class Wine(id: Option[Int],
                name:String,
                grapes:String,
                country:String,
                region:String,
                year:String,
                picture:String,
                description:String
                )

