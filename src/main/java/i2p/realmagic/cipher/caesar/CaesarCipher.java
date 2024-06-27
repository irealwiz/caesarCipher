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
 * Шифр Цезаря.
 * <p>При использовании шифра Цезаря, в процессе шифрования, каждый символ открытого текста заменяется на другой символ циклического диапазона того же алфавита, стоящий правее на выбранное значение сдвига. Расшифрование производится схожим способом: каждый символ шифротекста заменяется на другой символ циклического диапазона того же алфавита, стоящий левее на выбранное значение сдвига.</p>
 * <p>Таким образом, можно воспринимать выбранное значение сдвига, в качестве своеобразного ключа, тогда пространство таких ключей ограничивается размером выбранного алфавита.</p>
 * @param alphabet Алфавит.
 * @param rot Сдвиг.
 */
public record CaesarCipher (
	Alphabet alphabet,
	int rot
) { // record body

// constructors

	/**
	 * Конструктор шифра Цезаря.
	 * <p>Поскольку количество возможных сдвигов, определяется размером выбранного алфавита, а сдвиги осуществляются в циклическом диапазоне алфавита, то значение сдвига должно быть неотрицательным целым числом, меньшим размера алфавита.</p>
	 * @param alphabet Алфавит.
	 * @param rot Сдвиг.
	 * @throws NullPointerException Если указанный алфавит не существует.
	 * @throws IllegalArgumentException Если не выполнено любое из следующих условий:<ul>
	 *     <li>Значение сдвига неотрицательно.</li>
	 *     <li>Значение сдвига меньше размера алфавита.</li>
	 * </ul>
	 */
	public CaesarCipher {
		if ((rot >= alphabet.size()) || (rot < 0)) throw new IllegalArgumentException();
	} // CaesarCipher()

// instance methods

	/**
	 * Шифрование символа.
	 * <p>Метод зашифровывает шифром Цезаря указанный символ и возвращает результат шифрования. Зашифрованный символ символ принадлежит выбранному алфавиту.</p>
	 * @param ch Шифруемый символ.
	 * @return Зашифрованный символ.
	 * @throws IllegalArgumentException Если указанный символ не входит в выбранный алфавит.
	 */
	public char encrypt (
		final char ch
	) throws IllegalArgumentException
	{ // method body
		if (!alphabet.isValidChar(ch)) throw new IllegalArgumentException();
		final char alpha = alphabet.alpha();
		final int plainChIdx = (ch - alpha) & 0xffff;
		final int cryptChIdx = (plainChIdx + rot) % alphabet.size();
		return (char) (alpha + cryptChIdx);
	} // encrypt()

	/**
	 * Расшифровывание символа.
	 * <p>Метод расшифровывает зашифрованный шифром Цезаря символ и возвращает результат расшифровки. Расшифрованный символ символ принадлежит выбранному алфавиту.</p>
	 * @param ch Зашифрованный символ.
	 * @return Расшифрованный символ.
	 * @throws IllegalArgumentException Если указанный символ не входит в выбранный алфавит.
	 */
	public char decrypt (
		final char ch
	) throws IllegalArgumentException
	{ // method body
		if (!alphabet.isValidChar(ch)) throw new IllegalArgumentException();
		final char alpha = alphabet.alpha();
		final int size = alphabet.size();
		final int cryptChIdx = (ch - alpha) & 0xffff;
		final int plainChIdx = (cryptChIdx + size - rot) % size;
		return (char) (alpha + plainChIdx);
	} // decrypt()

	// todo
} // CaesarCipher
