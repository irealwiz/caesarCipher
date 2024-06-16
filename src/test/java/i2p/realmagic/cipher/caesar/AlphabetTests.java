/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License version 3 along with this code. If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 Sergey Shpagin
 */

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
 * Случайный алфавит. Символ, предшествующий первому. Ожидания: false.
 * Случайный алфавит. Первый символ. Ожидания: true.
 * Случайный алфавит. Последний символ. Ожидания: true.
 * Случайный алфавит. Символ, следующий за последним. Ожидания: false.
 */

/*
 Тесты метода of(char, char):
 * Случайный алфавит. Ожидания: первый символ соответствует заданному.
 * Первый и последний символы совпадают. Ожидания: размер алфавита равен 1.
 * Первый символ расположен раньше последнего. Ожидания: верный размер.
 * Первый символ расположен позже последнего. Ожидания: верный размер.
 * Первый символ следует непосредственно за последним. Ожидания: максимальный размер.
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

	/**
	 * Случайный алфавит. Символ, предшествующий первому. Ожидания: false.
	 */
	@Test
	public void isValidChar_chPrecedesAlpha_returnsFalse (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final int size = rng.nextInt(1, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) (alpha - 1);
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertFalse(isValidChar, "Случайный алфавит. Символ, предшествующий первому. Ожидания: false.");
	} // isValidChar_chPrecedesAlpha_returnsFalse()

	/**
	 * Случайный алфавит. Первый символ. Ожидания: true.
	 */
	@Test
	public void isValidChar_alpha_returnsTrue (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final int size = rng.nextInt(1, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = alpha;
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertTrue(isValidChar, "Случайный алфавит. Первый символ. Ожидания: true.");
	} // isValidChar_alpha_returnsTrue()

	/**
	 * Случайный алфавит. Последний символ. Ожидания: true.
	 */
	@Test
	public void isValidChar_omega_returnsTrue (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final int size = rng.nextInt(2, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) (alpha + size - 1);
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertTrue(isValidChar, "Случайный алфавит. Последний символ. Ожидания: true.");
	} // isValidChar_omega_returnsTrue()

	/**
	 * Случайный алфавит. Символ, следующий за последним. Ожидания: false.
	 */
	@Test
	public void isValidChar_chFollowsOmega_returnFalse (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final int size = rng.nextInt(1, Alphabet.MAX_ALPHABET_SIZE);
		final Alphabet alphabet = new Alphabet(alpha, size);
		final char ch = (char) (alpha + size);
		// act
		final boolean isValidChar = alphabet.isValidChar(ch);
		// assert
		Assertions.assertFalse(isValidChar, "Случайный алфавит. Символ, следующий за последним. Ожидания: false.");
	} // isValidChar_chFollowsOmega_returnFalse()

	/**
	 * Случайный алфавит. Ожидания: первый символ соответствует заданному.
	 */
	@Test
	public void of_randomAlphabet_expectedAlpha (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final char omega = (char) rng.nextInt();
		// act
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		// assert
		Assertions.assertEquals(alpha, alphabet.alpha(), "Случайный алфавит. Ожидания: первый символ соответствует заданному.");
	} // of_randomAlphabet_expectedAlpha()

	/**
	 * Первый и последний символы совпадают. Ожидания: размер алфавита равен 1.
	 */
	@Test
	public void of_omegaEqualAlpha_sizeIs1 (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final char omega = alpha;
		final int expectedSize = 1;
		// act
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		// assert
		Assertions.assertEquals(expectedSize, alphabet.size(), "Первый и последний символы совпадают. Ожидания: размер алфавита равен 1.");
	} // of_omegaEqualAlpha_sizeIs1()

	/**
	 * Первый символ расположен раньше последнего. Ожидания: верный размер.
	 */
	@Test
	public void of_alphaPrecedesOmega_expectedSize (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2);
		final char omega = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2, Alphabet.MAX_ALPHABET_SIZE);
		final int expectedSize = omega - alpha + 1;
		// act
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		// assert
		Assertions.assertEquals(expectedSize, alphabet.size(), "Первый символ расположен раньше последнего. Ожидания: верный размер.");
	} // of_alphaPrecedesOmega_expectedSize()

	/**
	 * Первый символ расположен позже последнего. Ожидания: верный размер.
	 */
	@Test
	public void of_alphaFollowsOmega_expectedSize (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2, Alphabet.MAX_ALPHABET_SIZE);
		final char omega = (char) rng.nextInt(Alphabet.MAX_ALPHABET_SIZE / 2);
		final int expectedSize = Alphabet.MAX_ALPHABET_SIZE - (alpha - omega - 1);
		// act
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		// assert
		Assertions.assertEquals(expectedSize, alphabet.size(), "Первый символ расположен позже последнего. Ожидания: верный размер.");
	} // of_alphaFollowsOmega_expectedSize()

	/**
	 * Первый символ следует непосредственно за последним. Ожидания: максимальный размер.
	 */
	@Test
	public void of_alphaImmediatelyFollowsOmega_maxSize (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt();
		final char omega = (char) (alpha - 1);
		final int expectedSize = Alphabet.MAX_ALPHABET_SIZE;
		// act
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		// assert
		Assertions.assertEquals(expectedSize, alphabet.size(), "Первый символ следует непосредственно за последним. Ожидания: максимальный размер.");
	} // of_alphaImmediatelyFollowsOmega_maxSize (()

	// todo
} // AlphabetTests
