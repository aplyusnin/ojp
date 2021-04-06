package ru.nsu.fit.ojp.Task_4.translator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

/**
 * Class with standard functions
 */
public class BaseSource {

	private Scanner sc;

	public BaseSource(){
		sc = new Scanner(System.in);
	}

	private Double add(Double a, Double b){
		return a + b;
	}

	private Double add(Integer a, Double b){
		return a + b;
	}

	private Double add(Double a, Integer b){
		return a + b;
	}

	private Integer add(Integer a, Integer b){
		return a + b;
	}

	protected Object add(Object a, Object b) throws ClassCastException {
		try	{
			return add((Integer)a, (Integer)b);
		}
		catch (Exception e){}
		try	{
			return add((Double)a, (Integer)b);
		}
		catch (Exception e){}
		try	{
			return add((Integer)a, (Double)b);
		}
		catch (Exception e){}
		try	{
			return add((Double)a, (Double)b);
		}
		catch (Exception e){}
		throw new ClassCastException();
	}

	private Double sub(Double a, Double b){
		return a - b;
	}

	private Double sub(Integer a, Double b){
		return a - b;
	}

	private Double sub(Double a, Integer b){
		return a - b;
	}

	private Integer sub(Integer a, Integer b){
		return a - b;
	}

	protected Object sub(Object a, Object b) throws ClassCastException {
		try	{
			return sub((Integer)a, (Integer)b);
		}
		catch (Exception e){}
		try	{
			return sub((Double)a, (Integer)b);
		}
		catch (Exception e){}
		try	{
			return sub((Integer)a, (Double)b);
		}
		catch (Exception e){}
		try	{
			return sub((Double)a, (Double)b);
		}
		catch (Exception e){}
		throw new ClassCastException();
	}

	protected Double castD(Object o) throws Exception {
		if (String.class.isInstance(o)){
			return Double.valueOf((String)o);
		}
		else if (Number.class.isInstance(o)){
			return ((Number)o).doubleValue();
		}
		throw new Exception("Cant cast to float");
	}

	protected Integer castI(Object o) throws Exception {
		if (String.class.isInstance(o)){
			return Integer.valueOf((String)o);
		}
		else if (Number.class.isInstance(o)){
			return ((Number)o).intValue();
		}
		throw new Exception("Cant cast to float");
	}

	protected String castS(Object o) throws Exception{
		return o.toString();
	}

	private Double mul(Double a, Double b){
		return a * b;
	}

	private Double mul(Integer a, Double b){
		return a * b;
	}

	private Double mul(Double a, Integer b){
		return a * b;
	}

	private Integer mul(Integer a, Integer b){
		return a * b;
	}

	protected Object mul(Object a, Object b) throws ClassCastException {
		try	{
			return mul((Integer)a, (Integer)b);
		}
		catch (Exception e){}
		try	{
			return mul((Double)a, (Integer)b);
		}
		catch (Exception e){}
		try	{
			return mul((Integer)a, (Double)b);
		}
		catch (Exception e){}
		try	{
			return mul((Double)a, (Double)b);
		}
		catch (Exception e){}
		throw new ClassCastException();
	}

	protected Object div(Object a, Object b) throws ClassCastException {
		try	{
			return (Double)a / (Double)b;
		}
		catch (Exception e){}
		throw new ClassCastException();
	}

	protected Object isEqual(Object a, Object b) {
		return a.equals(b);
	}

