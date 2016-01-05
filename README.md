# Design:
This tool is designed to describe the methods and dependancies of Java code using the ASM library. It relies on a series of visitor classes to parse the Java source code and determine how it is formulated. 

# Responsibilities:
Ben Kimmel - Wrote parsing and GraphViz code with Taylor.

Taylor How - Wrote Parsing and GraphViz code with Ben.

Shayna Oriold - Wrote test cases and troubleshot Ben and Taylor's code.

# Instructions: 
To use this tool, run DesignParser with arguments of the classes you wish to analyse. For example, under Run Configurations in Eclipse, we used the arguments "src.problem.asm.DesignParser src.problem.asm.ClassDeclarationVisitor src.problem.components.Class src.problem.components.IClass" for some of our testing.
