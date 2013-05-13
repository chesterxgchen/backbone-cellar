package com.xiaoguangchen.web.model


import java.sql.{DriverManager, Connection}
import com.xiaoguangchen.spa.QueryManager
import scala.Predef._

/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/8/13
 */
class WineDAO {

  import com.tingatech.web.example.Wine

  def getConnection: Connection = {
    val url ="jdbc:mysql://localhost"
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection(url, "root", "admin")
  }


  def findAll: List[Wine] = {
    val qm = QueryManager(open = getConnection)
    qm.queryWithClass("SELECT id, name, grapes, country, region, year, picture,description FROM cellar.wine ORDER BY name", classOf[Wine]).toList()
  }



  def findByName(name: String): List[Wine] = {
    val sql: String = "SELECT id,name, grapes, country, region, year, picture,description FROM cellar.wine as e " + "WHERE UPPER(name) LIKE ? " + "ORDER BY name"
    val qm = QueryManager(open = getConnection)
    qm.queryWithClass(sql, classOf[Wine]).parameterByPos(1, name) toList()
  }

  def findById(id: Int): Wine = {
    val sql: String = "SELECT id, name, grapes, country, region, year, picture,description FROM cellar.wine WHERE id = :id"
    val qm = QueryManager(open = getConnection)
    qm.queryWithClass(sql, classOf[Wine]).parameterByName("id", id).toSingle().get

  }

  def save(wine: Wine): Wine = {
    if (wine.id > 0) update(wine) else create(wine)
  }

  def create(wine: Wine): Wine = {
      val sql = "INSERT INTO cellar.wine (name, grapes, country, region, year, picture, description) VALUES (?, ?, ?, ?, ?, ?, ?)"
      val qm = QueryManager(open = getConnection)
      qm.queryForUpdate(sql)
        .parameterByPos(1, wine.name)
        .parameterByPos(2, wine.grapes)
        .parameterByPos(3, wine.country)
        .parameterByPos(4, wine.region)
        .parameterByPos(5, wine.year)
        .parameterByPos(6, wine.picture)
        .parameterByPos(7, wine.description)
        .executeUpdate

     findByName(wine.name).head
  }

  def update(wine: Wine): Wine = {

    //picture=?,
    val sql = "UPDATE cellar.wine SET name=?, grapes=?, country=?, region=?, year=?, description=? WHERE id=?"
    val qm = QueryManager(open = getConnection)
    qm.queryForUpdate(sql)
      .parameterByPos(1, wine.name)
      .parameterByPos(2, wine.grapes)
      .parameterByPos(3, wine.country)
      .parameterByPos(4, wine.region)
      .parameterByPos(5, wine.year)
//      .parameterByPos(6, wine.picture)
      .parameterByPos(6, wine.description)
      .parameterByPos(7, wine.id)
      .executeUpdate

      wine
  }

  def remove(id: Int): Boolean = {
      val sql = "DELETE FROM cellar.wine WHERE id=?"
      val qm = QueryManager(open = getConnection)
      val count = qm. queryForUpdate(sql)
                     .parameterByPos(1, id)
                      .executeUpdate
      count == 1
  }

}
