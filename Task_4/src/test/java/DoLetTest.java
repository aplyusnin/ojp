import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.nsu.fit.javalisp.parser.LispParser;
import ru.nsu.fit.javalisp.translator.LispTransformer;

import java.lang.reflect.Method;

public class DoLetTest {

	@Test
	@Ignore
	public void doletTest(){
		try {
			String name = "./src/test/resources/dolet.cl";
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
