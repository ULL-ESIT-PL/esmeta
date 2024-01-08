package esmeta.parser

import esmeta.es.*
import esmeta.spec.*
import esmeta.util.*
import esmeta.util.BaseUtils.*
import scala.util.parsing.input.Position
import scala.util.matching.Regex

/** ECMAScript special unicodes */
trait UnicodeParsers extends BasicParsers with EPackratParsers {
  val ZWNJ = 0x200c
  val ZWJ = 0x200d
  val ZWNBSP = 0xfeff
  // white spaces
  val TAB = 0x0009
  val VT = 0x000b
  val FF = 0x000c
  val SP = 0x0020
  val NBSP = 0x00a0
  // TODO automatically extract category "Zs"
  val USP = Set(
    0x1680, 0x2000, 0x2001, 0x2002, 0x2003, 0x2004, 0x2005, 0x2006, 0x2007,
    0x2008, 0x2009, 0x200a, 0x202f, 0x205f, 0x3000,
  )
  // line terminators
  val LF = 0x000a
  val CR = 0x000d
  val LS = 0x2028
  val PS = 0x2029

  lazy val lines = toParser(LF, CR, LS, PS)
  lazy val Any = "(?s).".r
  lazy val IDStart = toParser(Unicode.IDStart)
  lazy val IDContinue = toParser(Unicode.IDContinue)

  protected inline def toCodePoint(s: String): Int = s.codePoints.toArray.head

  protected inline def toParser(seq: Int*): Parser[String] = toParser(seq.toSet)
  protected inline def toParser(cp: Int): Parser[String] =
    Any.filter(s => toCodePoint(s) == cp)
  protected inline def toParser(set: Set[Int]): Parser[String] =
    Any.filter(s => set contains toCodePoint(s))

  // abbreviated code points mapping
  val abbrCPs: Map[String, Set[Int]] = Map(
    "ZWNJ" -> ZWNJ,
    "ZWJ" -> ZWJ,
    "ZWNBSP" -> ZWNBSP,
    "TAB" -> TAB,
    "VT" -> VT,
    "FF" -> FF,
    "SP" -> SP,
    "NBSP" -> NBSP,
    "LF" -> LF,
    "CR" -> CR,
    "LS" -> LS,
    "PS" -> PS,
  ).map((k, v) => k -> Set(v)) + (
    "USP" -> USP
  )
}
