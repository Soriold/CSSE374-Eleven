# Design:
This tool is designed to interpret a set of Java classes and generate an accurate UML diagram to describe the design of the sfotware. It is build atop ASM's Java Parsing Library. A visitor pattern is used to iteratively interpret the design hierarchy of the subject software. A basic decorator pattern is used in conjunction with the visitor pattern. An intermediate set of data container classes is used to create an intermediate representation of the software design as it is being visited and interpreted. After the entire design has been evaluated, the intermediate data containers are parsed into a special text output that can be used in GraphViz to generate a UML diagram.

Milestone 2 update: There was only one major design change during this Milestone. In order to parse method bodies (to find "use" cases), we needed to create a special subclass of MethodVisitor (which we called MethodBodyVisitor). In MethodBodyVisitor, we overrided the visitMethodInsn() method to parse each method instruction and find each one that created a new object. They we added the classes of these created objects to a HashSet<String> in the current Class object. When generating the text output, we iterated over this set and generated the appropriate "uses" arrows. MethodBodyVisitor followed the same visitor and decorator patterns as the three other visitor objects. In ClassMethodVisitor's visitMethod() method, instead of returning the vanilla MethodVisitor we get from the call to super's visitMethod() method, we decorate it with a new MethodBodyVisitor and then return it.

# Responsibilities:
Ben Kimmel - Wrote ASM parsing and GraphViz text output code with Tayler. Helped Tayler with creating UML diagrams. Wrote return type and parameter Uses edge code.

Tayler How - Wrote ASM parsing code with Ben. Helped Ben with writing GraphViz text output code. Created & updated manual/auto-generated UML diagrams. Wrote code to detect and parse object instantiation inside method bodies for "uses" arrows. Wrote test cases for edge cases of use/associate arrows & factory test cases.

Shayna Oriold - Wrote testing suite for parsing software. Also troubleshot Ben and Tayler's code. Wrote Association edge code. 

All team members were equally involved in important matters such as software design decisions, etcetera.

# Instructions: 
To use this tool, run DesignParser with arguments of the classes you wish to analyse. For example, under Run Configurations in Eclipse, we used the arguments "src.problem.asm.DesignParser src.problem.asm.ClassDeclarationVisitor src.problem.components.Class src.problem.components.IClass" for some of our testing. After running DesignParser with the desired arguments, a special text output will be printed in the console. This text needs to be used in GraphViz. After launching GraphViz's gvedit.exe, create a new .gv file and copy/paste the special text output into the .gv file. Then all you have to do is click the 'Run' button in gvedit and a UML diagram for your software will be created!
