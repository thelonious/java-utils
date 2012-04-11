/**
 * 
 */
package com.kevlindev.parsing;

import com.kevlindev.utils.StringUtils;

/**
 * @author kevin
 */
public class SourceBuilder {
	private String indent = "  ";
	private String currentIndent = "";
	private StringBuilder builder = new StringBuilder();

	public void dedent() {
		int currentLength = currentIndent.length();

		if (currentLength > 0) {
			int indentLength = indent.length();

			if (currentLength >= indentLength) {
				currentIndent = currentIndent.substring(0, currentLength - indentLength);
			} else {
				currentIndent = "";
			}
		}
	}

	public void indent() {
		currentIndent += indent;
	}

	public SourceBuilder print(String message) {
		builder.append(message);
		return this;
	}

	public SourceBuilder printIndent() {
		builder.append(currentIndent);
		return this;
	}

	public SourceBuilder println() {
		builder.append(StringUtils.EOL);
		return this;
	}

	public SourceBuilder println(String message) {
		builder.append(message).append(StringUtils.EOL);
		return this;
	}

	public SourceBuilder printlnIndent() {
		builder.append(currentIndent).append(StringUtils.EOL);
		return this;
	}

	public SourceBuilder printlnWithIndent(String message) {
		builder.append(currentIndent).append(message).append(StringUtils.EOL);
		return this;
	}

	public SourceBuilder printWithIndent(String message) {
		builder.append(currentIndent).append(message);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
