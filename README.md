# jmip-pojo-template

This project is a minimal example that demonstrates how to use the **_jMIP_** framework to solve a
mixed-integer program in Java.<br>
It's just three lines of code to your [optimal solution](https://www.optimal-solution.org/) :wink::

```java
Constants constants =
    new Constants(/*TODO: provide constants*/);

LinearSolver solver =
    LinearSolver.builder(Model.class, Solution.class).build();

Solution solution =
    solver.generateSolution(constants);
```

You can fork this project if you want to quickstart your own implementation of an executable MIP/LP
model.

### What is **_jMIP_** ?

**_jMIP_** is a **Dec**larative Java API for **O**perations **R**esearch (OR) software.

> **_jMIP_**'s original purpose is to get rid of the often hard-to-avoid boilerplate code typically
> arising in applications that rely on solving OR models using (open-source or commercial) OR software
> packages. In addition, it follows a more declarative programming paradigm by encouraging you to
> compose your specific OR model in a structured way.
>
> Long story short, it is designed to help making your code **cleaner** and **less error-prone**!

### How to install **_jMIP_** ?

* You can [download](https://central.sonatype.com/namespace/org.optsol.jmip) **_jMIP_** manually from maven central repository
* Alternatively add the dependency to your project's POM:
```xml
<dependencies>
  <dependency>
    <groupId>org.optsol.jmip</groupId>
    <artifactId>jmip-ortools-linearsolver</artifactId>
    <version>2.0.0</version>
  </dependency>
</dependencies>
```

# What do you find in the template project?

## 1. Optimization Problem

Slightly leaving the legal way, our idea is to forge EURO coins.

### 1.1 Problem Definition

#### Objective

Grabby as we are, we want to optimize our profit, namely the worth of EUROs produced.

#### Recipe

Fortunately we found an easy DIY-recipe in the shallow parts of the darknet. Now we know the
weight (grams) of the metals Copper (Cu), Iron (Fe), Nickel (Ni), Aluminium (Al), Zinc (Zn) and
Tin (Sn) needed for each coin type:

|          |   Cu   |  Fe   |  Ni   |  Al   |  Zn   |   Sn   |
|---------:|:------:|:-----:|:-----:|:-----:|:-----:|:------:|
|   1 Cent | 0.102  | 2.198 |   0   |   0   |   0   |   0    |
|  2 Cents | 0.118  | 2.942 |   0   |   0   |   0   |   0    |
|  5 Cents | 0.134  | 3.786 |   0   |   0   |   0   |   0    | 
| 10 Cents | 3.649  |   0   |   0   | 0.205 | 0.205 | 0.041  | 
| 20 Cents | 5.1086 |   0   |   0   | 0.287 | 0.287 | 0.0574 | 
| 50 Cents | 6.942  |   0   |   0   | 0.39  | 0.39  | 0.078  |
|   1 Euro | 5.625  |   0   | 1.695 |   0   | 0.18  |   0    |
|  2 Euros | 6.375  |   0   | 1.751 |   0   | 0.374 |   0    | 

#### Restriction

Not limited by our bad conscience, our only restriction is a given amount of the different metals:

* Copper (Cu): 100grams
* Iron (Fe): 80grams
* Nickel (Ni): 10grams
* Aluminium (Al): 4grams
* Zinc (Zn): 10grams
* Tin (Sn): 3grams

### 1.2 Mathematical Formulation

 <table style="width:100%">
  <tr>
    <th colspan="2" style="text-align:left">Sets</th>
  </tr>

  <tr>
    <td>set of coins</td>
    <td>
<img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}C&space;&space;=&space;\{&space;1,\dots,|C|&space;\}" title="C = {1,...,|C|}" />
<!--LATEX
\color{NavyBlue}
C  = \{ 1,\dots,|C| \}
-->
</td>
  </tr>

<tr>
    <td>set of metals</td>
    <td>
<img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}M&space;&space;=&space;\{&space;1,\dots,|M|&space;\}" title="M = {1,...,|M| }" />
<!--LATEX
\color{NavyBlue}
M  = \{ 1,\dots,|M| \}
-->
</td>
  </tr>
</table>

