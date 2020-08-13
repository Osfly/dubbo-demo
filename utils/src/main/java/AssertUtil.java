import com.coreland.framework.core.exception.IncorrectArgumentException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 常规判断断言工具类，断言方法内发生预知以外的条件时，直接抛出相应的异常信息。
 * @author youke
 *
 */
public class AssertUtil {
	/**
	 * 错误信息：参数必须含字符串“{}”。
	 */
	private static final String NOT_CONTAIN_ERROR_CODE = "参数必须含字符串“{}”。";
	
	/**
	 * 错误信息：数组内不能有null元素。
	 */
	private static final String NO_NULL_ELEMENT_ERROR_CODE = "数组内不能有null元素。";
	
	/**
	 * Assert that an object is not {@code null}.
	 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
	 * @param object the object to check
	 * @param message the exception message code to use if the assertion fails
	 * @throws IncorrectArgumentException if the object is {@code null}
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that an object is not {@code null}.
	 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
	 * @param object the object to check
	 * @throws IncorrectArgumentException if the object is {@code null}
	 */
	public static void notNull(Object object) {
		notNull(object, IncorrectArgumentException.ERROR_MESSAGE);
	}
	
	/**
	 * Assert that the given String is not empty; that is,
	 * it must not be {@code null} and not the empty String.
	 * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception message code to use if the assertion fails
	 * @see StringUtils#hasLength
	 * @throws IncorrectArgumentException if the text is empty
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that the given String is not empty; that is,
	 * it must not be {@code null} and not the empty String.
	 * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
	 * @param text the String to check
	 * @see StringUtils#hasLength
	 * @throws IncorrectArgumentException if the text is empty
	 */
	public static void hasLength(String text) {
		hasLength(text, IncorrectArgumentException.ERROR_MESSAGE);
	}

	/**
	 * Assert that the given String contains valid text content; that is, it must not
	 * be {@code null} and must contain at least one non-whitespace character.
	 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * @param text the String to check
	 * @param message the exception message code to use if the assertion fails
	 * @see StringUtils#hasText
	 * @throws IncorrectArgumentException if the text does not contain valid text content
	 */
	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that the given String contains valid text content; that is, it must not
	 * be {@code null} and must contain at least one non-whitespace character.
	 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
	 * @param text the String to check
	 * @see StringUtils#hasText
	 * @throws IncorrectArgumentException if the text does not contain valid text content
	 */
	public static void hasText(String text) {
		hasText(text, IncorrectArgumentException.ERROR_MESSAGE);
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param message the exception message to use if the assertion fails
	 * @throws IncorrectArgumentException if the text contains the substring
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throw new IncorrectArgumentException(message, substring);
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @throws IncorrectArgumentException if the text contains the substring
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring, NOT_CONTAIN_ERROR_CODE);
	}

	/**
	 * Assert that an array contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IncorrectArgumentException if the object array is {@code null} or contains no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array)) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that an array contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
	 * @param array the array to check
	 * @throws IncorrectArgumentException if the object array is {@code null} or contains no elements
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, IncorrectArgumentException.ERROR_MESSAGE);
	}

	/**
	 * Assert that an array contains no {@code null} elements.
	 * <p>Note: Does not complain if the array is empty!
	 * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IncorrectArgumentException if the object array contains a {@code null} element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IncorrectArgumentException(message);
				}
			}
		}
	}

	/**
	 * Assert that an array contains no {@code null} elements.
	 * <p>Note: Does not complain if the array is empty!
	 * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
	 * @param array the array to check
	 * @throws IncorrectArgumentException if the object array contains a {@code null} element
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array, NO_NULL_ELEMENT_ERROR_CODE);
	}

	/**
	 * Assert that a collection contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
	 * @param collection the collection to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IncorrectArgumentException if the collection is {@code null} or
	 * contains no elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that a collection contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
	 * @param collection the collection to check
	 * @throws IncorrectArgumentException if the collection is {@code null} or
	 * contains no elements
	 */
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection, IncorrectArgumentException.ERROR_MESSAGE);
	}

	/**
	 * Assert that a Map contains entries; that is, it must not be {@code null}
	 * and must contain at least one entry.
	 * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
	 * @param map the map to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IncorrectArgumentException if the map is {@code null} or contains no entries
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that a Map contains entries; that is, it must not be {@code null}
	 * and must contain at least one entry.
	 * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
	 * @param map the map to check
	 * @throws IncorrectArgumentException if the map is {@code null} or contains no entries
	 */
	public static void notEmpty(Map<?, ?> map) {
		notEmpty(map, IncorrectArgumentException.ERROR_MESSAGE);
	}
	
	/**
	 * Assert that a String ; that is, it must not be {@code null}
	 * and not empty.
	 * <pre class="code">Assert.notEmpty(str, "String must not null or empty");</pre>
	 * @param str the String to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IncorrectArgumentException if the str is {@code null} or empty
	 */
	public static void notEmpty(String str, String message) {
		if (StringUtils.isEmpty(str)) {
			throw new IncorrectArgumentException(message);
		}
	}

	/**
	 * Assert that a String ; that is, it must not be {@code null}
	 * and not empty.
	 * <pre class="code">Assert.notEmpty(str);</pre>
	 * @param str the String to check
	 * @throws IncorrectArgumentException if the str is {@code null} or empty
	 */
	public static void notEmpty(String str) {
		notEmpty(str, IncorrectArgumentException.ERROR_MESSAGE);
	}

}
