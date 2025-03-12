package org.optsol.jmip_pojo_template.model;

import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import java.util.List;
import org.optsol.jmip.core.model.constraints.IConstraint;
import org.optsol.jmip.core.model.objective.IObjective;
import org.optsol.jmip.core.model.variables.IVariable;
import org.optsol.jmip.linearsolver.model.LinearModel;
import org.optsol.jmip.linearsolver.model.variables.LinearVariable;
import org.optsol.jmip_pojo_template.model.constants.Constants;
import org.optsol.jmip_pojo_template.model.constraints.AvailableMetalQuantity;
import org.optsol.jmip_pojo_template.model.objective.MaximizeProfit;
import org.optsol.jmip_pojo_template.model.variables.Variables;

public class Model extends LinearModel<Constants> {

  @Override
  protected IVariable<? super Constants, MPSolver, MPVariable> generateVariables() {
    return
        new LinearVariable.Builder<>()
            // x : int+
            .addIntVar(Variables.x)
            .addLowerBound(Variables.x, 0.)

            .build();
  }

  @Override
  protected IObjective<? super Constants, MPVariable, MPSolver> generateObjective() {
    return new MaximizeProfit();
  }

  @Override
  protected List<IConstraint<? super Constants, MPVariable, MPSolver>> generateConstraints() {
    return List.of(
        new AvailableMetalQuantity());
  }
}
