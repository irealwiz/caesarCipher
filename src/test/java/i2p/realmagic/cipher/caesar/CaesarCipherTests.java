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
 Тесты конструктора шифра Цезаря:
 * Алфавит не существует. Ожидания: выброшено NPE.
 * Сдвиг отрицателен. Ожидания: выброшено IAE.
 * Сдвиг равен нулю. Ожидания: конструктор не выбрасывает исключений.
 * Сдвиг положителен, но меньше максимального. Ожидания: конструктор не выбрасывает исключений.
 * Максимальный сдвиг. Ожидания: конструктор не выбрасывает исключений.
 * Сдвиг равен размеру алфавита. Ожидания: выброшено IAE.
 * Сдвиг превышает размер алфавита. Ожидания: выброшено IAE.
 * Случайный алфавит. Ожидания: алфавит соответствует заданному.
 * Случайный сдвиг. Ожидания: сдвиг соответствует заданному.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * Тесты класса шифра Цезаря.
 */
public class CaesarCipherTests {

// nested classes

	/**
	 * Фабрика шифров Цезаря.
	 * <p>Создано just-for-fun, и для экономии на спичках, так как предотвращает избыточное продуцирование лямбд, в тестах конструктора.</p>
	 * @param alphabet Используемый алфавит.
	 * @param rot Используемый сдвиг.
	 */
	private record CipherSupplier (
		Alphabet alphabet,
		int rot
	) implements Supplier<CaesarCipher>, Executable
	{ // record body

		/**
		 * Фабричный метод шифров Цезаря.
		 * @return Шифр Цезаря с выбранными настройками.
		 */
		@Override
		public CaesarCipher get (
		) { // method body
			return new CaesarCipher(alphabet, rot);
		} // get()

		/**
		 * Исполнитель вызова конструктора.
		 * <p>На самом деле, метод не на прямую вызывает конструктор шифра, а производит это посредством обращения к методу {@link #get()}.</p>
		 */
		@Override
		public void execute (
		) { // method body
			get();
		} // execute()

	} // CipherSupplier

// instance fields

	/**
	 * ГПСЧ
	 */
	private final RandomGenerator rng;

	/**
	 * Стоковый алфавит.
	 */
	private final Alphabet validAlphabet;

	/**
	 * Стоковый сдвиг.
	 */
	private final int validRot;

	/**
	 * Поставщик шифров со стоковыми настройками.
	 */
	private final CipherSupplier cipherSupplier;

// constructors

	/**
	 * Конструктор теста.
	 */
	public CaesarCipherTests (
	) { // method body
		rng = ThreadLocalRandom.current();
		final char alpha = (char) rng.nextInt();
		final int minSize = 3;
		final int sizeLimit = 2048;
		final int size = rng.nextInt(minSize, sizeLimit);
		validAlphabet = new Alphabet(alpha, size);
		validRot = rng.nextInt(size);
		cipherSupplier = new CipherSupplier(validAlphabet, validRot);
	} // CaesarCipherTests()

// instance methods

	/**
	 * Алфавит не существует. Ожидания: выброшено NPE.
	 */
	@Test
	public void constructor_nullAlphabet_throwsNPE (
	) { // method body
		Assertions.assertThrows(NullPointerException.class, new CipherSupplier(null, validRot), "Алфавит не существует. Ожидания: выброшено NPE.");
	} // constructor_nullAlphabet_throwsNPE()

	/**
	 * Сдвиг отрицателен. Ожидания: выброшено IAE.
	 */
	@Test
	public void constructor_negativeRot_throwsIAE (
	) { // method body
		final int negativeRot = rng.nextInt(Integer.MIN_VALUE, 0);
		Assertions.assertThrows(IllegalArgumentException.class, new CipherSupplier(validAlphabet, negativeRot), "Сдвиг отрицателен. Ожидания: выброшено IAE.");
	} // constructor_negativeRot_throwsIAE()

	/**
	 * Сдвиг равен нулю. Ожидания: конструктор не выбрасывает исключений.
	 */
	@Test
	public void constructor_zeroRot_doesNotThrow (
	) { // method body
		final int zeroRot = 0;
		Assertions.assertDoesNotThrow(new CipherSupplier(validAlphabet, zeroRot), "Сдвиг равен нулю. Ожидания: конструктор не выбрасывает исключений.");
	} // constructor_zeroRot_doesNotThrow()

	/**
	 * Сдвиг положителен, но меньше максимального. Ожидания: конструктор не выбрасывает исключений.
	 */
	@Test
	public void constructor_positiveRot_doesNotThrow (
	) { // method body
		final int rot = rng.nextInt(1, validAlphabet.size() - 1);
		Assertions.assertDoesNotThrow(new CipherSupplier(validAlphabet, rot), "Сдвиг положителен, но меньше максимального. Ожидания: конструктор не выбрасывает исключений.");
	} // constructor_positiveRot_doesNotThrow()

	/**
	 * Максимальный сдвиг. Ожидания: конструктор не выбрасывает исключений.
	 */
	@Test
	public void constructor_maxRot_doesNotThrow (
	) { // method body
		final int maxRot = validAlphabet.size() - 1;
		Assertions.assertDoesNotThrow(new CipherSupplier(validAlphabet, maxRot), "Максимальный сдвиг. Ожидания: конструктор не выбрасывает исключений.");
	} // constructor_maxRot_doesNotThrow()

	/**
	 * Сдвиг равен размеру алфавита. Ожидания: выброшено IAE.
	 */
	@Test
	public void constructor_rotEqualAlphabetSize_throwsIAE (
	) { // method body
		final int rot = validAlphabet.size();
		Assertions.assertThrows(IllegalArgumentException.class, new CipherSupplier(validAlphabet, rot), "Сдвиг равен размеру алфавита. Ожидания: выброшено IAE.");
	} // constructor_rotEquivAlphabetSize_throwsIAE()

	/**
	 * Сдвиг превышает размер алфавита. Ожидания: выброшено IAE.
	 */
	@Test
	public void constructor_rotExceedsAlphabetSize_throwsIAE (
	) { // method body
		final int rot = validAlphabet.size() + rng.nextInt(2048) + 1;
		Assertions.assertThrows(IllegalArgumentException.class, new CipherSupplier(validAlphabet, rot), "Сдвиг превышает размер алфавита. Ожидания: выброшено IAE.");
	} // constructor_rotExceedsAlphabetSize_throwsIAE()

	/**
	 * Случайный алфавит. Ожидания: алфавит соответствует заданному.
	 */
	@Test
	public void constructor_randomAlphabet_sameAlphabet (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		Assertions.assertSame(cipherSupplier.alphabet(), cipher.alphabet(), "Случайный алфавит. Ожидания: алфавит соответствует заданному.");
	} // constructor_randomAlphabet_sameAlphabet()

	/**
	 * Случайный сдвиг. Ожидания: сдвиг соответствует заданному.
	 */
	@Test
	public void constructor_randomValidRot_sameRot (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		Assertions.assertEquals(cipherSupplier.rot(), cipher.rot(), "Случайный сдвиг. Ожидания: сдвиг соответствует заданному.");
	} // constructor_randomValidRot_sameRot()

	// todo
} // CaesarCipherTests
