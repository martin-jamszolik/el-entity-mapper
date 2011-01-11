/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.freedom.gj.mapper

trait EntityMapper {


  def map[T](source:Any,target:T):T

}
