/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.parsing;

import java.util.Set;
import java.util.regex.Matcher;

/**
 * Lexer
 * 
 * @author Kevin Lindsey
 * @version 1.0
 * @param <T>
 *            Any type of enumeration that implements ITokenMatcher
 */
public abstract class Lexer<T extends Enum<T> & ITokenMatcher> implements ILexer<T> {
	/**
	 * The source code being scanned
	 */
	private String source;

	/**
	 * The current offset within the source. Processing to determine the next
	 * lexeme will begin at this offset
	 */
	protected int offset;

	/**
	 * The last lexeme that was matched during scanning
	 */
	protected Lexeme<T> currentLexeme;

	/**
	 * A token indicating the end-of-file
	 */
	private Lexeme<T> eof;

	@Override
	public Lexeme<T> advance() {
		Lexeme<T> eof = getEOF();
		Set<T> filteredTokens = getFilteredTokens();

		while (true) {
			Lexeme<T> result = nextLexeme();

			if (result == eof || !filteredTokens.contains(result.type)) { // $codepro.audit.disable
																			// useEquals
				currentLexeme = result;
				break;
			}
		}

		return currentLexeme;
	}

	@Override
	public Lexeme<T> getCurrentLexeme() {
		return currentLexeme;
	}

	/**
	 * getEOF
	 * 
	 * @return Lexeme<T>
	 */
	protected Lexeme<T> getEOF() {
		if (eof == null) {
			String source = getSource();
			int length = (source != null) ? source.length() : 0;

			eof = new Lexeme<T>(getEOFType(), length, 0, "");
		}

		return eof;
	}

	/**
	 * getEOFType
	 * 
	 * @return
	 */
	protected abstract T getEOFType();

	/**
	 * getFilteredTokens
	 * 
	 * @return Set<T>
	 */
	protected abstract Set<T> getFilteredTokens();

	/**
	 * getOffset
	 * 
	 * @return int
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * getSource
	 * 
	 * @return String
	 */
	public String getSource() {
		return source;
	}

	/**
	 * getTokens
	 * 
	 * @return Set<T>
	 */
	protected abstract Set<T> getTokens();

	@Override
	public boolean hasNext() {
		return offset < source.length() && !(currentLexeme == null || currentLexeme.type == getEOFType());
	}

	/**
	 * nextLexeme
	 * 
	 * @return Lexeme<T>
	 */
	protected Lexeme<T> nextLexeme() {
		Lexeme<T> result = getEOF();

		if (offset < source.length()) {
			for (T type : getTokens()) {
				Matcher matcher = type.getMatcher(source);

				if (matcher != null) {
					matcher.region(offset, source.length());

					if (matcher.lookingAt()) {
						int start = matcher.start();
						int end = matcher.end();
						int length = end - start;

						result = new Lexeme<T>(type, start, length, source.substring(start, end));
						offset = end;

						break;
					}
				}
			}
		}

		return result;
	}

	@Override
	public void setSource(String source) {
		this.source = source;
		offset = 0;
		currentLexeme = null;
		eof = null;
	}
}
