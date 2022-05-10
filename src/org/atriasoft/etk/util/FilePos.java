/** @file
 * @author Edouard DUPIN
 * @copyright 2021, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */
package org.atriasoft.etk.util;

/** @file
 * @author Edouard DUPIN
 * @copyright 2011, Edouard DUPIN, all right reserved
 * @license MPL v2.0 (see license file)
 */

/**
 *  Position in the file of the original data.
 */
public class FilePos {
	private int col; //!< source text colomn
	private int line; //!< source Line colomn
	
	/**
	 *  default contructor (set line and col at 0)
	 */
	public FilePos() {
		this.col = 0;
		this.line = 0;
	}
	
	/**
	 *  initialize constructor
	 * @param line Line in the file
	 * @param col Colomn in the file
	 */
	public FilePos(final int line, final int col) {
		this.col = col;
		this.line = line;
	}
	
	/**
	 *  Addition operator
	 * @param obj Addition object..
	 * @return Reference on this
	 */
	public FilePos add(final FilePos obj) {
		if (obj.line == 0) {
			this.col += obj.col;
		} else {
			this.col = obj.col;
			this.line += obj.line;
		}
		return this;
	}
	
	/**
	 *  Colomn addition operator
	 * @param col Number of colomn to add
	 * @return Reference on this
	 */
	public FilePos add(final int col) {
		this.col += col;
		return this;
	}
	
	/**
	 *  Check if the value is a new line and update internal property
	 * @param val Char value to check
	 * @return true We find a new line
	 * @return false We NOT find a new line
	 */
	public boolean check(final Character val) {
		this.col++;
		if (val == '\n') {
			newLine();
			return true;
		}
		return false;
	}
	
	/**
	 *  Reset position at 0,0
	 */
	public void clear() {
		this.col = 0;
		this.line = 0;
	}
	
	@Override
	public FilePos clone() {
		final FilePos out = new FilePos();
		out.col = this.col;
		out.line = this.line;
		return out;
	}
	
	/**
	 *  Decrement the colomn position
	 * @return Reference on this
	 */
	public FilePos decrement() {
		this.col--;
		return this;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FilePos)) {
			return false;
		}
		final FilePos other = (FilePos) obj;
		return this.col == other.col && this.line == other.line;
	}
	
	/**
	 *  Get the colomn position
	 * @return Colomn in number of utf8-char
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 *  Get the line number position
	 * @return line ID (start at 0)
	 */
	public int getLine() {
		return this.line;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + this.line + this.col;
	}
	
	/**
	 *  Increment the colomn position
	 * @return Reference on this
	 */
	public FilePos increment() {
		this.col++;
		return this;
	}
	
	/**
	 *  Find a new line & reset colomn at 0
	 */
	public void newLine() {
		this.col = 0;
		this.line++;
	}
	
	/**
	 *  Asignment operator
	 * @param obj Object to copy
	 * @return Reference on this
	 */
	public FilePos set(final FilePos obj) {
		this.col = obj.col;
		this.line = obj.line;
		return this;
	}
	
	/**
	 *  Setter of specific data
	 * @param line Line in the file
	 * @param col Colomn in the file
	 */
	public void set(final int line, final int col) {
		this.col = col;
		this.line = line;
	}
	
	@Override
	public String toString() {
		String out = "(l=";
		out += this.line;
		out += ",c=";
		out += this.col;
		out += ")";
		return out;
	}
	
}
