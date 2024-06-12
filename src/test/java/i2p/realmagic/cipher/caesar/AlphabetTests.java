package i2p.realmagic.cipher.caesar;

/*
 Тесты конструктора алфавита:
 * Размер отрицателен. Выбрасывает IAE.
 * Размер равен нулю. Выбрасывает IAE.
 * Размер превышает максимальное число символов. Выбрасывает IAE.
 * Размер равен единице. Не выбрасывает исключений.
 * Размер равен максимальному числу символов. Не выбрасывает исключений.
 * Размер превышает единицу, но меньше максимального. Не выбрасывает исключений.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

/**
 * Тесты класса алфавита текста сообщений.
 */
public class AlphabetTests {

// nested classes

	/**
	 * Исполнитель вызовов конструктора алфавита. Написан just for fun.
	 * @param alpha Начальная буква алфавита.
	 * @param size Размер алфавита
	 */
	private static record AlphabetConstructorCaller (
		char alpha,
		int size
	) implements Executable {

	// instance methods

		/**
		 * Осуществление обращения к конструктору алфавита.
		 */
		public void execute (
		) { // method body
			new Alphabet(alpha, size);
		} // execute()

	} // AlphabetConstructorCaller

// instance fields

	/**
	 * ГПСЧ
	 */
	private final RandomGenerator rng = ThreadLocalRandom.current();

	/**
	 * Первый символ алфавита.
	 */
	private final char alpha = (char) rng.nextInt();

// instance methods

	/**
	 * Размер отрицателен. Выбрасывает IAE.
	 */
	@Test
	public void constructor_negativeSize_throwsIAE (
	) { // method body
		final int negativeSize = rng.nextInt(Integer.MIN_VALUE, 0);
		Assertions.assertThrows(IllegalArgumentException.class, new AlphabetConstructorCaller(alpha, negativeSize));
	} // constructor_negativeSize_throwsIAE()

	/**
	 * Размер равен нулю. Выбрасывает IAE.
	 */
	@Test
	public void constructor_zeroSize_throwsIAE (
	) { // method body
		final int zeroSize = 0;
		Assertions.assertThrows(IllegalArgumentException.class, new AlphabetConstructorCaller(alpha, zeroSize));
	} // constructor_zeroSize_throwsIAE()

	/**
	 * Размер превышает максимальное число символов. Выбрасывает IAE.
	 */
	@Test
	public void constructor_sizeAboveMax_throwsIAE (
	) { // method body
		final int size = rng.nextInt(0x10001, Integer.MAX_VALUE);
		Assertions.assertThrows(IllegalArgumentException.class, new AlphabetConstructorCaller(alpha, size));
	} // constructor_sizeAboveMax_throwsIAE()

	/**
	 * Размер равен единице. Не выбрасывает исключений.
	 */
	@Test
	public void constructor_sizeEqualOne_notThrow (
	) { // method body
		final int size = 1;
		Assertions.assertDoesNotThrow(new AlphabetConstructorCaller(alpha, size));
	} // constructor_sizeEqualOne_notThrow()

	/**
	 * Размер равен максимальному числу символов. Не выбрасывает исключений.
	 */
	@Test
	public void constructor_maxSize_notThrow (
	) { // method body
		final int maxSize = 0x10000;
		Assertions.assertDoesNotThrow(new AlphabetConstructorCaller(alpha, maxSize));
	} // constructor_maxSize_notThrow()

	/**
	 * Размер превышает единицу, но меньше максимального. Не выбрасывает исключений.
	 */
	@Test
	public void constructor_ordinarySize_notThrow (
	) { // method body
		// arrange
		final int minSize = 2;
		final int maxSize = 0x10000;
		final int ordinarySize = rng.nextInt(minSize, maxSize);
		// act
		final AlphabetConstructorCaller caller = new AlphabetConstructorCaller(alpha, ordinarySize);
		// assert
		Assertions.assertDoesNotThrow(caller);
	} // constructor_ordinarySize_notThrow()

	// todo
} // AlphabetTests
