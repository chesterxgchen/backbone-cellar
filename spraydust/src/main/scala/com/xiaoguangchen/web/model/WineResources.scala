package com.xiaoguangchen.web.model


/**
 *
 * Chester Chen (chester@tingatech.com)
 *
 * 5/8/13
 */
object WineResources  {
  private val dao: WineDAO = new WineDAO


   def findAll: List[Wine] = {
    println("findAll")
     dao.findAll
  }

  def findByName(name: String): List[Wine] = {
    println("findByName: " + name)
    dao.findByName(name)
  }

 def findById(id: Int): Wine = {
    println("findById " + id)
     dao.findById(id)
  }

  def create(wine: Wine): Wine = {
    dao.create(wine)
  }

  def update(wine: Wine): Wine = {
    println("Updating wine: " + wine.name)
    dao.update(wine)
    return wine
  }

  def remove(id: Int) {
    dao.remove(id)
  }
}
