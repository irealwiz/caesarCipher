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

	// todo
} // Alphabet
