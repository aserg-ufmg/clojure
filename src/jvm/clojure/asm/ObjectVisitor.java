package clojure.asm;

public class ObjectVisitor {

	/**
	 * The annotation visitor to which this visitor must delegate method calls.
	 * May be null.
	 */
	protected AnnotationVisitor av;

	public ObjectVisitor() {
		super();
	}

	/**
	 * Visits a primitive value of the annotation.
	 *
	 * @param name
	 *            the value name.
	 * @param value
	 *            the actual value, whose type must be {@link Byte},
	 *            {@link Boolean}, {@link Character}, {@link Short},
	 *            {@link Integer} , {@link Long}, {@link Float}, {@link Double},
	 *            {@link String} or {@link Type} or OBJECT or ARRAY sort. This
	 *            value can also be an array of byte, boolean, short, char, int,
	 *            long, float or double values (this is equivalent to using
	 *            {@link #visitArray visitArray} and visiting each array element
	 *            in turn, but is more convenient).
	 */
	public void visit(String name, Object value) {
	    if (av != null) {
	        av.visit(name, value);
	    }
	}

}