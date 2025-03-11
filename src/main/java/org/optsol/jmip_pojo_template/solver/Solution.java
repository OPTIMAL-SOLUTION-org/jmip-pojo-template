package org.optsol.jmip_pojo_template.solver;

import org.optsol.jmip.core.solver.solution.ISolution;

public interface Solution
    extends ISolution {
  Integer[] get_x();
}
