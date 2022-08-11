package esmeta.phase

import esmeta.*
import esmeta.cfg.CFG
import esmeta.analyzer.*
import esmeta.analyzer.domain
import esmeta.util.*
import esmeta.util.SystemUtils.*

/** `typecheck` phase */
case object TypeCheck extends Phase[CFG, AbsSemantics] {
  val name = "typecheck"
  val help = "performs a type analysis of ECMA-262"
  def apply(
    cfg: CFG,
    globalConfig: GlobalConfig,
    config: Config,
  ): AbsSemantics = {
    // initalize
    domain._cfgOpt = Some(cfg)
    domain.ABS_STATE = domain.TypeStateDomain
    domain.ABS_VALUE = domain.TypeDomain
    IR_SENS = false
    TYPE_SENS = true
    val npMap = Initialize.initType(cfg)

    // perform type analysis
    AbsSemantics(npMap, 0).fixpoint
  }
  def defaultConfig: Config = Config()
  val options: List[PhaseOption[Config]] = List(
    (
      "repl",
      BoolOption(c => USE_REPL = true),
      "use a REPL for meta-level static analysis.",
    ),
  )
  case class Config()
}
