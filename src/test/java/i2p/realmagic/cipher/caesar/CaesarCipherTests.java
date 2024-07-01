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

/*
 Тесты метода encrypt(char):
 * Случайный алфавит. Символ вне алфавита. Ожидания: выброшено IAE.
 * Случайный алфавит. Символ из алфавита. Ожидания: исключений не выброшено.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 8. Символ \uFFFD. Ожидания: возвращён символ \u0005.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 68. Символ \uFFFD. Ожидания: возвращён символ \uFFC1.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 9. Символ \u003C. Ожидания: возвращён символ \uFFC5.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 123. Символ \u003C. Ожидания: возвращён символ \u0037.
 * Случайный алфавит. Сдвиг 0. Случайный символ. Ожидания: возвращён тот же символ.
 * Алфавит a-z. Сдвиг 25. Случайный символ, кроме a. Ожидания: возвращён предшествующий указанному символ.
 * Алфавит a-z. Сдвиг 11. Символ из a-o. Ожидания: возвращён соответствующий символ из l-z.
 * Алфавит a-z. Сдвиг 13. Символ из n-z. Ожидания: возвращён соответствующий символ из a-m.
 */

/*
 Тесты метода decrypt(char):
 * Случайный алфавит. Символ вне алфавита. Ожидания: выброшено IAE.
 * Случайный алфавит. Символ из алфавита. Ожидания: исключений не выброшено.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 8. Символ \u0005. Ожидания: возвращён символ \uFFFD.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 68. Символ \uFFC1. Ожидания: возвращён символ \uFFFD.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 9. Символ \uFFC5. Ожидания: возвращён символ \u003C.
 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 123. Символ \u0037. Ожидания: возвращён символ \u003C.
 * Случайный алфавит. Сдвиг 0. Случайный символ. Ожидания: возвращён тот же символ.
 * Алфавит a-z. Сдвиг 25. Случайный символ, кроме z. Ожидания: возвращён следующий за указанным символом.
 * Алфавит a-z. Сдвиг 11. Символ из l-z. Ожидания: возвращён соответствующий символ из a-o.
 * Алфавит a-z. Сдвиг 13. Символ из a-m. Ожидания: возвращён соответствующий символ из n-z.
 */

/*
 Тесты метода encrypt(String):
 * Шифруемая строка не существует. Ожидания: выброшено NPE.
 * Шифруемая строка содержит символ не входящий в алфавит. Ожидания: выброшено IAE.
 * Алфавит пересекает границу типа char. Случайная строка. Ожидания: возвращено ожидаемое значение.
 * Случайный шифр. Случайная строка. Ожидания: возвращено ожидаемое значение.
 * Шифр ROT13. Открытый текст: "sha". Шифротекст: "fun".
 * Шифр ROT13. Открытый текст: "png". Шифротекст: "cat".
 */

