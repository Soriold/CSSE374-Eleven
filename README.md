# Design

This tool is designed to interpret a set of Java classes and generate an accurate UML diagram to describe the design of the sfotware. It is build atop ASM's Java Parsing Library. A visitor pattern is used to iteratively interpret the design hierarchy of the subject software. A basic strategy pattern is used in conjunction with the visitor pattern to detect code stereotypes. An intermediate set of data container classes is used to create an intermediate representation of the software design as it is being visited and interpreted. After the entire design has been evaluated, the intermediate data containers are parsed into a special text output that can be used in GraphViz to generate a UML diagram. This tool can also be used to generate Sequence Diagrams to describe how the classes interact with each other. This is accomplished using the same visitor pattern as the UML Generator and does not require additional parsing. One parse can generate both the UML and Sequence Diagram.

## Mileston 6:

![alt tag](https://raw.githubusercontent.com/Soriold/CSSE374-Eleven/master/CSSE374-Eleven/M6%20Project%20UML%20Diagrams/M6%20Manual%20UML.png)

Milestone 6 update: The design now includes the ability to detect the Composite pattern. IMultipleClassPatternSpotter and ISingleClassSpotter were re-merged into IPatternSpotter. Pattern detection now always takes the entire parsed model, but otherwise operates very similarly to the previous milestones.

## Milestone 5:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M5%20Project%20UML%20Diagrams/M5%20Manual%20UML.png?raw=true)

Milestone 5 update: The design now includes the ability to detect Adapter and Decorator patterns, as well as support for any pattern detection that involves multiple classes. The IPatternSpotter interface was seperated into two classes, IMultipleClassesPatternSpotter and ISingleClassPatternSpotter to distinguish between what type of pattern is being searched for. Other than the splitting of the interface, pattern detection works very similar to how it did in the previous milestone.

## Milestone 4:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M4%20Project%20UML%20Diagrams/M4%20Manual%20UML.png?raw=true)

Milestone 4 update: The design now includes the ability to detect Singletons, as well as support for adding additional pattern detection. The PatternRecognizer  This is achieved through use of the strategy pattern, with each design pattern having it's own detector. Pattern detection happens in Class.accept(IVisitor v). at the beginning of the call, the pattern of the class (a PatternType enum) is set with a call to the static method PatternRecognizer.recognize(IClass c). Inside PatternRecognizer there is a static list of IPatternSpotters (this is the strategy part of the implementation). Each IPatternSpotter has a single public method, spot(IClass c) that takes in a class, and returns the PatternType enum associated with the stereotype if the class fits the pattern, or PatternType.NOT\_FOUND if it does not. Inside the recognize() method we loop over the list of IPatternSpotters, applying each to the IClass parameter. Currently, if an IPatternSpotter returns something other than PatternType.NOT\_FOUND that value is returned immediately.

## Milestone 3:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M3%20Project%20UML%20Diagrams/M3%20Manual%20UML.jpg?raw=true)

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M3%20Sequence%20Diagrams/Manual%20SVEdit%202Deep.jpg?raw=true)

Milestone 3 update: The design now includes sequence diagram generation. This was accomplished by creating a new outputstream, "SDEditOutputStream" and a new instance variable in Method which contains all the methods that are called by that method. SDEditOutputStream uses this new variable to generate the sequence diagram. 

## Milestone 1 and 2:

![alt tag](https://github.com/Soriold/CSSE374-Eleven/blob/master/CSSE374-Eleven/M1%20Project%20UML%20Diagrams/M1%20Manually%20Created%20UML%20Diagram.png?raw=true)

Milestone 2 update: There was only one major design change during this Milestone. In order to parse method bodies (to find "use" cases), we needed to create a special subclass of MethodVisitor (which we called MethodBodyVisitor). In MethodBodyVisitor, we overrided the visitMethodInsn() method to parse each method instruction and find each one that created a new object. They we added the classes of these created objects to a HashSet<String> in the current Class object. When generating the text output, we iterated over this set and generated the appropriate "uses" arrows. MethodBodyVisitor followed the same visitor and decorator patterns as the three other visitor objects. In ClassMethodVisitor's visitMethod() method, instead of returning the vanilla MethodVisitor we get from the call to super's visitMethod() method, we decorate it with a new MethodBodyVisitor and then return it.

Milestone 2 Refactoring update: There was only one major design change during the refactoring after our Milestone 2 meeting. Previously, each of our model components had implemented a IGraphVizComponent interface and had a toGraphViz() method that generated the special text output for that component. After ASM parsing, our model would iterate over all of its components and gradually build this special GraphViz text output. Instead of doing this, we updated our design to use a visitor pattern to visit each model component and generate the GraphViz text output. Switching to this visitor pattern will make it much easier to generate new, different types of output in the future; we can reuse the same visitor design that is already in place.

# Responsibilities:
Ben Kimmel - Wrote ASM parsing and GraphViz text output code with Tayler. Helped Tayler with creating UML diagrams. Wrote return type and parameter Uses edge code. Helped Shayna write the parsing and processing code to produce basic sequence diagrams. Added support for including parameters and return types on sequence diagrams. Wrote framework for pattern detection, as well as Singleton detection. Improved the framework for pattern detection to work with multiple classes. Troubleshot the pattern detection classes and fixed bugs. Produced UML diagrams and updated README.

Tayler How - Wrote ASM parsing code with Ben. Helped Ben with writing GraphViz text output code. Created & updated manual/auto-generated UML diagrams. Wrote code to detect and parse object instantiation inside method bodies for "uses" arrows. Wrote test cases for edge cases of use/associate arrows & factory test cases. Wrote integration test cases for sequence diagrams, troubleshot sequence diagram code. Created manual sequence diagram for Collections.shuffle(). Wrote integration tests for singleton detection, and troubleshot singleton detection code. Wrote the DecoratorSpotter for detecting decorator patterns in the code. Wrote original Composite detection. Wrote integration tests for AdapterSpotter, CompositeSpotter and DecoratorSpotter.

Shayna Oriold - Wrote testing suite for parsing software. Also troubleshot Ben and Tayler's code. Wrote Association edge code. Wrote the parsing and processing code to produce basic sequence diagrams. Created manual sequence diagram for SDEditOutputStream.writeMethod(). Created manual UML diagrams for all current milestones. Helped Taylor troubleshoot singleton detection code. Wrote the AdapterSpotter for detecting Adapter patterns in the code. Helped troubleshoot the pattern spotter classes.
Simplified the current UML Diagrams. Improved Composite spotting.

All team members were equally involved in important matters such as software design decisions, etcetera.

# Instructions: 
DesignParser now takes four arguments. The first argument is the file that contains the fully qualified names of the classes you want to parse. The second argument is either "true" or "false", depending on whether you want to generate a sequence diagram. If you do want to generate a sequence diagram, and have set the second argument to "true", the third argument should be the fully qualified name of the method you want to generate a diagram for, including the method parameters. the fourth and final argument should be the number of levels of recursion that you want for the sequence diagram. Be careful setting the levels of recursion, as the diagram may grow very quickly. For example, under Run Configurations in Eclipse, we used the arguments "collectionsArgs.txt true java.util.Collections.shuffle(List<T> list) 3" to generate the sequence diagram of java.util.Collections.shuffle() at a depth of 3. After running DesignParser with the desired arguments, two text files are created: GVOutput.txt contains the GraphViz code to make a UML diagram of the parsed classes, and SDEditOutput.txt contains the SDEdit markup for the sequence diagram of the given method.
