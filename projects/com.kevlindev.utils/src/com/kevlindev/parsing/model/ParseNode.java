/**
 * 
 */
package com.kevlindev.parsing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import beaver.Symbol;

import com.kevlindev.text.SourceBuilder;
import com.kevlindev.utils.StringUtils;

/**
 * @author Kevin Lindsey
 * 
 */
public abstract class ParseNode<T> extends Symbol {
	private Symbol symbol;
	private T type;
	private ParseNode<T> parent;
	private List<ParseNode<T>> children;

	/**
	 * ParseNode
	 * 
	 * @param symbol
	 */
	public ParseNode(Symbol symbol) {
		this.symbol = symbol;
		this.type = getType(symbol.getId());
	}

	/**
	 * ParseNode
	 * 
	 * @param type
	 */
	public ParseNode(T type) {
		this.symbol = new Symbol(getTypeIndex(type));
		this.type = type;
	}

	/**
	 * addChild
	 * 
	 * @param child
	 */
	public void addChild(ParseNode<T> child) {
		if (children == null) {
			children = new ArrayList<ParseNode<T>>();
		}

		children.add(child);
		child.setParent(this);
	}

	/**
	 * addChildren
	 * 
	 * @param children
	 */
	public void addChildren(List<? extends ParseNode<T>> children) {
		// HACK: adding no children will create the backing list which is used to indicate that this is not a leaf node
		// even though it has no children
		if (this.children == null) {
			this.children = new ArrayList<ParseNode<T>>();
		}

		if (children != null) {
			for (ParseNode<T> child : children) {
				addChild(child);
			}
		}
	}

	/**
	 * getChildren
	 * 
	 * @return
	 */
	public List<ParseNode<T>> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		} else {
			return children;
		}
	}

	/**
	 * getFirstChild
	 * 
	 * @return
	 */
	public ParseNode<T> getFirstChild() {
		return (children != null && !children.isEmpty()) ? children.get(0) : null;
	}

	/**
	 * getParent
	 * 
	 * @return
	 */
	public ParseNode<T> getParent() {
		return parent;
	}

	/**
	 * getSymbol
	 * 
	 * @return
	 */
	public Symbol getSymbol() {
		return symbol;
	}

	/**
	 * getText
	 * 
	 * @return
	 */
	public String getText() {
		Object value = getValue();

		return (value != null) ? value.toString() : StringUtils.EMPTY;
	}

	/**
	 * getType
	 * 
	 * @return
	 */
	public T getType() {
		return type;
	}

	/**
	 * getType
	 * 
	 * @param id
	 * @return
	 */
	protected abstract T getType(short id);

	/**
	 * getTypeIndex
	 * 
	 * @param type
	 * @return
	 */
	protected abstract short getTypeIndex(T type);

	/**
	 * getValue
	 * 
	 * @return
	 */
	public Object getValue() {
		return (symbol != null) ? symbol.value : null;
	}

	/**
	 * hasChildren
	 * 
	 * @return
	 */
	public boolean hasChildren() {
		return (children != null && !children.isEmpty());
	}

	/**
	 * setParent
	 * 
	 * @param parent
	 */
	protected void setParent(ParseNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * toString
	 */
	public String toString() {
		SourceBuilder builder = new SourceBuilder();

		toString(builder);

		return builder.toString();
	}

	/**
	 * toString
	 * 
	 * @param builder
	 */
	private void toString(SourceBuilder builder) {
		builder.printWithIndent("(").print(getType().toString());

		if (children != null) {
			int size = children.size();

			if (size > 0) {
				builder.println().indent();

				for (int i = 0; i < size; i++) {
					ParseNode<T> child = children.get(i);

					child.toString(builder);

					if (i < size - 1) {
						builder.println();
					}
				}

				builder.dedent();
			}
		} else {
			if (symbol != null && symbol.value != null) {
				builder.print(" ").print(symbol.value.toString());
			}
		}

		builder.print(")");
	}
}
