package zoranodensha.api.structures.tracks;

/**
 * This class has been adapted from
 * http://math.hws.edu/javanotes/source/chapter8/Expr.java (Accessed on 2015/08/30 15:30)
 * 
 * Zora no Densha does not take any credit for the content of this file; All credit belongs to the rightful creator and author.
 * Class and variable names may have been changed, annotations may have been altered or removed.
 * 
 * @author David J. Eck., ©1996-2015
 */
public final class Expression {

	/**
	 * Construct an expression, given its definition as a String.
	 * This will throw an IllegalArgumentException if the String does not contain a legal expression.
	 */
	public Expression(String definition) {

		parse(definition);
	}

	/**
	 * Computes the value of this expression, when the variable x has a specified value.
	 * If the expression is undefined for the specified value of x, then Double.NaN is returned.
	 * 
	 * @param x - The value to be used for the variable x in the expression
	 * @return The computed value of the expression
	 */
	public double value(double x) {

		return eval(x);
	}

	/**
	 * Return the original definition string of this expression.
	 * This is the same string that was provided in the constructor.
	 */
	public String toString() {

		return definition;
	}

	/** The original definition of the expression. */
	private String definition;
	/** A translated version of the expression, containing stack operations that compute the value of the expression. */
	private byte[] code;
	/** A stack to be used during the evaluation of the expression. */
	private double[] stack;
	/** An array containing all the constants found in the expression. */
	private double[] constants;

	/** Values for the code array; Values greater and equal to zero are indices in the constants array. */
	private static final byte
	PLUS = -1,
	MINUS = -2,
	TIMES = -3,
	DIVIDE = -4,
	POWER = -5,
	SIN = -6,
	COS = -7,
	TAN = -8,
	COT = -9,
	SEC = -10,
	CSC = -11,
	ARCSIN = -12,
	ARCCOS = -13,
	ARCTAN = -14,
	EXP = -15,
	LN = -16,
	LOG10 = -17,
	LOG2 = -18,
	ABS = -19,
	SQRT = -20,
	UNARYMINUS = -21,
	VARIABLE = -22;

	/** Names of standard functions, used during parsing */
	private static String[] functionNames = {
		"sin", "cos", "tan", "cot", "sec",
		"csc", "arcsin", "arccos", "arctan", "exp",
		"ln", "log10", "log2", "abs", "sqrt"};

	/**
	 * Evaluate this expression for the variable's value.
	 */
	private double eval(double variable) {

		try {

			int top = 0;

			for (int i = 0; i < this.codeSize; i++) {

				if (this.code[i] >= 0) {

					this.stack[top++] = this.constants[code[i]];
				}
				else if (this.code[i] >= POWER) {

					double y = this.stack[--top];
					double x = this.stack[--top];
					double ans = Double.NaN;

					switch (code[i]) {

					case PLUS:
						ans = x + y;
						break;

					case MINUS:
						ans = x - y;
						break;

					case TIMES:
						ans = x * y;
						break;

					case DIVIDE:
						ans = x / y;
						break;

					case POWER:
						ans = Math.pow(x,y);
						break;
					}

					if (Double.isNaN(ans)) {

						return ans;
					}

					this.stack[top++] = ans;
				}
				else if (this.code[i] == VARIABLE) {

					this.stack[top++] = variable;
				}
				else {

					double x = this.stack[--top];
					double ans = Double.NaN;

					switch (this.code[i]) {

					case SIN:
						ans = Math.sin(x);
						break;

					case COS:
						ans = Math.cos(x);
						break;

					case TAN:
						ans = Math.tan(x);
						break;

					case COT:
						ans = Math.cos(x) / Math.sin(x);
						break;

					case SEC:
						ans = 1.0D / Math.cos(x);
						break;

					case CSC:
						ans = 1.0D / Math.sin(x);
						break;

					case ARCSIN:
						if (Math.abs(x) <= 1.0D) {

							ans = Math.asin(x);
						}
						break;

					case ARCCOS:
						if (Math.abs(x) <= 1.0D) {

							ans = Math.acos(x);
						}
						break;

					case ARCTAN:
						ans = Math.atan(x);
						break;

					case EXP:
						ans = Math.exp(x);
						break;

					case LN:
						if (x > 0.0D) {

							ans = Math.log(x);
						}
						break;

					case LOG2:
						if (x > 0.0D) {

							ans = Math.log(x) / Math.log(2);
						}
						break;

					case LOG10:
						if (x > 0.0D) {

							ans = Math.log(x) / Math.log(10);
						}
						break;

					case ABS:
						ans = Math.abs(x);
						break;

					case SQRT:
						if (x >= 0.0D) {

							ans = Math.sqrt(x);
						}
						break;

					case UNARYMINUS:
						ans = -x;
						break;
					}

					if (Double.isNaN(ans)) {

						return ans;
					}

					this.stack[top++] = ans;
				}
			}
		}
		catch (Exception e) {

			return Double.NaN;
		}
		if (Double.isInfinite(stack[0])) {

			return Double.NaN;
		}
		else {

			return stack[0];               
		}
	}      

	/** Data to use during parsing */
	private int
	pos = 0,
	constantCt = 0,
	codeSize = 0;

	/**
	 * Called when an error occurs during parsing.
	 */
	private void error(String message) {

		throw new IllegalArgumentException("Parse error:  " + message + "  (@Position: " + pos + ")");
	}

