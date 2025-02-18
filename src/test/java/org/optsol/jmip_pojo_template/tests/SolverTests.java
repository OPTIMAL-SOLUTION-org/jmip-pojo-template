package org.optsol.jmip_pojo_template.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.optsol.jmip.core.SolutionState;
import org.optsol.jmip.ortools.OrtoolsModel;
import org.optsol.jmip.ortools.SolverEngine;
import org.optsol.jmip_pojo_template.model.Model;
import org.optsol.jmip_pojo_template.model.constants.Constants;
import org.optsol.jmip_pojo_template.solver.Solution;
import org.optsol.jmip_pojo_template.solver.Solver;
import org.optsol.jmip_pojo_template.utils.Utils;

@Slf4j
public class SolverTests {

  @Test
  public void testBuildAndExportTemplateModelToLpFile() {
    Constants constants = Utils.generateConstants();

    try {
      OrtoolsModel<Constants> model =
          new Model(SolverEngine.SCIP).buildModel(constants);

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
          new Solver(SolverEngine.CBC)
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
          new Solver(SolverEngine.SCIP)
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
          new Solver(SolverEngine.GUROBI)
              .generateSolution(constants);
    } catch (Exception ex) {
      fail(ex);
    }

    assertEquals(SolutionState.OPTIMAL, solution.getSolutionState());

    Utils.printSolution(solution, constants);
  }
}
