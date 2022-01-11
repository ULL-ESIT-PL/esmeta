package esmeta.spec

import esmeta.util.BaseUtils.*
import esmeta.spec.*
import Symbol.*

class StringifierTinyTest extends SpecTest {
  val name: String = "specStringifierTest"

  // registration
  def init: Unit = {
    // /////////////////////////////////////////////////////////////////////////
    // Grammar
    // /////////////////////////////////////////////////////////////////////////
    // pre-defined values
    val ntArgs = List(
      NtArg(NtArg.Kind.True, "Await"),
      NtArg(NtArg.Kind.False, "Yield"),
      NtArg(NtArg.Kind.Pass, "For"),
    )
    val nt: Nonterminal = Nonterminal("Identifier", Nil, false)
    val symbols = List(Terminal("{"), Terminal("}"))

    checkStringify("Symbol")(
      Terminal("{") -> "`{`",
      Nonterminal(
        "Identifier",
        ntArgs,
        true,
      ) -> "Identifier[+Await, ~Yield, ?For]?",
      Nonterminal("Identifier", Nil, false) -> "Identifier",
      ButNot(nt, List(nt)) -> "Identifier but not Identifier",
      Lookahead(
        true,
        List(symbols, symbols),
      ) -> "[lookahead < {`{` `}`, `{` `}`}]",
      Lookahead(
        false,
        List(symbols, symbols),
      ) -> "[lookahead <! {`{` `}`, `{` `}`}]",
      Empty -> "[empty]",
      NoLineTerminator -> "[no LineTerminator here]",
      Unicode("LT") -> "<LT>",
      UnicodeAny -> "<UnicodeAny>",
      UnicodeIdStart -> "<UnicodeIdStart>",
      UnicodeIdContinue -> "<UnicodeIdContinue>",
      UnicodeLeadSurrogate -> "<UnicodeLeadSurrogate>",
      UnicodeTrailSurrogate -> "<UnicodeTrailSurrogate>",
      NotCodePoint -> "<NotCodePoint>",
      CodePoint -> "<CodePoint>",
      HexLeadSurrogate -> "<HexLeadSurrogate>",
      HexTrailSurrogate -> "<HexTrailSurrogate>",
      HexNonSurrogate -> "<HexNonSurrogate>",
      NonUnicodeModeDecimalEscape -> "<NonUnicodeModeDecimalEscape>",
    )

    checkStringify("NtArg")(
      NtArg(NtArg.Kind.True, "Await") -> "+Await",
      NtArg(NtArg.Kind.False, "Yield") -> "~Yield",
      NtArg(NtArg.Kind.Pass, "Wait") -> "?Wait",
    )

    checkStringify("NtArg.Kind")(
      NtArg.Kind.True -> "+",
      NtArg.Kind.False -> "~",
      NtArg.Kind.Pass -> "?",
    )

    checkStringify("RhsCond")(
      RhsCond("Hello", true) -> "[+Hello]",
      RhsCond("Bye", false) -> "[~Bye]",
    )

    val rhsCond: RhsCond = RhsCond("Yield", true)
    val rhs1: Rhs = Rhs(Some(rhsCond), symbols, None)
    val rhs2: Rhs = Rhs(None, symbols, Some("this-is-id"))
    val rhs3: Rhs = Rhs(None, List(Terminal("a")), None)
    val lhs1 = Lhs("Identifier", List("Yield", "Await", "In"))
    val lhs2 = Lhs("Identifier", Nil)
    val prod1 =
      Production(lhs2, Production.Kind.Lexical, true, List(rhs3, rhs3))
    val prod2 =
      Production(lhs2, Production.Kind.Normal, false, List(rhs1, rhs2))
    val prod3 =
      Production(lhs1, Production.Kind.NumericString, false, List(rhs1))

    checkStringify("Rhs")(
      rhs1 -> "[+Yield] `{` `}`",
      rhs2 -> "`{` `}` #this-is-id",
      rhs3 -> "`a`",
    )

    checkStringify("Lhs")(
      lhs1 -> "Identifier[Yield, Await, In]",
      lhs2 -> "Identifier",
    )

    checkStringify("Production")(
      prod1 -> """Identifier :: one of
                 |  `a`
                 |  `a`
                 |""".stripMargin,
      prod2 -> """Identifier :
                 |  [+Yield] `{` `}`
                 |  `{` `}` #this-is-id
                 |""".stripMargin,
      prod3 -> """Identifier[Yield, Await, In] :::
                 |  [+Yield] `{` `}`
                 |""".stripMargin,
    )

    checkStringify("Production.Kind")(
      Production.Kind.Normal -> ":",
      Production.Kind.Lexical -> "::",
      Production.Kind.NumericString -> ":::",
    )

    checkStringify("Grammar")(
      Grammar(List(prod1), List(prod2)) ->
        s"""// Productions
           |Identifier :: one of
           |  `a`
           |  `a`
           |
           |// Productions for Web
           |Identifier :
           |  [+Yield] `{` `}`
           |  `{` `}` #this-is-id
           |""".stripMargin,
    )

    // /////////////////////////////////////////////////////////////////////////
    // Algorithm
    // /////////////////////////////////////////////////////////////////////////
    // TODO
  }

  init
}