/*
 Тесты метода decrypt(String):
 * Шифротекст не существует. Ожидания: выброшено NPE.
 * Шифротекст содержит символ не входящий в алфавит. Ожидания: выброшено IAE.
 * Алфавит пересекает границу типа char. Случайная строка. Ожидания: возвращено ожидаемое значение.
 * Случайный шифр. Случайная строка. Ожидания: возвращено ожидаемое значение.
 * Шифр ROT13. Шифротекст: "or". Открытый текст: "be".
 * Шифр ROT13. Шифротекст: "green". Открытый текст: "terra".
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

// static methods

	/**
	 * Генерация случайной строки указанной длинны, состоящей из символов алфавита.
	 * @param rng ГПСЧ
	 * @param alphabet Используемый алфавит.
	 * @param len Длина генерируемой строки.
	 * @return Случайная строка.
	 */
	private static String genRandomString (
		final RandomGenerator rng,
		final Alphabet alphabet,
		final int len
	) { // method body
		final StringBuilder str = new StringBuilder(len);
		final char alpha = alphabet.alpha();
		final int size = alphabet.size();
		for (int i = len; i > 0; i--) {
			final char ch = (char) (alpha + rng.nextInt(size));
			str.append(ch);
		} // for
		return str.toString();
	} // genRandomString()

	/**
	 * Шифрование указанной строки символ за символом.
	 * @param cipher Используемый шифр Цезаря.
	 * @param plainText Шифруемая строка.
	 * @return Зашифрованная строка.
	 */
	private static String charByCharEncryption (
		final CaesarCipher cipher,
		final String plainText
	) { // method body
		final int len = plainText.length();
		final StringBuilder cipherText = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			final char plainCh = plainText.charAt(i);
			final char cipherCh = cipher.encrypt(plainCh);
			cipherText.append(cipherCh);
		} // for
		return cipherText.toString();
	} // charByCharEncryption()

	/**
	 * Расшифровывание указанной строки символ за символом.
	 * @param cipher Используемый шифр Цезаря.
	 * @param cipherText Шифротекст.
	 * @return Открытый текс.
	 */
	private static String charByCharDecryption (
		final CaesarCipher cipher,
		final String cipherText
	) { // method body
		final int len = cipherText.length();
		final StringBuilder plainText = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			final char cipherCh = cipherText.charAt(i);
			final char plainCh = cipher.decrypt(cipherCh);
			plainText.append(plainCh);
		} // for
		return plainText.toString();
	} // charByCharDecryption()

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

	/**
	 * Случайный алфавит. Символ вне алфавита. Ожидания: выброшено IAE.
	 */
	@Test
	public void encrypt_randomAlphabet_chOutOfAlphabet_throwsIAE (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		final char ch = (char) (cipher.alphabet().alpha() - 1);
		Assertions.assertThrows(IllegalArgumentException.class, () -> cipher.encrypt(ch), "Случайный алфавит. Символ вне алфавита. Ожидания: выброшено IAE.");
	} // encrypt_randomAlphabet_chOutOfAlphabet_throwsIAE()

	/**
	 * Случайный алфавит. Символ из алфавита. Ожидания: исключений не выброшено.
	 */
	@Test
	public void encrypt_randomAlphabet_chOfAlphabet_doesNotThrow (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		final char ch = (char) (cipher.alphabet().alpha() + rng.nextInt(cipher.alphabet().size()));
		Assertions.assertDoesNotThrow(() -> cipher.encrypt(ch), "Случайный алфавит. Символ из алфавита. Ожидания: исключений не выброшено.");
	} // encrypt_randomAlphabet_chOfAlphabet_doesNotThrow()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 8. Символ \uFFFD. Ожидания: возвращён символ \u0005.
	 */
	@Test
	public void encrypt_alphabet128_rot8_chUFFFD_returnsU0005 (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 8;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\uFFFD';
		final char expected = '\u0005';
		// act
		final char real = cipher.encrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 8. Символ \\uFFFD. Ожидания: возвращён символ \\u0005.");
	} // encrypt_alphabet128_rot8_chUFFFD_returnsU0005()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 68. Символ \uFFFD. Ожидания: возвращён символ \uFFC1.
	 */
	@Test
	public void encrypt_alphabet128_rot68_chUFFFD_returnsUFFC1 (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 68;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\uFFFD';
		final char expected = '\uFFC1';
		// act
		final char real = cipher.encrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 68. Символ \\uFFFD. Ожидания: возвращён символ \\uFFC1.");
	} // encrypt_alphabet128_rot68_chUFFFD_returnsUFFC1()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 9. Символ \u003C. Ожидания: возвращён символ \uFFC5.
	 */
	@Test
	public void encrypt_alphabet128_rot9_chU003C_returnsUFFC5 (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 9;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\u003C';
		final char expected = '\uFFC5';
		// act
		final char real = cipher.encrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 9. Символ \\u003C. Ожидания: возвращён символ \\uFFC5.");
	} // encrypt_alphabet128_rot9_chU003C_returnsUFFC5()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 123. Символ \u003C. Ожидания: возвращён символ \u0037.
	 */
	@Test
	public void encrypt_alphabet128_rot123_chU003C_returnsU0037 (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 123;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\u003C';
		final char expected = '\u0037';
		// act
		final char real = cipher.encrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 123. Символ \\u003C. Ожидания: возвращён символ \\u0037.");
	} // encrypt_alphabet128_rot123_chU003C_returnsU0037()

	/**
	 * Случайный алфавит. Сдвиг 0. Случайный символ. Ожидания: возвращён тот же символ.
	 */
	@Test
	public void encrypt_randomAlphabet_zeroRot_randomCh_sameCh (
	) { // method body
		// arrange
		final int zeroRot = 0;
		final CaesarCipher cipher = new CaesarCipher(validAlphabet, zeroRot);
		final char plainCh = (char) (validAlphabet.alpha() + rng.nextInt(validAlphabet.size()));
		// act
		final char cryptCh = cipher.encrypt(plainCh);
		// assert
		Assertions.assertEquals(plainCh, cryptCh, "Случайный алфавит. Сдвиг 0. Случайный символ. Ожидания: возвращён тот же символ.");
	} // encrypt_randomAlphabet_zeroRot_randomCh_sameCh()

	/**
	 * Алфавит a-z. Сдвиг 25. Случайный символ, кроме a. Ожидания: возвращён предшествующий указанному символ.
	 */
	@Test
	public void encrypt_alphabetAZ_rot25_randomCh_precedingChar (
	) { // method body
		// arrange
		final Alphabet alphabet = Alphabet.of('a', 'z');
		final int rot = 25;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char plainCh = (char) (rng.nextInt('a', 'z') + 1);
		final char expectedCipherCh = (char) (plainCh - 1);
		// act
		final char cipherCh = cipher.encrypt(plainCh);
		// assert
		Assertions.assertEquals(expectedCipherCh, cipherCh, "Алфавит a-z. Сдвиг 25. Случайный символ, кроме a. Ожидания: возвращён предшествующий указанному символ.");
	} // encrypt_alphabetAZ_rot25_randomCh_precedingChar()

	/**
	 * Алфавит a-z. Сдвиг 11. Символ из a-o. Ожидания: возвращён соответствующий символ из l-z.
	 */
	@Test
	public void encrypt_alphabetAZ_rot11_chAO_charLZ (
	) { // method body
		// arrange
		final Alphabet alphabet = Alphabet.of('a', 'z');
		final int rot = 11;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char plainCh = (char) rng.nextInt('a', 'o' + 1);
		final char expectedCipherCh = (char) (plainCh + rot);
		// act
		final char cipherCh = cipher.encrypt(plainCh);
		// assert
		Assertions.assertEquals(expectedCipherCh, cipherCh, "Алфавит a-z. Сдвиг 11. Символ из a-o. Ожидания: возвращён соответствующий символ из l-z.");
	} // encrypt_alphabetAZ_rot11_chAO_charLZ()

	/**
	 * Алфавит a-z. Сдвиг 13. Символ из n-z. Ожидания: возвращён соответствующий символ из a-m.
	 */
	@Test
	public void encrypt_alphabetAZ_rot13_chNZ_charAM (
	) { // method body
		// arrange
		final Alphabet alphabet = Alphabet.of('a', 'z');
		final int rot = 13;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char plainCh = (char) rng.nextInt('n', 'z' + 1);
		final char expectedCipherCh = (char) (plainCh - rot);
		// act
		final char cipherCh = cipher.encrypt(plainCh);
		// assert
		Assertions.assertEquals(expectedCipherCh, cipherCh, "Алфавит a-z. Сдвиг 13. Символ из n-z. Ожидания: возвращён соответствующий символ из a-m.");
	} // encrypt_alphabetAZ_rot13_chNZ_charAM()

	/**
	 * Случайный алфавит. Символ вне алфавита. Ожидания: выброшено IAE.
	 */
	@Test
	public void decrypt_randomAlphabet_chOutOfAlphabet_throwsIAE (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		final char ch = (char) (cipher.alphabet().alpha() - 1);
		Assertions.assertThrows(IllegalArgumentException.class, () -> cipher.decrypt(ch), "Случайный алфавит. Символ вне алфавита. Ожидания: выброшено IAE.");
	} // decrypt_randomAlphabet_chOutOfAlphabet_throwsIAE()

	/**
	 * Случайный алфавит. Символ из алфавита. Ожидания: исключений не выброшено.
	 */
	@Test
	public void decrypt_randomAlphabet_chOfAlphabet_doesNotThrow (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		final char ch = (char) (cipher.alphabet().alpha() + rng.nextInt(cipher.alphabet().size()));
		Assertions.assertDoesNotThrow(() -> cipher.decrypt(ch), "Случайный алфавит. Символ из алфавита. Ожидания: исключений не выброшено.");
	} // decrypt_randomAlphabet_chOfAlphabet_doesNotThrow()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 8. Символ \u0005. Ожидания: возвращён символ \uFFFD.
	 */
	@Test
	public void decrypt_alphabet128_rot8_chU0005_returnsUFFFD (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 8;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\u0005';
		final char expected = '\uFFFD';
		// act
		final char real = cipher.decrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 8. Символ \\u0005. Ожидания: возвращён символ \\uFFFD.");
	} // decrypt_alphabet128_rot8_chU0005_returnsUFFFD()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 68. Символ \uFFC1. Ожидания: возвращён символ \uFFFD.
	 */
	@Test
	public void decrypt_alphabet128_rot68_chUFFC1_returnsUFFFD (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 68;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\uFFC1';
		final char expected = '\uFFFD';
		// act
		final char real = cipher.decrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 68. Символ \\uFFC1. Ожидания: возвращён символ \\uFFFD.");
	} // decrypt_alphabet128_rot68_chUFFC1_returnsUFFFD()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 9. Символ \uFFC5. Ожидания: возвращён символ \u003C.
	 */
	@Test
	public void decrypt_alphabet128_rot9_chUFFC5_returnsU003C (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 9;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\uFFC5';
		final char expected = '\u003C';
		// act
		final char real = cipher.decrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 9. Символ \\uFFC5. Ожидания: возвращён символ \\u003C.");
	} // decrypt_alphabet128_rot9_chUFFC5_returnsU003C()

	/**
	 * Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 123. Символ \u0037. Ожидания: возвращён символ \u003C.
	 */
	@Test
	public void decrypt_alphabet128_rot123_chU0037_returnsU003C (
	) { // method body
		// arrange
		final Alphabet alphabet = new Alphabet('\uFFC0', 128);
		final int rot = 123;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char ch = '\u0037';
		final char expected = '\u003C';
		// act
		final char real = cipher.decrypt(ch);
		// assert
		Assertions.assertEquals(expected, real, "Алфавит в 128 символов пересекает границу типа char, в равных пропорциях. Сдвиг 123. Символ \\u0037. Ожидания: возвращён символ \\u003C.");
	} // decrypt_alphabet128_rot123_chU0037_returnsU003C()

	/**
	 * Случайный алфавит. Сдвиг 0. Случайный символ. Ожидания: возвращён тот же символ.
	 */
	@Test
	public void decrypt_randomAlphabet_zeroRot_randomCh_sameCh (
	) { // method body
		// arrange
		final int zeroRot = 0;
		final CaesarCipher cipher = new CaesarCipher(validAlphabet, zeroRot);
		final char cipherCh = (char) (validAlphabet.alpha() + rng.nextInt(validAlphabet.size()));
		// act
		final char plainCh = cipher.decrypt(cipherCh);
		// assert
		Assertions.assertEquals(cipherCh, plainCh, "Случайный алфавит. Сдвиг 0. Случайный символ. Ожидания: возвращён тот же символ.");
	} // decrypt_randomAlphabet_zeroRot_randomCh_sameCh()

	/**
	 * Алфавит a-z. Сдвиг 25. Случайный символ, кроме z. Ожидания: возвращён следующий за указанным символом.
	 */
	@Test
	public void decrypt_alphabetAZ_rot25_randomCh_followingChar (
	) { // method body
		// arrange
		final Alphabet alphabet = Alphabet.of('a', 'z');
		final int rot = 25;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char cipherCh = (char) rng.nextInt('a', 'z');
		final char expectedPlainCh = (char) (cipherCh + 1);
		// act
		final char plainCh = cipher.decrypt(cipherCh);
		// assert
		Assertions.assertEquals(expectedPlainCh, plainCh, "Алфавит a-z. Сдвиг 25. Случайный символ, кроме z. Ожидания: возвращён следующий за указанным символом.");
	} // decrypt_alphabetAZ_rot25_randomCh_followingChar()

	/**
	 * Алфавит a-z. Сдвиг 11. Символ из l-z. Ожидания: возвращён соответствующий символ из a-o.
	 */
	@Test
	public void decrypt_alphabetAZ_rot11_chLZ_charAO (
	) { // method body
		// arrange
		final Alphabet alphabet = Alphabet.of('a', 'z');
		final int rot = 11;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char cipherCh = (char) rng.nextInt('l', 'z' + 1);
		final char expectedPlainCh = (char) (cipherCh - rot);
		// act
		final char plainCh = cipher.decrypt(cipherCh);
		// assert
		Assertions.assertEquals(expectedPlainCh, plainCh, "Алфавит a-z. Сдвиг 11. Символ из l-z. Ожидания: возвращён соответствующий символ из a-o.");
	} // decrypt_alphabetAZ_rot11_chLZ_charAO()

	/**
	 * Алфавит a-z. Сдвиг 13. Символ из a-m. Ожидания: возвращён соответствующий символ из n-z.
	 */
	@Test
	public void decrypt_alphabetAZ_rot13_chAM_charNZ (
	) { // method body
		// arrange
		final Alphabet alphabet = Alphabet.of('a', 'z');
		final int rot = 13;
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final char cipherCh = (char) rng.nextInt('a', 'm' + 1);
		final char expectedPlainCh = (char) (cipherCh + rot);
		// act
		final char plainCh = cipher.decrypt(cipherCh);
		// assert
		Assertions.assertEquals(expectedPlainCh, plainCh, "Алфавит a-z. Сдвиг 13. Символ из a-m. Ожидания: возвращён соответствующий символ из n-z.");
	} // decrypt_alphabetAZ_rot13_chAM_charNZ()

	/**
	 * Шифруемая строка не существует. Ожидания: выброшено NPE.
	 */
	@Test
	public void encrypt_nullPlainText_throwsNPE (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		Assertions.assertThrows(NullPointerException.class, () -> cipher.encrypt(null), "Шифруемая строка не существует. Ожидания: выброшено NPE.");
	} // encrypt_nullPlainText_throwsNPE()

	/**
	 * Шифруемая строка содержит символ не входящий в алфавит. Ожидания: выброшено IAE.
	 */
	@Test
	public void encrypt_plainTextContainsOutOfAlphabetCharacter_throwsIAE (
	) { // method body
		// arrange
		final CaesarCipher cipher = cipherSupplier.get();
		final char invalidChar = (char) (cipher.alphabet().alpha() - 1);
		final StringBuilder plainText = new StringBuilder(genRandomString(rng, cipher.alphabet(), 128));
		plainText.setCharAt(rng.nextInt(plainText.length()), invalidChar);
		// assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> cipher.encrypt(plainText.toString()), "Шифруемая строка содержит символ не входящий в алфавит. Ожидания: выброшено IAE.");
	} // encrypt_plainTextContainsOutOfAlphabetCharacter_throwsIAE()

	/**
	 * Алфавит пересекает границу типа char. Случайная строка. Ожидания: возвращено ожидаемое значение.
	 */
	@Test
	public void encrypt_alphabetCrossesCharBound_randomString_validCipherText (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(-64, 0);
		final char omega = (char) rng.nextInt(64);
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		final int rot = rng.nextInt(alphabet.size());
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final String plainText = genRandomString(rng, alphabet, 2048);
		final String expectedCipherText = charByCharEncryption(cipher, plainText);
		// act
		final String cipherText = cipher.encrypt(plainText);
		// assert
		Assertions.assertEquals(expectedCipherText, cipherText, "Алфавит пересекает границу типа char. Случайная строка. Ожидания: возвращено ожидаемое значение.");
	} // encrypt_alphabetCrossesCharBound_randomString_validCipherText()

	/**
	 * Случайный шифр. Случайная строка. Ожидания: возвращено ожидаемое значение.
	 */
	@Test
	public void encrypt_randomCipher_randomPlainText_validCipherText (
	) { // method body
		// arrange
		final CaesarCipher cipher = cipherSupplier.get();
		final String plainText = genRandomString(rng, cipher.alphabet(), 2048);
		final String expectedCipherText = charByCharEncryption(cipher, plainText);
		// act
		final String cipherText = cipher.encrypt(plainText);
		// assert
		Assertions.assertEquals(expectedCipherText, cipherText, "Случайный шифр. Случайная строка. Ожидания: возвращено ожидаемое значение.");
	} // encrypt_randomCipher_randomPlainText_validCipherText()

	/**
	 * Шифр ROT13. Открытый текст: &quot;sha&quot;. Шифротекст: &quot;fun&quot;.
	 */
	@Test
	public void encrypt_rot13_plainTextSha_cipherTextFun (
	) { // method body
		// arrange
		final CaesarCipher cipher = CaesarCipher.ROT13;
		final String plainText = "sha";
		final String expectedCipherText = "fun";
		// act
		final String cipherText = cipher.encrypt(plainText);
		// assert
		Assertions.assertEquals(expectedCipherText, cipherText, "Шифр ROT13. Открытый текст: \"sha\". Шифротекст: \"fun\".");
	} // encrypt_rot13_plainTextSha_cipherTextFun()

	/**
	 * Шифр ROT13. Открытый текст: &quot;png&quot;. Шифротекст: &quot;cat&quot;.
	 */
	@Test
	public void encrypt_rot13_plainTextPng_cipherTextCat (
	) { // method body
		// arrange
		final CaesarCipher cipher = CaesarCipher.ROT13;
		final String plainText = "png";
		final String expectedCipherText = "cat";
		// act
		final String cipherText = cipher.encrypt(plainText);
		// assert
		Assertions.assertEquals(expectedCipherText, cipherText, "Шифр ROT13. Открытый текст: \"png\". Шифротекст: \"cat\".");
	} // encrypt_rot13_plainTextPng_cipherTextCat()

	/**
	 * Шифротекст не существует. Ожидания: выброшено NPE.
	 */
	@Test
	public void decrypt_nullCipherText_throwsNPE (
	) { // method body
		final CaesarCipher cipher = cipherSupplier.get();
		Assertions.assertThrows(NullPointerException.class, () -> cipher.decrypt(null), "Шифротекст не существует. Ожидания: выброшено NPE.");
	} // decrypt_nullCipherText_throwsNPE()

	/**
	 * Шифротекст содержит символ не входящий в алфавит. Ожидания: выброшено IAE.
	 */
	@Test
	public void decrypt_cipherTextContainsOutOfAlphabetCharacter_throwsIAE (
	) { // method body
		// arrange
		final CaesarCipher cipher = cipherSupplier.get();
		final StringBuilder cipherText = new StringBuilder(genRandomString(rng, cipher.alphabet(), 128));
		final char invalidChar = (char) (cipher.alphabet().alpha() - 1);
		cipherText.setCharAt(rng.nextInt(cipherText.length()), invalidChar);
		// assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> cipher.decrypt(cipherText.toString()), "Шифротекст содержит символ не входящий в алфавит. Ожидания: выброшено IAE.");
	} // decrypt_cipherTextContainsOutOfAlphabetCharacter_throwsIAE()

	/**
	 * Алфавит пересекает границу типа char. Случайная строка. Ожидания: возвращено ожидаемое значение.
	 */
	@Test
	public void decrypt_alphabetCrossesCharBound_randomCipherText_validPlainText (
	) { // method body
		// arrange
		final char alpha = (char) rng.nextInt(-64, 0);
		final char omega = (char) rng.nextInt(64);
		final Alphabet alphabet = Alphabet.of(alpha, omega);
		final int rot = rng.nextInt(1, alphabet.size());
		final CaesarCipher cipher = new CaesarCipher(alphabet, rot);
		final String cipherText = genRandomString(rng, alphabet, 2048);
		final String expectedPlainText = charByCharDecryption(cipher, cipherText);
		// act
		final String plainText = cipher.decrypt(cipherText);
		// assert
		Assertions.assertEquals(expectedPlainText, plainText, "Алфавит пересекает границу типа char. Случайная строка. Ожидания: возвращено ожидаемое значение.");
	} // decrypt_alphabetCrossesCharBound_randomCipherText_validPlainText()

	/**
	 * Случайный шифр. Случайная строка. Ожидания: возвращено ожидаемое значение.
	 */
	@Test
	public void decrypt_randomCipher_randomCipherText_validPlainText (
	) { // method body
		// arrange
		final CaesarCipher cipher = cipherSupplier.get();
		final String srcPlainText = genRandomString(rng, cipher.alphabet(), 2048);
		final String cipherText = cipher.encrypt(srcPlainText);
		// act
		final String plainText = cipher.decrypt(cipherText);
		// assert
		Assertions.assertEquals(srcPlainText, plainText, "Случайный шифр. Случайная строка. Ожидания: возвращено ожидаемое значение.");
	} // decrypt_randomCipher_randomCipherText_validPlainText()

	/**
	 * Шифр ROT13. Шифротекст: &quot;or&quot;. Открытый текст: &quot;be&quot;.
	 */
	@Test
	public void decrypt_rot13_cipherTextOr_plainTextBe (
	) { // method body
		// arrange
		final CaesarCipher cipher = CaesarCipher.ROT13;
		final String cipherText = "or";
		final String expectedPlainText = "be";
		// act
		final String plainText = cipher.decrypt(cipherText);
		// assert
		Assertions.assertEquals(expectedPlainText, plainText, "Шифр ROT13. Шифротекст: \"or\". Открытый текст: \"be\".");
	} // decrypt_rot13_cipherTextOr_plainTextBe()

	/**
	 * Шифр ROT13. Шифротекст: &quot;green&quot;. Открытый текст: &quot;terra&quot;.
	 */
	@Test
	public void decrypt_rot13_cipherTextGreen_plainTextTerra (
	) { // method body
		// arrange
		final CaesarCipher cipher = CaesarCipher.ROT13;
		final String cipherText = "green";
		final String expectedPlainText = "terra";
		// act
		final String plainText = cipher.decrypt(cipherText);
		// assert
		Assertions.assertEquals(expectedPlainText, plainText, "Шифр ROT13. Шифротекст: \"green\". Открытый текст: \"terra\".");
	} // decrypt_rot13_cipherTextGreen_plainTextTerra()

} // CaesarCipherTests