	private Object isLess(Double a, Double b){
		return a < b;
	}
	private Object isLess(Integer a, Double b){
		return a.doubleValue() < b;
	}
	private Object isLess(Double a, Integer b){
		return a < b.doubleValue();
	}
	private Object isLess(Integer a, Integer b){
		return a < b;
	}
	protected Object isLess(Object a, Object b) {
		try{
			return isLess((Integer)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isLess((Double)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isLess((Integer)a, (Double)b);
		}
		catch (Exception ignored){}
		return isLess((Double)a, (Double)b);
	}

	private Object isLeq(Double a, Double b){
		return a <= b;
	}
	private Object isLeq(Integer a, Double b){
		return a.doubleValue() <= b;
	}
	private Object isLeq(Double a, Integer b){
		return a <= b.doubleValue();
	}
	private Object isLeq(Integer a, Integer b){
		return a <= b;
	}
	protected Object isLeq(Object a, Object b) {
		try{
			return isLeq((Integer)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isLeq((Double)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isLeq((Integer)a, (Double)b);
		}
		catch (Exception ignored){}
		return isLeq((Double)a, (Double)b);
	}

	private Object isGt(Double a, Double b){
		return a > b;
	}
	private Object isGt(Integer a, Double b){
		return a.doubleValue() > b;
	}
	private Object isGt(Double a, Integer b){
		return a > b.doubleValue();
	}
	private Object isGt(Integer a, Integer b){
		return a > b;
	}
	protected Object isGt(Object a, Object b) {
		try{
			return isGt((Integer)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isGt((Double)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isGt((Integer)a, (Double)b);
		}
		catch (Exception ignored){}
		return isGt((Double)a, (Double)b);
	}
	private Object isGte(Double a, Double b){
		return a >= b;
	}
	private Object isGte(Integer a, Double b){
		return a.doubleValue() >= b;
	}
	private Object isGte(Double a, Integer b){
		return a >= b.doubleValue();
	}
	private Object isGte(Integer a, Integer b){
		return a >= b;
	}
	protected Object isGte(Object a, Object b) {
		try{
			return isGte((Integer)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isGte((Double)a, (Integer)b);
		}
		catch (Exception ignored){}
		try{
			return isGte((Integer)a, (Double)b);
		}
		catch (Exception ignored){}
		return isGte((Double)a, (Double)b);
	}

	protected Object inc(Integer a){
		return (a + 1);
	}

	protected Object inc(Double a){
		return (a + 1);
	}

	protected Object inc(Object a) throws ClassCastException{
		try{
			return inc((Integer)a);
		}
		catch (Exception e){}
		return inc((Double) a);
	}

	protected Object not(Object a) throws ClassCastException{
		return !((Boolean)a);
	}

	protected Object and(Object a, Object b) throws ClassCastException{
		return (Boolean)a && (Boolean)b;
	}
	protected Object or(Object a, Object b) throws ClassCastException{
		return (Boolean)a || (Boolean)b;
	}


	protected Object read(){
		return sc.next();
	}

	protected Object print(Object a){
		System.out.print(a);
		return null;
	}

	protected Object println(Object a){
		System.out.println(a);
		return null;
	}

	protected Object list(Object ... objects){
		return new LinkedList<>(Arrays.asList(objects));
	}

	protected Object range(Object left, Object right){
		int l = (Integer)left;
		int r = (Integer)right;

		List<Object> list = new LinkedList<>();
		for (int i = Math.min(l, r); i <= Math.max(l, r);  i++){
			list.add(i);
		}
		if (r < l) return reverse(list);
		else return list;
	}

	protected Object get(Object id, Object list){
		return ((List<Object>)list).get((Integer)id);
	}


	protected Object head(Object list){
		return ((List<Object>)list).get(0);
	}

	protected Object tail(Object list){
		var t  = new LinkedList<Object>();
		var it = ((List<Object>)list).iterator();
		try {
			it.next();
		}
		catch (Exception ignored){

		}
		while (it.hasNext()){
			t.add(it.next());
		}
		return t;
	}

	protected Object reverse(Object list){
		var t = new LinkedList<Object>();
		t.addAll((List<Object>)list);
		Collections.reverse(t);
		return t;
	}

	protected Object concat(Object list1, Object list2){
		var t = new LinkedList<>();
		t.addAll((List<Object>)list1);
		t.addAll((List<Object>)list2);
		return t;
	}


	private Method findMethod(String name){
		Class mclass = this.getClass();
		Method[] methods = mclass.getDeclaredMethods();
		for (var x : methods){
			if (x.toString().contains(name) && !Modifier.isPrivate(x.getModifiers())){
				return x;
			}
		}
		mclass = BaseSource.class;
		methods = mclass.getDeclaredMethods();
		for (var x : methods){
			if (x.toString().contains(name) && !Modifier.isPrivate(x.getModifiers())){
				return x;
			}
		}
		return null;
	}

	protected Object invokeMethod(Object name, Object... params) throws Exception{
		Method m = findMethod((String)name);
		return m.invoke(this, params);
	}

	protected Object map(Object name, Object list) throws Exception{
		var t = new LinkedList<>();
		for (var x : (List<Object>)list){
			t.add(invokeMethod(name, x));
		}
		return t;
	}

	protected Object reduce(Object name, Object list, Object acc) throws Exception {
		Object res = acc;
		for (var x : (List<Object>)list){
			res = invokeMethod(name, res, x);
		}
		return res;
	}

	public void evaluate(){

	}
}
