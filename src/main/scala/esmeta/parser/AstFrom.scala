package esmeta.parser

import esmeta.es.Ast

/** ECMAScript abstract syntax tree getter from strings or files */
trait AstFrom {
  def fromFile(str: String): Ast
  def from(str: String): Ast
}
