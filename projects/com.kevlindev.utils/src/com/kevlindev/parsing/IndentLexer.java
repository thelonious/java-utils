/**
 * 
 */
package com.kevlindev.parsing;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

/**
 * @author kevin
 */
public abstract class IndentLexer<T extends Enum<T> & ITokenMatcher> extends Lexer<T> {
	private Queue<Lexeme<T>> lexemeQueue = new ArrayDeque<Lexeme<T>>();
	private String indentText = "\t";
	private String currentIndent = "";

	@Override
	public Lexeme<T> advance() {
		Set<T> filteredTokens = getFilteredTokens();

		do {
			if (!lexemeQueue.isEmpty()) {
				currentLexeme = lexemeQueue.poll();
			} else {
				currentLexeme = advanceWithIndents();
			}
		} while (filteredTokens.contains(currentLexeme.type));

		return currentLexeme;
	}

	private Lexeme<T> advanceWithIndents() {
		Lexeme<T> candidate = nextLexeme();

		if (getIndentCheckSet().contains(candidate.type)) {
			candidate = nextLexeme();

			if (getIndentSet().contains(candidate.type)) {
				String text = (candidate.type != getEOFType()) ? candidate.text : "";
				// NOTE: This isn't checking that the whitespace actually
				// contains the correct indent character(s)
				int count = (text.length() - currentIndent.length()) / indentText.length();

				if (count != 0) {
					int start = candidate.offset;
					T type;

					if (count < 0) {
						count = -count;
						type = getDedentType();
					} else {
						type = getIndentType();
					}

					for (int i = 0; i < count; i++) {
						Lexeme<T> lexeme = new Lexeme<T>(type, start, indentText.length(), indentText);

						lexemeQueue.offer(lexeme);
					}

					currentIndent = text;

					candidate = lexemeQueue.poll();
				}
			}
		}

		return candidate;
	}

	protected abstract T getDedentType();

	protected abstract Set<T> getIndentCheckSet();

	protected abstract Set<T> getIndentSet();

	protected abstract T getIndentType();
}
