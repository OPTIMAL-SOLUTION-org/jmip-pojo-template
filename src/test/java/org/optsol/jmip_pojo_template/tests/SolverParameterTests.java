package org.optsol.jmip_pojo_template.tests;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolverParameters;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.optsol.jmip.core.solver.solution.SolutionState;
import org.optsol.jmip.linearsolver.solver.LinearSolver;
import org.optsol.jmip_pojo_template.model.Model;
import org.optsol.jmip_pojo_template.model.constants.Constants;
import org.optsol.jmip_pojo_template.solver.Solution;
import org.optsol.jmip_pojo_template.utils.Utils;

public class SolverParameterTests {

  @Test
  public void testEnabledOutput() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING)
              .enableOutput(true)
              .build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }

  @Test
  public void testTimeLimit() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING)
              .timeLimit(Duration.ofSeconds(3))
              .build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }

  @Test
  public void testSolverSpecificParameterString() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING)
              .solverSpecificParameters("TODO")
              .build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }

  @Test
  public void testSolverParameters() {
    Loader.loadNativeLibraries();
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      MPSolverParameters mpSolverParameters = new MPSolverParameters();
      mpSolverParameters.setDoubleParam(MPSolverParameters.DoubleParam.RELATIVE_MIP_GAP, 0.5);
      mpSolverParameters.setIntegerParam(
          MPSolverParameters.IntegerParam.PRESOLVE,
          MPSolverParameters.PresolveValues.PRESOLVE_OFF.swigValue());

      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING)
              .solverParameters(mpSolverParameters)
              .build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }
}
