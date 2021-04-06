import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.nsu.fit.ojp.Task_4.parser.LispParser;
import ru.nsu.fit.ojp.Task_4.translator.LispTransformer;

import java.lang.reflect.Method;

public class ifVTest {

	@Test
	@Ignore
	public void ifVTest(){
		try {
			String name = "./src/test/resources/ifV.cl";
			LispParser parser = new LispParser(name);

			var nodes = parser.parse();
			LispTransformer transformer = new LispTransformer();


			Class clazz = transformer.generate(nodes);
			Object source = clazz.newInstance();
			Method method1 = clazz.getDeclaredMethod("initGlobals");
			method1.invoke(source);
			Method method2 = clazz.getDeclaredMethod("evaluate");
			method2.invoke(source);

		}
		catch (Exception e){
			Assert.fail();
		}
	}
}
