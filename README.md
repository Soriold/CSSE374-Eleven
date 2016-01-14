# Design:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/raw/master/CSSE374-Eleven/M2%20Project%20UML%20Diagrams/Auto-Generated%20UML%20Diagram.png)

This tool is designed to interpret a set of Java classes and generate an accurate UML diagram to describe the design of the sfotware. It is build atop ASM's Java Parsing Library. A visitor pattern is used to iteratively interpret the design hierarchy of the subject software. A basic decorator pattern is used in conjunction with the visitor pattern. An intermediate set of data container classes is used to create an intermediate representation of the software design as it is being visited and interpreted. After the entire design has been evaluated, the intermediate data containers are parsed into a special text output that can be used in GraphViz to generate a UML diagram.

Milestone 2 update: There was only one major design change during this Milestone. In order to parse method bodies (to find "use" cases), we needed to create a special subclass of MethodVisitor (which we called MethodBodyVisitor). In MethodBodyVisitor, we overrided the visitMethodInsn() method to parse each method instruction and find each one that created a new object. They we added the classes of these created objects to a HashSet<String> in the current Class object. When generating the text output, we iterated over this set and generated the appropriate "uses" arrows. MethodBodyVisitor followed the same visitor and decorator patterns as the three other visitor objects. In ClassMethodVisitor's visitMethod() method, instead of returning the vanilla MethodVisitor we get from the call to super's visitMethod() method, we decorate it with a new MethodBodyVisitor and then return it.

Milestone 2 Refactoring update: There was only one major design change during the refactoring after our Milestone 2 meeting. Previously, each of our model components had implemented a IGraphVizComponent interface and had a toGraphViz() method that generated the special text output for that component. After ASM parsing, our model would iterate over all of its components and gradually build this special GraphViz text output. Instead of doing this, we updated our design to use a visitor pattern to visit each model component and generate the GraphViz text output. Switching to this visitor pattern will make it much easier to generate new, different types of output in the future; we can reuse the same visitor design that is already in place.

# Responsibilities:
Ben Kimmel - Wrote ASM parsing and GraphViz text output code with Tayler. Helped Tayler with creating UML diagrams. Wrote return type and parameter Uses edge code. Helped Shayna write the parsing and processing code to produce basic sequence diagrams. Added support for including parameters and return types on sequence diagrams.

Tayler How - Wrote ASM parsing code with Ben. Helped Ben with writing GraphViz text output code. Created & updated manual/auto-generated UML diagrams. Wrote code to detect and parse object instantiation inside method bodies for "uses" arrows. Wrote test cases for edge cases of use/associate arrows & factory test cases. Wrote test cases for sequence diagrams, throubleshot sequence diagram code.

Shayna Oriold - Wrote testing suite for parsing software. Also troubleshot Ben and Tayler's code. Wrote Association edge code. Wrote the parsing and processing code to produce basic sequence diagrams.

All team members were equally involved in important matters such as software design decisions, etcetera.

# Instructions: 
To use this tool, run DesignParser with arguments of the configuration file you want to use, and the full method signature you wish to create a sequence diagram for. For example, under Run Configurations in Eclipse, we used the arguments "collectionsArgs.txt java.util.Collections.shuffle(List<T> list)" to generate the sequence diagram of java.util.Collections.shuffle(). After running DesignParser with the desired arguments, two text files are created: GVOutput.txt contains the GraphViz code to make a UML diagram of the parsed classes, and SDEditOutput.txt contains the SDEdit markup for the sequence diagram of the given method.