	/**
	 * Call after code[] is computed
	 */
	private int computeStackUsage() {

		/* Stack size after each operation */
		int s = 0;
		/* Maximum stack size seen */
		int max = 0;

		for (int i = 0; i < this.codeSize; i++) {

			if (this.code[i] >= 0 || this.code[i] == VARIABLE) {

				s++;

				if (s > max) {

					max = s;
				}
			}
			else if (this.code[i] >= POWER) {

				s--;
			}
		}

		return max;
	}

	/**
	 * Parse the definition and produce all the data that represents the expression internally.
	 * May throw an IllegalArgumentException.
	 */
	private void parse(String definition) {

		if (definition == null || definition.trim().equals("")) {

			error("No data provided to Expr constructor");
		}

		this.definition = definition;
		this.code = new byte[definition.length()];
		this.constants = new double[definition.length()];

		parseExpression();
		skip();

		if (next() != 0) {

			error("Extra data found after the end of the expression.");
		}

		int stackSize = computeStackUsage();
		this.stack = new double[stackSize];

		byte[] c = new byte[this.codeSize];
		System.arraycopy(this.code, 0, c, 0, this.codeSize);
		this.code = c;

		double[] A = new double[this.constantCt];
		System.arraycopy(this.constants, 0, A, 0, this.constantCt);
		this.constants = A;
	}

	/**
	 * Return next char in data or 0 if data is all used up
	 */
	private char next() {

		if (this.pos >= this.definition.length()) {

			return 0;
		}
		else {

			return definition.charAt(this.pos);
		}
	}       

	private void skip() {

		while(Character.isWhitespace(next())) {

			this.pos++;
		}
	}

	/**
	 * Remaining routines do a standard recursive parse of the expression
	 */
	private void parseExpression() {

		boolean neg = false;

		skip();

		if (next() == '+' || next() == '-') {

			neg = (next() == '-');
			this.pos++;
			skip();
		}

		parseTerm();

		if (neg) {

			this.code[this.codeSize++] = UNARYMINUS;
		}

		skip();

		while (next() == '+' || next() == '-') {

			char op = next();
			this.pos++;
			parseTerm();
			this.code[this.codeSize++] = (op == '+') ? PLUS : MINUS;
			skip();
		}
	}

	private void parseTerm() {

		parseFactor();
		skip();

		while (next() == '*' || next() == '/') {

			char op = next();
			this.pos++;
			parseFactor();
			this.code[this.codeSize++] = (op == '*') ? TIMES : DIVIDE;
			skip();
		}
	}

	private void parseFactor() {

		parsePrimary();
		skip();

		while (next() == '^') {

			this.pos++;
			parsePrimary();
			this.code[this.codeSize++] = POWER;
			skip();
		}
	}

	private void parsePrimary() {

		skip();
		char ch = next();

		if (ch == 'x' || ch == 'X') {

			this.pos++;
			this.code[codeSize++] = VARIABLE;
		}
		else if (Character.isLetter(ch)) {

			parseWord();
		}
		else if (Character.isDigit(ch) || ch == '.') {

			parseNumber();
		}
		else if (ch == '(') {

			this.pos++;
			parseExpression();
			skip();

			if (next() != ')') {

				error("Expected a right parenthesis.");
			}

			this.pos++;
		}
		else if (ch == ')') {

			error("Unmatched right parenthesis.");
		}
		else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {

			error("Operator '" + ch + "' found in an unexpected position.");
		}
		else if (ch == 0) {

			error("Unexpected end of data in the middle of an expression.");
		}
		else {

			error("Illegal character '" + ch + "' found in data.");
		}
	}

	private void parseWord() {

		String w = "";

		while (Character.isLetterOrDigit(next())) {

			w += next();
			this.pos++;
		}

		w = w.toLowerCase();

		for (int i = 0; i < functionNames.length; i++) {

			if (w.equals(functionNames[i])) {

				skip();

				if (next() != '(') {

					error("Function name '" + w + "' must be followed by its parameter in parentheses.");
				}

				this.pos++;

				parseExpression();
				skip();

				if (next() != ')') {

					error("Missing right parenthesis after parameter of function '" + w + "'.");
				}

				this.pos++;
				this.code[this.codeSize++] = (byte) (SIN - i);
				return;
			}
		}

		error("Unknown word '" + w + "' found in data.");
	}

	private void parseNumber() {

		String w = "";

		while (Character.isDigit(next())) {

			w += next();
			this.pos++;
		}

		if (next() == '.') {

			w += next();
			this.pos++;

			while (Character.isDigit(next())) {
				w += next();
				this.pos++;
			}          
		}

		if (w.equals(".")) {

			error("Illegal number found, consisting of decimal point only.");
		}

		if (next() == 'E' || next() == 'e') {

			w += next();
			this.pos++;

			if (next() == '+' || next() == '-') {
				w += next();
				this.pos++;
			}

			if (! Character.isDigit(next())) {

				error("Illegal number found, with no digits in its exponent.");
			}

			while (Character.isDigit(next())) {

				w += next();
				this.pos++;
			}
		}

		double d = Double.NaN;

		try {

			d = Double.valueOf(w).doubleValue();
		}
		catch (Exception e) {}

		if (Double.isNaN(d)) {

			error("Illegal number '" + w + "' found in data.");
		}

		this.code[this.codeSize++] = (byte) this.constantCt;
		this.constants[this.constantCt++] = d;
	}
}