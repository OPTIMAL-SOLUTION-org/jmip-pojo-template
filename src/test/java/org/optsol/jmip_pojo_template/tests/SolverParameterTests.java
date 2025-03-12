package org.optsol.jmip_pojo_template.tests;

import com.google.ortools.Loader;
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
                  Model.class,
                  Solution.class)
              .enableOutput()
              .build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }

  @Test
  public void testSupressOutput() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  Model.class,
                  Solution.class)
              .suppressOutput()
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
                  Model.class,
                  Solution.class)
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
  public void testMipGap() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
              Model.class,
              Solution.class)
              .relativeMipGap(0.05)
              .enableOutput()
          .build()
          .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }

  @Test
  public void testPresolveOff() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  Model.class,
                  Solution.class)
              .presolveOff()
              .enableOutput()
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
                  Model.class,
                  Solution.class)
              //FIND ALL SCIP-specific parameters here: https://scipopt.org/doc/html/PARAMETERS.php
              .solverSpecificParameters("presolving/maxrounds = 0")
              .enableOutput()
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
      // MIP-Gap
      mpSolverParameters.setDoubleParam(MPSolverParameters.DoubleParam.RELATIVE_MIP_GAP, 0.5);
      // Primal Tolerance
      mpSolverParameters.setDoubleParam(MPSolverParameters.DoubleParam.PRIMAL_TOLERANCE, 0.001);
      // Dual Tolerance
      mpSolverParameters.setDoubleParam(MPSolverParameters.DoubleParam.DUAL_TOLERANCE, 0.001);
      // Presolve
      mpSolverParameters.setIntegerParam(
          MPSolverParameters.IntegerParam.PRESOLVE,
          MPSolverParameters.PresolveValues.PRESOLVE_OFF.swigValue());
      // Presolve
      mpSolverParameters.setIntegerParam(
          MPSolverParameters.IntegerParam.LP_ALGORITHM,
          MPSolverParameters.LpAlgorithmValues.BARRIER.swigValue());
      // Presolve
      mpSolverParameters.setIntegerParam(
          MPSolverParameters.IntegerParam.INCREMENTALITY,
          MPSolverParameters.IncrementalityValues.INCREMENTALITY_OFF.swigValue());
      // Presolve
      mpSolverParameters.setIntegerParam(
          MPSolverParameters.IntegerParam.SCALING,
          MPSolverParameters.ScalingValues.SCALING_OFF.swigValue());


      solution =
          LinearSolver.builder(
                  Model.class,
                  Solution.class)
              .solverParameters(mpSolverParameters)
              .enableOutput()
              .build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }
}
