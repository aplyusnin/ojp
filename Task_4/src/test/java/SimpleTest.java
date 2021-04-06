import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.nsu.fit.ojp.Task_4.parser.LispParser;
import ru.nsu.fit.ojp.Task_4.translator.LispTransformer;

import java.lang.reflect.Method;

public class SimpleTest {

	@Test
	@Ignore
	public void simpleTest(){
		try {
			String name = "./src/test/resources/simple.cl";
			LispParser parser = new LispParser(name);

			var nodes = parser.parse();
			LispTransformer transformer = new LispTransformer();


			Class clazz = transformer.generate(nodes);
			Object source = clazz.newInstance();
			Method method = clazz.getDeclaredMethod("evaluate");
			method.invoke(source);

		}
		catch (Exception e){
			Assert.fail();
		}
	}
}
