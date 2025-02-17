package org.optsol.jmip_pojo_template.solver;

import org.optsol.jmip.ortools.OrtoolsSolver;
import org.optsol.jmip.ortools.SolverEngine;
import org.optsol.jmip_pojo_template.model.Model;
import org.optsol.jmip_pojo_template.model.constants.Constants;

public class Solver
    extends OrtoolsSolver<Constants, Solution> {

  public Solver(
      SolverEngine solverEngine) {
    super(
        new Model(solverEngine),
        Solution.class);
  }

  public Solver(
      SolverEngine solverEngine,
      int timelimitSeconds) {
    super(
        timelimitSeconds,
        new Model(solverEngine),
        Solution.class);
  }
}
