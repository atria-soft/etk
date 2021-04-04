package org.atriasoft.etk.math;

/**
 *Internal data
 *  sx  shx  tx
 *  sy  shy  ty
 */
@SuppressWarnings("preview")
public record Matrix2x3f(
		float sx,
		float shy,
		float shx,
		float sy,
		float tx,
		float ty) {
	
	/**
	 *Configuration ructor.
	 * @param sx Scale threw X axis
	 * @param shy Rotate in radian threw Y axis
	 * @param shx Rotate in radian threw X axis
	 * @param sy Scale threw Y axis
	 * @param tx Translate threw X axis
	 * @param ty translate threw Y axis
	 */
	public Matrix2x3f(final float sx, final float shy, final float shx, final float sy, final float tx, final float ty) {
		this.sx = sx;
		this.shy = shy;
		this.shx = shx;
		this.sy = sy;
		this.tx = tx;
		this.ty = ty;
	}
	
	/**
	 *Configuration ructor.
	 * @param values vector of values in float
	 */
	public Matrix2x3f(final float[] values) {
		this(values[0], values[1], values[2], values[3], values[4], values[5]);
	}
	
	/**
	 *Configuration ructor.
	 * @param values vector of values in double
	 */
	public Matrix2x3f(final double[] values) {
		this((float) values[0], (float) values[1], (float) values[2], (float) values[3], (float) values[4], (float) values[5]);
	}
	
	/**
	 *Load Identity matrix
	 */
	public static final Matrix2x3f IDENTITY = new Matrix2x3f(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
	
	/**
	 *Operator+= Addition an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector additionned
	 */
	public Matrix2x3f add(final Matrix2x3f obj) {
		return new Matrix2x3f(this.sx + obj.sx, this.shy + obj.shy, this.shx + obj.shx, this.sy + obj.sy, this.tx + obj.tx, this.ty + obj.ty);
	}
	
	/**
	 *Operator-= Decrement an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector decremented
	 */
	public Matrix2x3f less(final Matrix2x3f obj) {
		return new Matrix2x3f(this.sx - obj.sx, this.shy - obj.shy, this.shx - obj.shx, this.sy - obj.sy, this.tx - obj.tx, this.ty - obj.ty);
	}
	
	/**
	 *Operator*= Multiplication an other matrix with this one
	 * @param obj Reference on the external object
	 * @return Local reference of the vector multiplicated
	 */
	public Matrix2x3f multiply(final Matrix2x3f obj) {
		float sx = this.sx * obj.sx + this.shy * obj.shx;
		float shx = this.shx * obj.sx + this.sy * obj.shx;
		float tx = this.tx * obj.sx + this.ty * obj.shx + obj.tx;
		float shy = this.sx * obj.shy + this.shy * obj.sy;
		float sy = this.shx * obj.shy + this.sy * obj.sy;
		float ty = this.tx * obj.shy + this.ty * obj.sy + obj.ty;
		return new Matrix2x3f(sx, shy, shx, sy, tx, ty);
	}
	
	/**
	 *Operator* apply matrix on a vector
	 * @param point Point value to apply the matrix
	 * @return New vector containing the value
	 */
	public Vector2f multiply(final Vector2f point) {
		return new Vector2f(point.x() * this.sx + point.y() * this.shx + this.tx, point.x() * this.shy + point.y() * this.sy + this.ty);
	}
	
	/**
	 *Apply matrix on a vector Scale Rotate, but NOT the translation
	 * @param point Point value to apply the matrix
	 * @return New vector containing the value
	 */
	public Vector2f applyScaleRotation(final Vector2f point) {
		return new Vector2f(point.x() * this.sx + point.y() * this.shx, point.x() * this.shy + point.y() * this.sy);
	}
	
	/**
	 *Flip the mathix threw the X axis
	 */
	public Matrix2x3f flipX() {
		return new Matrix2x3f(-this.sx, this.shy, -this.shx, this.sy, -this.tx, this.ty);
	}
	
	/**
	 *Flip the mathix threw the Y axis
	 */
	public Matrix2x3f flipY() {
		return new Matrix2x3f(this.sx, -this.shy, this.shx, -this.sy, this.tx, -this.ty);
	}
	
	/**
	 *Scale the current Matrix.
	 * @param vect Vector to scale matrix.
	 */
	public Matrix2x3f scale(final Vector2f vect) {
		return new Matrix2x3f(this.sx * vect.x(), this.shy * vect.y(), this.shx * vect.x(), this.sy * vect.y(), this.tx * vect.x(), this.ty * vect.y());
	}
	
	/**
	 *Scale the current Matrix.
	 * @param value Single value to scale in X andf Y.
	 */
	public Matrix2x3f scale(final float value) {
		return new Matrix2x3f(this.sx * value, this.shy * value, this.shx * value, this.sy * value, this.tx * value, this.ty * value);
	}
	
	/**
	 *Makes a rotation matrix.
	 * @param angleRad angle to apply.
	 */
	public Matrix2x3f rotate(final float angleRad) {
		float ca = FMath.cos(angleRad);
		float sa = FMath.sin(angleRad);
		float sx = this.sx * ca - this.shy * sa;
		float shx = this.shx * ca - this.sy * sa;
		float tx = this.tx * ca - this.ty * sa;
		float shy = this.sx * sa + this.shy * ca;
		float sy = this.shx * sa + this.sy * ca;
		float ty = this.tx * sa + this.ty * ca;
		return new Matrix2x3f(sx, shy, shx, sy, tx, ty);
	}
	
	/**
	 *Makes a translation of the matrix
	 * @param vect Translation to apply.
	 */
	public Matrix2x3f translate(final Vector2f vect) {
		return new Matrix2x3f(this.sx, this.shy, this.shx, this.sy, this.tx + vect.x(), this.ty + vect.y());
	}
	
	/**
	 *Computes the determinant of the matrix.
	 * @return The determinent Value.
	 */
	public float determinant() {
		return this.sx * this.sy - this.shy * this.shx;
	}
	
	/**
	 *Inverts the matrix.
	 * @note The determinant must be != 0, otherwithe the matrix can't be inverted.
	 * @return The inverted matrix.
	 */
	public Matrix2x3f invert() {
		double det = 1.0 / determinant();
		float sx = (float) (this.sy * det);
		float sy = (float) (this.sx * det);
		float shy = (float) (-this.shy * det);
		float shx = (float) (-this.shx * det);
		float tx = -this.tx * sx - this.ty * this.shx;
		float ty = -this.tx * this.shy - this.ty * this.sy;
		return new Matrix2x3f(sx, shy, shx, sy, tx, ty);
	}
	
	/**
	 * Create a matrix 2D with a simple rotation
	 * @param angleRad Radian angle to set at the matrix
	 * @return New matrix of the transformation requested
	 */
	public static Matrix2x3f createRotate(final float angleRad) {
		return new Matrix2x3f(FMath.cos(angleRad), FMath.sin(angleRad), -FMath.sin(angleRad), FMath.cos(angleRad), 0.0f, 0.0f);
	};
	
	/**
	 * Create a matrix 2D with a simple scale
	 * @param scale 2 dimention scale
	 * @return New matrix of the transformation requested
	 */
	public static Matrix2x3f createScale(final Vector2f scale) {
		return new Matrix2x3f(scale.x(), 0.0f, 0.0f, scale.y(), 0.0f, 0.0f);
	};
	
	/**
	 *Create a matrix 2D with a simple scale
	 * @param scale same scale in 2 and Y
	 * @return New matrix of the transformation requested
	 */
	public static Matrix2x3f createScale(final float scale) {
		return new Matrix2x3f(scale, 0.0f, 0.0f, scale, 0.0f, 0.0f);
	};
	
	/**
	 *Create a matrix 2D with a simple translation
	 * @param translate 2 dimention translation
	 * @return New matrix of the transformation requested
	 */
	public static Matrix2x3f createTranslate(final Vector2f translate) {
		return new Matrix2x3f(1.0f, 0.0f, 0.0f, 1.0f, translate.x(), translate.y());
	};
	
	/**
	 *Create a matrix 2D with a simple skew
	 * @param skew 2 dimention skew
	 * @return New matrix of the transformation requested
	 */
	public static Matrix2x3f createSkew(final Vector2f skew) {
		return new Matrix2x3f(1.0f, FMath.tan(skew.y()), FMath.tan(skew.x()), 1.0f, 0.0f, 0.0f);
	};
	
}
