package esmeta.ai

import esmeta.cfg.*

/** control points */
sealed trait ControlPoint {
  val view: View
  def func: Func
  def isBuiltin: Boolean = func.isBuiltin
}
case class NodePoint[+T <: Node](func: Func, node: T, view: View)
  extends ControlPoint
case class ReturnPoint(func: Func, view: View) extends ControlPoint
