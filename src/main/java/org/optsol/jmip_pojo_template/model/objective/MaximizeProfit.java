package org.optsol.jmip_pojo_template.model.objective;

import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPVariable;
import org.optsol.jmip.core.model.variables.IVariableProvider;
import org.optsol.jmip.linearsolver.model.objective.LinearObjective;
import org.optsol.jmip_pojo_template.model.constants.Constants;
import org.optsol.jmip_pojo_template.model.variables.Variables;

public class MaximizeProfit extends LinearObjective<Constants> {

  @Override
  protected void configureObjective(
      MPObjective objective,
      Constants constants,
      IVariableProvider<MPVariable> variables) throws Exception {
    //max sum_c:C[ P_c * x_c ]
    objective.setMaximization();

    for (int c : constants.get_C()) {
      objective.setCoefficient(
          variables.getVar(Variables.x, c),
          constants.get_P(c));
    }
  }
}
