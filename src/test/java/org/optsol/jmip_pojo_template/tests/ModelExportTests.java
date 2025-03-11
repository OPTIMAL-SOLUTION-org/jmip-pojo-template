package org.optsol.jmip_pojo_template.tests;

import com.google.ortools.linearsolver.MPSolver;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.optsol.jmip.linearsolver.model.LinearModel;
import org.optsol.jmip_pojo_template.model.Model;
import org.optsol.jmip_pojo_template.model.constants.Constants;
import org.optsol.jmip_pojo_template.utils.Utils;

@Slf4j
public class ModelExportTests {
  @Test
  public void testBuildAndExportTemplateModelToLpFile() {
    Constants constants = Utils.generateConstants();

    try {
      LinearModel<Constants> model =
          new Model().buildModel(
              constants,
              new MPSolver(
                  "Testsolver",
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING));

      log.info(model.getSolver().exportModelAsLpFormat());

    } catch (Exception ex) {
      fail(ex);
    }
  }

  @Test
  public void testBuildAndExportTemplateModelToMpsFile() {
    Constants constants = Utils.generateConstants();

    try {
      LinearModel<Constants> model =
          new Model().buildModel(
              constants,
              new MPSolver(
                  "Testsolver",
                  MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING));

      log.info(model.getSolver().exportModelAsMpsFormat(true, false));

    } catch (Exception ex) {
      fail(ex);
    }
  }
}
