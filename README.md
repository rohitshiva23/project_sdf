# project_sdf
INTRODUCTION  

PROJECT STRUCTURE 

/my_project
├── build/
│   ├── MyInfArith.class
│   └── arbitraryarithmetic/
│       ├── AFloat.class
│       └── AInteger.class
├── src/
│   ├── MyInfArith.java
│   ├── MyInfArith.class
│   └── arbitraryarithmetic/
│       ├── AFloat.java
│       ├── AFloat.class
│       ├── AInteger.java
│       └── AInteger.class
├── arbitraryarithmetic/
│   └── aarithmetic.jar
├── run_project.py
├── build.xml
├── README.md
└── report.pdf

USES OF THE FILES : 

We have a arbitraryarithmetic package consisting of these 2 classes AFloat and AInteger. Also a MyInfArith class which has main method to test these.   
And a python script and make file called build.xml which have been used as well. 

The python script which compiles the .java files into .class files and runs the commands and the compiled .class files also lie in the src directory of root.

The makefile  compiles the .java files into .class files into the build directory and the ant jar command gives the arbitraryarithmetic/aarithmetic.jar which can be linked to any executable. 

WAYS TO RUN THE PROGRAM :  

This can be done in 3 ways to verify the program  \\

1) using the main class itself : java MyInfArith float mul 2.5 2.0 \\
                                  5.0 \\

2) using the python script : python3 runproject.py int add 3 4 \\
                              7\\
                              
3) using ant run command : ant run -Darg1=float -Darg2=add -Darg3=100.5 -Darg4=3.25 \\

103.75 \\

