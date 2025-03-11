package org.optsol.jmip_pojo_template.tests;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPSolver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.optsol.jmip.core.solver.solution.SolutionState;
import org.optsol.jmip.linearsolver.model.LinearModel;
import org.optsol.jmip.linearsolver.solver.LinearSolver;
import org.optsol.jmip_pojo_template.model.Model;
import org.optsol.jmip_pojo_template.model.constants.Constants;
import org.optsol.jmip_pojo_template.solver.Solution;
import org.optsol.jmip_pojo_template.utils.Utils;

@Slf4j
public class SolverTests {

  @Test
  public void testBuildAndExportTemplateModelToLpFile() {
    Constants constants = Utils.generateConstants();

    try {
      LinearModel<Constants> model =
          new Model().buildModel(
              constants,
              new MPSolver(
                  "Testsolver",
                  MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING));

      log.info(model.getSolver().exportModelAsLpFormat());

    } catch (Exception ex) {
      fail(ex);
    }
  }

  @Test
  public void testSolveTemplateModelWithCBC() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING)
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
  public void testSolveTepmlateModelWithSCIP() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING).build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }

  //@Test //HINT: Gurobi must be installed under standard path!
  //e.g.: C:\Program Files\Gurobi\win64\bin\gurobi90.dll
  public void testSolveTepmlateModelWithGUROBI() {
    Constants constants = Utils.generateConstants();

    Solution solution = null;
    try {
      solution =
          LinearSolver.builder(
                  new Model(),
                  Solution.class,
                  MPSolver.OptimizationProblemType.GUROBI_MIXED_INTEGER_PROGRAMMING).build()
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }
}
