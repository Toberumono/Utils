package toberumono.utils.functions;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A simple functional interface that represents the equivalent of {@link Predicate} that can throw an {@link Exception}.<br>
 * {@link #and(ExceptedPredicate)}, {@link #negate()}, {@link #or(ExceptedPredicate)}, and {@link #isEqual(Object)} (including their documentation)
 * are all from {@link Predicate} in the Java API.
 * 
 * @author Toberumono
 * @param <T>
 *            the type of the first argument
 */
@FunctionalInterface
public interface ExceptedPredicate<T> {
	
	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param t
	 *            the input argument
	 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
	 * @throws Exception
	 *             if something goes wrong
	 */
	public boolean test(T t) throws Exception;
	
	/**
	 * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. When evaluating the composed
	 * predicate, if this predicate is {@code false}, then the {@code other} predicate is not evaluated.
	 * <p>
	 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @param other
	 *            a predicate that will be logically-ANDed with this predicate
	 * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the {@code other} predicate
	 * @throws NullPointerException
	 *             if other is {@code null}
	 */
	public default ExceptedPredicate<T> and(ExceptedPredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}
	
	/**
	 * Returns a predicate that represents the logical negation of this predicate.
	 *
	 * @return a predicate that represents the logical negation of this predicate
	 */
	public default ExceptedPredicate<T> negate() {
		return t -> !test(t);
	}
	
	/**
	 * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. When evaluating the composed
	 * predicate, if this predicate is {@code true}, then the {@code other} predicate is not evaluated.
	 * <p>
	 * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @param other
	 *            a predicate that will be logically-ORed with this predicate
	 * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the {@code other} predicate
	 * @throws NullPointerException
	 *             if other is {@code null}
	 */
	public default ExceptedPredicate<T> or(ExceptedPredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}
	
	/**
	 * Returns a predicate that tests if two arguments are equal according to {@link Objects#equals(Object, Object)}.
	 *
	 * @param <T>
	 *            the type of arguments to the predicate
	 * @param targetRef
	 *            the object reference with which to compare for equality, which may be {@code null}
	 * @return a predicate that tests if two arguments are equal according to {@link Objects#equals(Object, Object)}
	 */
	static <T> ExceptedPredicate<T> isEqual(Object targetRef) {
		return (null == targetRef) ? Objects::isNull : object -> targetRef.equals(object);
	}
	
	/**
	 * Returns a {@link Predicate} that wraps this {@link ExceptedPredicate} and returns {@code false} if an {@link Exception} would have been thrown
	 * and optionally prints the stack trace of said {@link Exception}.
	 * 
	 * @param printStackTrace
	 *            whether to print the stack trace of {@link Exception} thrown by this function when called from within the wrapper
	 * @return a {@link Predicate} that wraps this {@link ExceptedPredicate}
	 * @see #toPredicate(boolean, boolean)
	 */
	public default Predicate<T> toPredicate(boolean printStackTrace) {
		return toPredicate(printStackTrace, false);
	}
	
	/**
	 * Returns a {@link Predicate} that wraps this {@link ExceptedPredicate} and returns {@code exceptionReturn} if an {@link Exception} would have
	 * been thrown and optionally prints the stack trace of said {@link Exception}.
	 * 
	 * @param printStackTrace
	 *            whether to print the stack trace of {@link Exception} thrown by this function when called from within the wrapper
	 * @param exceptionReturn
	 *            the value to return when an {@link Exception} is thrown
	 * @return a {@link Predicate} that wraps this {@link ExceptedPredicate}
	 * @see #toPredicate(boolean)
	 */
	public default Predicate<T> toPredicate(boolean printStackTrace, boolean exceptionReturn) {
		if (printStackTrace)
			return t -> {
				try {
					return this.test(t);
				}
				catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			};
		else
			return t -> {
				try {
					return this.test(t);
				}
				catch (Exception e) {
					return false;
				}
			};
	}
}