<table style="width:100%">
<tr>
    <th colspan="2"  style="text-align:left">Parameters</th>
  </tr>

<tr>
	<td>quantity available of metal <i>m</i></td>
    <td>
  <img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}Q_m" title="Q_m" />
<!--LATEX
\color{NavyBlue}
Q_m
-->
</td>
  </tr>

<tr>
    <td>profit of coin  <i>c</i></td>
    <td>
  <img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}P_c" title="P_c" />
<!--LATEX
\color{NavyBlue}
P_c
-->
</td>
  </tr>

<tr>
    <td>needed quantity of metal  <i>m</i> for coin <i>c</i></td>
    <td>
  <img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}N_{cm}" title="N_cm" />
<!--LATEX
\color{NavyBlue}
N_{cm}
-->
</td>
  </tr>
</table>

<table style="width:100%">
  <tr>
    <th colspan="2"  style="text-align:left">Variables</th>
  <tr>

<tr>
    <td>number of coins of type  <i>c</i> to be forged</td>
    <td>
  <img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}x_c" title="x_c" />
<!--LATEX
\color{NavyBlue}
x_c
-->
</td>
  </tr>
</table>

<table style="width:100%">
<tr>
    <th colspan="2"  style="text-align:left">Model</th>
  </tr>

<tr>
    <td  colspan="2" style="text-align:center">
<img src="https://latex.codecogs.com/png.image?\dpi{110}&space;\color{NavyBlue}\begin{align*}\sum_{c&space;\in&space;C}&space;P_c&space;x_c&&space;\rightarrow&space;\max!&space;\\\mathrm{s.t.}&space;\quad&space;\sum_{c&space;\in&space;C}&space;N_{cm}&space;x_c&\leq&space;Q_m&&&space;m&space;\in&space;M&space;\\x_c&space;&&space;\in&space;\mathbb{N}&&&space;c&space;\in&space;C\end{align*}" title="objective: maximize profit | constraint: available metals restriction" />

<!--LATEX:
\color{NavyBlue}
\begin{align*}
\sum_{c \in C} P_c x_c
& \rightarrow \max! \\
\mathrm{s.t.} \quad \sum_{c \in C} N_{cm} x_c
&\leq Q_m
&& m \in M \\
x_c 
& \in \mathbb{N}
&& c \in C
\end{align*}
-->
</td>
  </tr>
</table> 

## 2. Implementation

### 2.1 Model `org.optsol.jmip_pojo_template.model.Model`

**_jMIP_** provides a class for our model definition: `LinearModel`<br>
Extending this abstract class requires you to provide:

1. a type parameter defining your available **constants** _(Section 2.2)_
2. a definition of your **variables** implementing `generateVariables()` method  _(Section 2.3)_
3. your definition of the **objective** by overriding `generateObjective()` method _(Section 2.4)_
4. a list of **constraints** in `generateConstraints()` method _(Section 2.5)_

### 2.2 Constants `org.optsol.jmip_pojo_template.model.constants.Constants`

