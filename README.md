# Design

This tool is designed to interpret a set of Java classes and generate an accurate UML diagram to describe the design of the sfotware. It is build atop ASM's Java Parsing Library. A visitor pattern is used to iteratively interpret the design hierarchy of the subject software. A basic decorator pattern is used in conjunction with the visitor pattern. An intermediate set of data container classes is used to create an intermediate representation of the software design as it is being visited and interpreted. After the entire design has been evaluated, the intermediate data containers are parsed into a special text output that can be used in GraphViz to generate a UML diagram. This tool can also be used to generate Sequence Diagrams to describe how the classes interact with each other. This is accomplished using the same visitor pattern as the UML Generator and does not require additional parsing. One parse can generate both the UML and Sequence Diagram.

Milestone 4:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M4%20Project%20UML%20Diagrams/M4%20Manual%20UML.png?raw=true)

Milestone 3:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M3%20Project%20UML%20Diagrams/M3%20Manual%20UML.jpg?raw=true)

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M3%20Sequence%20Diagrams/Manual%20SVEdit%202Deep.jpg?raw=true)

Milestone 3 update: The design now includes sequence diagram generation. This was accomplished by creating a new outputstream, "SDEditOutputStream" and a new instance variable in Method which contains all the methods that are called by that method. SDEditOutputStream uses this new variable to generate the sequence diagram. 

Milestone 1 and 2:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M2%20Project%20UML%20Diagrams/M2%20Manually%20Created%20UML%20Diagram.png?raw=true)

Milestone 2 update: There was only one major design change during this Milestone. In order to parse method bodies (to find "use" cases), we needed to create a special subclass of MethodVisitor (which we called MethodBodyVisitor). In MethodBodyVisitor, we overrided the visitMethodInsn() method to parse each method instruction and find each one that created a new object. They we added the classes of these created objects to a HashSet<String> in the current Class object. When generating the text output, we iterated over this set and generated the appropriate "uses" arrows. MethodBodyVisitor followed the same visitor and decorator patterns as the three other visitor objects. In ClassMethodVisitor's visitMethod() method, instead of returning the vanilla MethodVisitor we get from the call to super's visitMethod() method, we decorate it with a new MethodBodyVisitor and then return it.

Milestone 2 Refactoring update: There was only one major design change during the refactoring after our Milestone 2 meeting. Previously, each of our model components had implemented a IGraphVizComponent interface and had a toGraphViz() method that generated the special text output for that component. After ASM parsing, our model would iterate over all of its components and gradually build this special GraphViz text output. Instead of doing this, we updated our design to use a visitor pattern to visit each model component and generate the GraphViz text output. Switching to this visitor pattern will make it much easier to generate new, different types of output in the future; we can reuse the same visitor design that is already in place.

# Responsibilities:
Ben Kimmel - Wrote ASM parsing and GraphViz text output code with Tayler. Helped Tayler with creating UML diagrams. Wrote return type and parameter Uses edge code. Helped Shayna write the parsing and processing code to produce basic sequence diagrams. Added support for including parameters and return types on sequence diagrams.

Tayler How - Wrote ASM parsing code with Ben. Helped Ben with writing GraphViz text output code. Created & updated manual/auto-generated UML diagrams. Wrote code to detect and parse object instantiation inside method bodies for "uses" arrows. Wrote test cases for edge cases of use/associate arrows & factory test cases. Wrote test cases for sequence diagrams, throubleshot sequence diagram code. Created manual sequence diagram for Collections.shuffle().

Shayna Oriold - Wrote testing suite for parsing software. Also troubleshot Ben and Tayler's code. Wrote Association edge code. Wrote the parsing and processing code to produce basic sequence diagrams. Created manual sequence diagram for SDEditOutputStream.writeMethod().

All team members were equally involved in important matters such as software design decisions, etcetera.

# Instructions: 
To use this tool, run DesignParser with arguments of the configuration file you want to use, the full method signature you wish to create a sequence diagram for, and the depth you would like the sequence diagram to reach. For example, under Run Configurations in Eclipse, we used the arguments "collectionsArgs.txt java.util.Collections.shuffle(List<T> list) 3" to generate the sequence diagram of java.util.Collections.shuffle() at a depth of 3. After running DesignParser with the desired arguments, two text files are created: GVOutput.txt contains the GraphViz code to make a UML diagram of the parsed classes, and SDEditOutput.txt contains the SDEdit markup for the sequence diagram of the given method.
