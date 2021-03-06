package toberumono.utils.functions;

import java.util.function.BiConsumer;

/**
 * A simple functional interface that represents the equivalent of {@link BiConsumer} that can throw an {@link Exception}.
 * 
 * @author Toberumono
 * @param <T>
 *            the type of the first argument
 * @param <U>
 *            the type of the second argument
 */
@FunctionalInterface
public interface ExceptedBiConsumer<T, U> {
	
	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param t
	 *            the first input argument
	 * @param u
	 *            the second input argument
	 * @throws Exception
	 *             if something goes wrong
	 */
	public void accept(T t, U u) throws Exception;
	
	/**
	 * Performs this operation on the given arguments.<br>
	 * Forwards to {@link #accept(Object, Object)}
	 *
	 * @param t
	 *            the first input argument
	 * @param u
	 *            the second input argument
	 * @throws Exception
	 *             if something goes wrong
	 */
	@Deprecated
	public default void apply(T t, U u) throws Exception {
		accept(t, u);
	}
	
	/**
	 * Returns a {@link BiConsumer} that wraps this {@link ExceptedBiConsumer} and, if an {@link Exception} is thrown, either
	 * silently fails or prints its stack trace.
	 * 
	 * @param printStackTrace
	 *            whether to print the stack trace of {@link Exception} thrown by this function when called from within the
	 *            wrapper
	 * @return a {@link BiConsumer} that wraps this {@link ExceptedBiConsumer}
	 */
	public default BiConsumer<T, U> toBiConsumer(boolean printStackTrace) {
		if (printStackTrace)
			return (t, u) -> {
				try {
					accept(t, u);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			};
		else
			return (t, u) -> {
				try {
					accept(t, u);
				}
				catch (Exception e) {}
			};
	}
}