First you have to define an object holding the input parameters of an instance of your optimization
problem. We prefer using an immutable object for that purpose. (see
Lombok's [@Value](https://projectlombok.org/features/Value)).
> Note: A different approach would be to define an interface for your constants. This is beneficial
> when you want use different implementations for your constants, for example when having different
> sources for your instance data.

We use our problem specific `Constants` to parameterize **_jMIP_**'s `LinearModel`

```java
public class Model extends LinearModel<Constants>
```

### 2.3 Variables `org.optsol.jmip_pojo_template.model.variables.Variables`

**_jMIP_** lazily generates the needed variables based on their names.
We recommend listing your variable group names in a dedicated class such as an enum or interface.
This avoids misspellings and facilitates refactoring.

```java
public interface Variables {

  String x = "x";
}
```

Using the builder of the `LinearVariable` you can define bounds for groups of variables or
restrict them to integer values. Any lazily generated variable not restricted in the
`LinearVariable.Builder`, will be unbounded and continous by default.

```java
@Override
protected IVariable<? super Constants, MPSolver, MPVariable> generateVariables() {
  return
      new LinearVariable.Builder()
          // x : int+
          .addIntVar(Variables.x)
          .addLowerBound(Variables.x, 0.)

          .build();
}
```

### 2.4 Objective `org.optsol.jmip_pojo_template.model.objective.MaximizeProfit`

Extending **_jMIP_**'s `LinearObjective` you can define the objective function of
your optimization model. `objective.setMaximization()` or `objective.setMinimization()` sets the
direction of enhancement.

```java
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
```

Finally provide an instance of your objective class in the `generateObjective()` method of your
`Model`.

```java
@Override
protected IObjective<
    ? super Constants, MPVariable, MPSolver> generateObjective() {
  return new MaximizeProfit();
}
```

### 2.5 Constraints `org.optsol.jmip_pojo_template.model.constraints.AvailableMetalQuantity`

Implementation of constraint groups should be based on **_jMIP_**'s
`LinearConstraint`.

#### This requires three steps for each constraint group:

- **(I) Define constraint index structure**<br>
  In accordance with our mathematical model, we define the constraint group scoped index _m_:

```java
public AvailableMetalQuantity() {
  //define constraint index m
  super("m");
}
```

> Note: By providing multiple index names to the super constructor, you can define indexes of
> arbitrary dimension. Non-indexed constraint groups consisting of one single constraint do not need
> to define indexes.

- **(II) Generate constraint index keys**<br>
  `ConstraintKey` class represents the constraint index structure. You can define the constraint key
  values by overriding the `createKeys()` method.

```java
@Override
protected Collection<ConstraintKey> createKeys(Constants constants) {
  //generate all indexes of constraint group:
  HashSet<ConstraintKey> indexes = new HashSet<>();
  for (int m : constants.get_M()) {
    indexes.add(new ConstraintKey(m));
  }
  return indexes;
}
```

- **(III) Configure constraints**<br>
  Implementing the method `configureConstraint()` is obligatory. With help of constants and
  variables the bounds and coefficients of the constraint with given index has to be defined.

```java
@Override
protected void configureConstraint(
    MPConstraint constraint,
    Constants constants,
    IVariableProvider<MPVariable> variables,
    ConstraintKey index) throws Exception {
  //configure constraint for index m:
  int m = index.get("m");

  //sum_c N_c_m * x_c <= Q_m
  constraint.setUb(constants.get_Q(m));

  for (int c : constants.get_C()) {
    constraint.setCoefficient(
        variables.getVar(Variables.x, c),
        constants.get_N(c, m));
  }
}
```

#### Finally stitch all defined constraint groups together:

Provide a collection of instances of each constraint group in the `generateConstraints()` method of
your `Model`:

```java
@Override
protected List<
    IConstraint<
        ? super Constants,
        MPVariable,
        MPSolver>> generateConstraints() {
  return List.of(
      new AvailableMetalQuantity());
}
```

---

## 3. Solving the Problem

### 3.1 Solution Definition `org.optsol.jmip_pojo_template.solver.Solution`

**_jMIP_** can automatically extract solutions from the solved model. You simply need to define an
interface describing the structure of your expected solution. This must be in accordance with the
declared variables.

- **Basic solution information**<br>
  Every solution contains basic information such as SolutionState, ObjectiveValue,
  BestObjectiveBound and SolutionTime. **_jMIP_**'s `ISolution` interface describes getters for
  these values. Just extend `ISolution` in your individual solution definition.

- **Values of variables**<br>
  To retrieve all values of a variable group, you simply need to define a method in your solution
  interface whose name starts with "get_"
  followed by a variable group name (e.g. `get_x()`).
  **_jMIP_** offers two options to access solution data. You must choose one of the two options by
  specifying the return type of your getter method:

    1. **_Access-by-index_**
        - specify return type as a single `Double`/`Integer`/`Boolean` according to the type of
          variable or as a multidimensional array (e.g., 1D: `Integer[]`, 2D: `Boolean[][]`)
        - example:

    ```java
    public interface Solution extends ISolution {
      Integer[] get_x();
    }
          
    //...  somewhere deep in your code
    Integer[] x = solution.get_x();
    // sum up all x_c values
    int total = 0;
    for (int c : constants.get_C()) {
      total += x[c];
    }
    ```

    2. **_Access-by-name_** via map
        - return type:<br>
          `java.util.Map` with
            - key type parameter: `String` because key objects represent variable names; a variable
              name is defined as a variable group name (e.g., "x") followed by an
              underscore-separated list of integer-valued indices (e.g., "_1" or "_1_8_7")
            - value type parameter: depends on the type of variable (`Double`/`Integer`/`Boolean`)
        - example:

    ```java
    public interface Solution extends ISolution {
      Map<String, Integer> get_x();
    }
             
    //...  somewhere deep in your code
    Map<String, Integer> x = solution.get_x();
    // sum up all x_c values
    int total = 0;
    for (int c : constants.get_C()) {
      String varName = "x_" + c;
      total += x.get(varName);
    }
    ```

> Note: In case your model has many variables with two or more indices, we recommend the
> access-by-name option.
> Otherwise, solution retrieval can become quite slow since **_jMIP_** makes heavily use of Java's
> reflection API under the hood... :wink:

### 3.2 Solver Definition `org.optsol.jmip_pojo_template.tests.SolverParameterTests`

A convenient way to set up a problem specific solver is to parameterize **_jMIP_**'s `LinearSolver` by using its built-in Builder.
Provide at least your Model-Definition `Model.class` and Solution-Interface `Solution.class`.

```java
LinearSolver solver =
    LinearSolver.builder(Model.class, Solution.class).build();
```

#### 3.2.1 Solver Engine

**_jMIP_** Framework is based on the
well-known [Google OR-Tools](https://developers.google.com/optimization). The following solvers are
prepackaged for Windows, Linux and MacOS:

* `MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING`: [CBC from the CoinOR project](https://github.com/coin-or/Cbc) (mixed-integer)
* `MPSolver.OptimizationProblemType.SCIP_MIXED_INTEGER_PROGRAMMING`: [SCIP](https://www.scipopt.org/) (mixed-integer)
* `MPSolver.OptimizationProblemType.GLOP_LINEAR_PROGRAMMING`: [GLOP](https://developers.google.com/optimization/lp/glop) (linear)
* `MPSolver.OptimizationProblemType.GUROBI_MIXED_INTEGER_PROGRAMMING`: [GUROBI](https://www.gurobi.com/) (mixed-integer) | Gurobi is not
  prepackaged! Gurobi must be installed under standard path on your machine!

> Disclaimer: The usage of certain solver packages may be restricted by an appropriate licensing.
> Please ensure correct licensing in accordance with the solver provider's usage terms.

#### 3.2.2 Enable Output

Enable detailed Output from the underlying solver-engine.
```java
LinearSolver solver =
    LinearSolver
        .builder(Model.class, Solution.class)
        .enableOutput()
        .build();
```

#### 3.2.2 Solver TimeLimit

Set a timelimit for the solution process.
```java
LinearSolver solver =
    LinearSolver
        .builder(Model.class, Solution.class)
        .timeLimit(Duration.ofSeconds(3))
        .build();
```

#### 3.2.3 Solver Mip-Gap

Configure your preferred MIP-Gap.
```java
LinearSolver solver =
    LinearSolver
        .builder(Model.class, Solution.class)
        .relativeMipGap(0.05)
        .build();
```

#### 3.2.4 Solver Specific String Parameterization

Set a solver specific String Parameter.
```java
LinearSolver solver =
    LinearSolver
        .builder(Model.class, Solution.class)
        .solverSpecificParameters("presolving/maxrounds = 0")
        .build();
```

### 3.3 Generate Solutions `org.optsol.jmip_pojo_template.tests.SolverTests`

When calling the Solvers `generateSolution`-Method a new model will be generated from your `Constants` and an optimization will be started to find a `Solution`.

Now it is _really_ just three lines of code to retrieve solutions:

```java
Constants constants =
    new Constants(/*TODO: provide constants*/);

LinearSolver solver =
    LinearSolver.builder(Model.class, Solution.class).build();

Solution solution =
    solver.generateSolution(constants);
```

Additionally you may choose a different solver engine or customize some solver parameters such as timelimit, MIP-gap or detailed solver outputs.

```java
LinearSolver solver =
    LinearSolver
        .builder(Model.class, Solution.class)        
        .solverEngineType(MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING)
        .relativeMipGap(0.01)
        .enableOutput().build();
```

