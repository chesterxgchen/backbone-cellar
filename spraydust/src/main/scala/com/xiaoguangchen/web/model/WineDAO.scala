package com.xiaoguangchen.web.model


import java.sql.{DriverManager, Connection}
import com.xiaoguangchen.spa.{ColumnMetadata, RowExtractor, QueryManager}
import scala.Predef._

/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/8/13
 */
class WineDAO {

  def getConnection: Connection = {
    val url ="jdbc:mysql://localhost"
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection(url, "root", "admin")
  }


  def wineRowProcessor = new RowExtractor[Wine] {
    def extractRow(oneRow: Map[ColumnMetadata, Any]): Wine= {
      val results = oneRow.map( x => (x._1.colName, x._2))
      val wineId = results.get("id").get.asInstanceOf[Int]
      val name = results.get("name").get.asInstanceOf[String]
      val grapes = results.get("grapes").get.asInstanceOf[String]
      val country = results.get("country").get.asInstanceOf[String]
      val region = results.get("region").get.asInstanceOf[String]
      val year = results.get("year").get.asInstanceOf[String]
      val picture = results.get("picture").get.asInstanceOf[String]
      val description = results.get("description").get.asInstanceOf[String]
      //Wine(None, name, grapes, country, region, year, picture, description )
      Wine(Some(wineId), name, grapes, country, region, year, picture, description )
    }
  }




  def findAll: List[Wine] = {
    val qm = QueryManager(open = getConnection)
    val sqlString = "SELECT id, name, grapes, country, region, year, picture,description FROM cellar.wine ORDER BY id"
    qm.query(sqlString,wineRowProcessor).toList()
  }



  def findByName(name: String): List[Wine] = {
    val sql: String = "SELECT id,name, grapes, country, region, year, picture,description FROM cellar.wine as e " + "WHERE UPPER(name) LIKE ? " + "ORDER BY name"
    val qm = QueryManager(open = getConnection)
    qm.query(sql, wineRowProcessor).parameterByPos(1, name) toList()
  }

  def findById(id: Int): Wine = {
    val sql: String = "SELECT id, name, grapes, country, region, year, picture,description FROM cellar.wine WHERE id = :id"
    val qm = QueryManager(open = getConnection)
    qm.query(sql, wineRowProcessor).parameterByName("id", id).toSingle().get

  }

  def save(wine: Wine): Wine = {

    if (wine.id.isDefined) update(wine) else create(wine)
  }

  def create(wine: Wine): Wine = {
      val sql = "INSERT INTO cellar.wine (name, grapes, country, region, year, picture, description) VALUES (?, ?, ?, ?, ?, ?, ?)"
      val qm = QueryManager(open = getConnection)
      val newId = qm.queryForUpdate(sql)
        .parameterByPos(1, wine.name)
        .parameterByPos(2, wine.grapes)
        .parameterByPos(3, wine.country)
        .parameterByPos(4, wine.region)
        .parameterByPos(5, wine.year)
        .parameterByPos(6, wine.picture)
        .parameterByPos(7, wine.description)
        .executeUpdate

       wine.copy(id=Some(newId.toInt))
  }

  def update(wine: Wine): Wine = {
    require(wine.id.isDefined)

    val sql = "UPDATE cellar.wine SET name=?, grapes=?, country=?, region=?, year=?, description=? WHERE id=?"
    val qm = QueryManager(open = getConnection)
    val code = qm.queryForUpdate(sql)
      .parameterByPos(1, wine.name)
      .parameterByPos(2, wine.grapes)
      .parameterByPos(3, wine.country)
      .parameterByPos(4, wine.region)
      .parameterByPos(5, wine.year)
//      .parameterByPos(6, wine.picture)
      .parameterByPos(6, wine.description)
      .parameterByPos(7, wine.id.get)
      .executeUpdate

     if (code == 0) throw new IllegalArgumentException(" no such wine")
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
