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

/**
 * Алфавит текста сообщений.
 * <p>Экземпляры класса описывают алфавит текста сообщений шифра Цезаря. При использовании шифра Цезаря, алфавит сообщений шифрованного текста совпадает с алфавитом сообщений открытого текста.</p>
 * <p>Алфавит образуется подмножестовом символов указанного размера, начиная с указанного первого символа, из циклически повторяющегося множества символов типа {@code char}.</p>
 * @param alpha Первый символ алфавита.
 * @param size Размер алфавита.
 */
public record Alphabet (
	char alpha,
	int size
) { // record body

// static fields

	/**
	 * Максимально возможный размер алфавита.
	 * <p>Совокупное число символов типа {@code char}.</p>
	 */
	public static final int MAX_ALPHABET_SIZE = 0x1_0000;

// static methods

	/**
	 * Задание алфавита первым и последним символами.
	 * <p>Метод возвращает алфавит, заданный первым и последним символами. В результирующий алфавит входят все символы циклического диапазона типа {@code char}, от первого, до последнего включительно.</p>
	 * @param alpha Первый символ алфавита.
	 * @param omega Последний символ алфавита.
	 * @return Алфавит, заданный первым и последним символами.
	 */
	public static Alphabet of (
		final char alpha,
		final char omega
	) { // method body
		final int size = ((omega - alpha) & 0xffff) + 1;
		return new Alphabet(alpha, size);
	} // of()

// constructors

	/**
	 * Конструктор алфавита.
	 * <p>Размер алфавита не должен превышать совокупное число символов типа {@code char}.</p>
	 * @param alpha Первый символ алфавита.
	 * @param size Размер алфавита.
	 * @throws IllegalArgumentException Если указанный размер алфавита не является положительным числом, не превышающим общее число символов типа {@code char}.
	 */
	public Alphabet {
		if ((size <= 0) || (size > MAX_ALPHABET_SIZE)) throw new IllegalArgumentException();
	} // Alphabet()

// instance methods

	/**
	 * Проверка принадлежности символа алфавиту.
	 * <p>Метод возвращает значение {@code true}, тогда и только тогда, когда указанный символ принадлежит данному алфавиту.</p>
	 * @param ch Проверяемый символ.
	 * @return <ul>
	 *     <li>{@code true} - Если указанный символ принадлежит данному алфавиту.</li>
	 *     <li>{@code false} - В противном случае.</li>
	 * </ul>
	 */
	public boolean isValidChar (
		final char ch
	) { // method body
		return ((ch - alpha) & 0xffff) < size;
	} // isValidChar()

	/**
	 * Последний символ алфавита.
	 * <p>Метод возвращает последний символ данного алфавита.</p>
	 * @return Последний символ алфавита.
	 */
	public char omega (
	) { // method body
		return (char) (alpha + size - 1);
	} // omega()

} // Alphabet
