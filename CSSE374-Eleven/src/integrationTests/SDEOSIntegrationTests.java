package integrationTests;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import src.problem.asm.DesignParser;
import src.problem.components.IClass;
import src.problem.components.IModel;
import src.problem.components.Model;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.outputvisitor.SDEditOutputStream;

public class SDEOSIntegrationTests {

	@Test
	public void testHasClassInstances() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("java.util.Collections", model);
		model.addClass(clazz);

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		SDEditOutputStream sdeos = new SDEditOutputStream(resultStream);
		sdeos.writeMethod((Model) model, "java.util.Collections.shuffle(List<T> list)", 2);
		sdeos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("Random:Random\nList:List\nCollections:Collections\nListIterator:ListIterator"));
	}

	@Test
	public void testHasMethodCalls() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("java.util.Collections", model);
		model.addClass(clazz);

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		SDEditOutputStream sdeos = new SDEditOutputStream(resultStream);
		sdeos.writeMethod((Model) model, "java.util.Collections.shuffle(List<T> list)", 2);
		sdeos.close();
		String result = resultStream.toString();

		assertTrue(result.contains(
				"Collections:void=Random.create()\nCollections:void=Collections.shuffle(List Random )\nCollections:int=List.size()\nCollections:int=Random.nextInt(I )\nCollections:void=Collections.swap(List I I )\nCollections:Object=List.toArray()\nCollections:int=Random.nextInt(I )\nCollections:void=Collections.swap(Object I I )\nCollections:ListIterator=List.listIterator()\nCollections:Object=ListIterator.next()\nCollections:void=ListIterator.set(Object )"));
	}

}
