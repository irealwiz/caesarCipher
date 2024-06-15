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

/*
 Тесты метода isValidChar:
 * Символ предшествует алфавиту. Ожидания: false.
 * Символ следует за алфавитом. Ожидания: false.
 * Алфавит не пересекает границу типа char. Символ внутри алфавита. Ожидания: true.
 * Алфавит пересекает границу типа char. Символ внутри алфавита, до границы. Ожидания: true.
 * Алфавит пересекает границу типа char. Символ внутри алфавита, после границы. Ожидания: true.
 * Алфавит пересекает границу типа char. Символ вне алфавита. Ожидания: false.
 * Алфавит включает все символы типа char. Любой случайный символ. Ожидания: true.
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

	/**
	 * Конструктор устанавливает указанный начальный символ.
	 */
	@Test
	public void constructor_selectedAlphaIsSet (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final int size = rng.nextInt(128) + 1;
		// act
		final Alphabet alphabet = new Alphabet(alpha, size);
		// assert
		Assertions.assertEquals(alpha, alphabet.alpha());
	} // constructor_selectedAlphaIsSet()

	/**
	 * Конструктор устанавливает указанный размер алфавита.
	 */
	@Test
	public void constructor_selectedSizeIsSet (
	) { // method body
		// arrange
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE) + 1;
		// act
		final Alphabet alphabet = new Alphabet(alpha, size);
		// assert
		Assertions.assertEquals(size, alphabet.size());
	} // constructor_selectedSizeIsSet()

	/**
	 * Символ предшествует алфавиту. Ожидания: false.
	 */
	@Test
	public void isValidChar_chPrecedesAlphabet_returnsFalse (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(1, Alphabet.MAX_ALPHABET_SIZE);
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - alpha) + 1;
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) rng.nextInt(alpha);
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertFalse(isValidChar, "Символ предшествует алфавиту. Ожидания: false.");
	} // isValidChar_chPrecedesAlphabet_returnsFalse()

	/**
	 * Символ следует за алфавитом. Ожидания: false.
	 */
	@Test
	public void isValidChar_chFollowsAlphabet_returnsFalse (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - 1);
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - 1 - alpha) + 1;
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) rng.nextInt(alpha + size, Alphabet.MAX_ALPHABET_SIZE);
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertFalse(isValidChar, "Символ следует за алфавитом. Ожидания: false.");
	} // isValidChar_chFollowsAlphabet_returnsFalse()

	/**
	 * Алфавит не пересекает границу типа char. Символ внутри алфавита. Ожидания: true.
	 */
	@Test
	public void isValidChar_chIsInAlphabet_returnsTrue (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE);
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - alpha) + 1;
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) (alpha + rng.nextInt(size));
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertTrue(isValidChar, "Алфавит не пересекает границу типа char. Символ внутри алфавита. Ожидания: true.");
	} // isValidChar_chIsInAlphabet_returnsTrue()

	/**
	 * Алфавит пересекает границу типа char. Символ внутри алфавита, до границы. Ожидания: true.
	 */
	@Test
	public void isValidChar_chBeforeCharTypeBound_returnsTrue (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2, Alphabet.MAX_ALPHABET_SIZE);
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - alpha + 1, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) (alpha + rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - alpha));
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertTrue(isValidChar, "Алфавит пересекает границу типа char. Символ внутри алфавита, до границы. Ожидания: true.");
	} // isValidChar_chBeforeCharTypeBound_returnsTrue()

	/**
	 * Алфавит пересекает границу типа char. Символ внутри алфавита, после границы. Ожидания: true.
	 */
	@Test
	public void isValidChar_chAfterCharTypeBound_returnsTrue (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2, Alphabet.MAX_ALPHABET_SIZE);
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - alpha + 1, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) rng.nextInt(size - (Alphabet.MAX_ALPHABET_SIZE - alpha));
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertTrue(isValidChar, "Алфавит пересекает границу типа char. Символ внутри алфавита, после границы. Ожидания: true.");
	} // isValidChar_chAfterCharTypeBound_returnsTrue()

	/**
	 * Алфавит пересекает границу типа char. Символ вне алфавита. Ожидания: false.
	 */
	@Test
	public void isValidChar_chOutsideAlphabet_returnsFalse (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2, Alphabet.MAX_ALPHABET_SIZE);
		final int size = rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - alpha + 1, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) (alpha - (rng.nextInt(Alphabet.MAX_ALPHABET_SIZE - size) + 1));
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertFalse(isValidChar, "Алфавит пересекает границу типа char. Символ вне алфавита. Ожидания: false.");
	} // isValidChar_chOutsideAlphabet_returnsFalse()

	/**
	 * Алфавит включает все символы типа char. Любой случайный символ. Ожидания: true.
	 */
	@Test
	public void isValidChar_maxAlphabetSize_randomCh_returnsTrue (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final Alphabet alphabet = new Alphabet(alpha, Alphabet.MAX_ALPHABET_SIZE);
		final char ch = (char) rng.nextInt();
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertTrue(isValidChar, "Алфавит включает все символы типа char. Любой случайный символ. Ожидания: true.");
	} // isValidChar_maxAlphabetSize_randomCh_returnsTrue()

	// todo
} // AlphabetTests
