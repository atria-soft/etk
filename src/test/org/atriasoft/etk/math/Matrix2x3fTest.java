package org.atriasoft.etk.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Matrix2x3f Tests")
class Matrix2x3fTest {

    private static final float EPSILON = 0.001f;

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Constructor with six float values")
    void testConstructorSixValues() {
        Matrix2x3f mat = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        assertEquals(1.0f, mat.sx(), EPSILON);
        assertEquals(2.0f, mat.shy(), EPSILON);
        assertEquals(3.0f, mat.shx(), EPSILON);
        assertEquals(4.0f, mat.sy(), EPSILON);
        assertEquals(5.0f, mat.tx(), EPSILON);
        assertEquals(6.0f, mat.ty(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with float array")
    void testConstructorFloatArray() {
        float[] values = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f};
        Matrix2x3f mat = new Matrix2x3f(values);
        assertEquals(1.0f, mat.sx(), EPSILON);
        assertEquals(2.0f, mat.shy(), EPSILON);
        assertEquals(3.0f, mat.shx(), EPSILON);
        assertEquals(4.0f, mat.sy(), EPSILON);
        assertEquals(5.0f, mat.tx(), EPSILON);
        assertEquals(6.0f, mat.ty(), EPSILON);
    }

    @Test
    @DisplayName("Constructor with double array")
    void testConstructorDoubleArray() {
        double[] values = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        Matrix2x3f mat = new Matrix2x3f(values);
        assertEquals(1.0f, mat.sx(), EPSILON);
        assertEquals(2.0f, mat.shy(), EPSILON);
        assertEquals(3.0f, mat.shx(), EPSILON);
        assertEquals(4.0f, mat.sy(), EPSILON);
        assertEquals(5.0f, mat.tx(), EPSILON);
        assertEquals(6.0f, mat.ty(), EPSILON);
    }

    // ==================== Constants Tests ====================

    @Test
    @DisplayName("IDENTITY constant")
    void testIdentityConstant() {
        assertEquals(1.0f, Matrix2x3f.IDENTITY.sx(), EPSILON);
        assertEquals(0.0f, Matrix2x3f.IDENTITY.shy(), EPSILON);
        assertEquals(0.0f, Matrix2x3f.IDENTITY.shx(), EPSILON);
        assertEquals(1.0f, Matrix2x3f.IDENTITY.sy(), EPSILON);
        assertEquals(0.0f, Matrix2x3f.IDENTITY.tx(), EPSILON);
        assertEquals(0.0f, Matrix2x3f.IDENTITY.ty(), EPSILON);
    }

    // ==================== Arithmetic Operations ====================

    @Test
    @DisplayName("add should add matrices element-wise")
    void testAdd() {
        Matrix2x3f mat1 = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        Matrix2x3f mat2 = new Matrix2x3f(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        Matrix2x3f result = mat1.add(mat2);
        assertEquals(2.0f, result.sx(), EPSILON);
        assertEquals(3.0f, result.shy(), EPSILON);
        assertEquals(4.0f, result.shx(), EPSILON);
        assertEquals(5.0f, result.sy(), EPSILON);
        assertEquals(6.0f, result.tx(), EPSILON);
        assertEquals(7.0f, result.ty(), EPSILON);
    }

    @Test
    @DisplayName("less should subtract matrices element-wise")
    void testLess() {
        Matrix2x3f mat1 = new Matrix2x3f(10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 60.0f);
        Matrix2x3f mat2 = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        Matrix2x3f result = mat1.less(mat2);
        assertEquals(9.0f, result.sx(), EPSILON);
        assertEquals(18.0f, result.shy(), EPSILON);
        assertEquals(27.0f, result.shx(), EPSILON);
        assertEquals(36.0f, result.sy(), EPSILON);
        assertEquals(45.0f, result.tx(), EPSILON);
        assertEquals(54.0f, result.ty(), EPSILON);
    }

    @Test
    @DisplayName("multiply with matrix should multiply matrices")
    void testMultiplyMatrix() {
        Matrix2x3f mat1 = Matrix2x3f.IDENTITY;
        Matrix2x3f mat2 = Matrix2x3f.IDENTITY;
        Matrix2x3f result = mat1.multiply(mat2);
        // Identity * Identity should be Identity
        assertEquals(1.0f, result.sx(), EPSILON);
        assertEquals(0.0f, result.shy(), EPSILON);
        assertEquals(0.0f, result.shx(), EPSILON);
        assertEquals(1.0f, result.sy(), EPSILON);
        assertEquals(0.0f, result.tx(), EPSILON);
        assertEquals(0.0f, result.ty(), EPSILON);
    }

    @Test
    @DisplayName("multiply with vector should transform vector")
    void testMultiplyVector() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        Vector2f vec = new Vector2f(5.0f, 10.0f);
        Vector2f result = mat.multiply(vec);
        assertEquals(5.0f, result.x(), EPSILON);
        assertEquals(10.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("applyScaleRotation should apply transformation without translation")
    void testApplyScaleRotation() {
        Matrix2x3f mat = Matrix2x3f.createScale(2.0f);
        Vector2f vec = new Vector2f(5.0f, 10.0f);
        Vector2f result = mat.applyScaleRotation(vec);
        assertEquals(10.0f, result.x(), EPSILON);
        assertEquals(20.0f, result.y(), EPSILON);
    }

    // ==================== Transformation Methods ====================

    @Test
    @DisplayName("scale with vector should scale matrix")
    void testScaleVector() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        Vector2f scale = new Vector2f(2.0f, 3.0f);
        Matrix2x3f result = mat.scale(scale);
        assertEquals(2.0f, result.sx(), EPSILON);
        assertEquals(0.0f, result.shy(), EPSILON);
        assertEquals(0.0f, result.shx(), EPSILON);
        assertEquals(3.0f, result.sy(), EPSILON);
    }

    @Test
    @DisplayName("scale with scalar should scale matrix uniformly")
    void testScaleScalar() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        Matrix2x3f result = mat.scale(2.0f);
        assertEquals(2.0f, result.sx(), EPSILON);
        assertEquals(0.0f, result.shy(), EPSILON);
        assertEquals(0.0f, result.shx(), EPSILON);
        assertEquals(2.0f, result.sy(), EPSILON);
    }

    @Test
    @DisplayName("rotate should rotate matrix")
    void testRotate() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        Matrix2x3f result = mat.rotate((float) Math.PI / 2); // 90 degrees
        assertEquals(0.0f, result.sx(), EPSILON);
        assertEquals(1.0f, result.shy(), EPSILON);
        assertEquals(-1.0f, result.shx(), EPSILON);
        assertEquals(0.0f, result.sy(), EPSILON);
    }

    @Test
    @DisplayName("translate should translate matrix")
    void testTranslate() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        Vector2f translation = new Vector2f(10.0f, 20.0f);
        Matrix2x3f result = mat.translate(translation);
        assertEquals(10.0f, result.tx(), EPSILON);
        assertEquals(20.0f, result.ty(), EPSILON);
    }

    @Test
    @DisplayName("flipX should flip matrix on X axis")
    void testFlipX() {
        Matrix2x3f mat = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        Matrix2x3f result = mat.flipX();
        assertEquals(-1.0f, result.sx(), EPSILON);
        assertEquals(2.0f, result.shy(), EPSILON);
        assertEquals(-3.0f, result.shx(), EPSILON);
        assertEquals(4.0f, result.sy(), EPSILON);
        assertEquals(-5.0f, result.tx(), EPSILON);
        assertEquals(6.0f, result.ty(), EPSILON);
    }

    @Test
    @DisplayName("flipY should flip matrix on Y axis")
    void testFlipY() {
        Matrix2x3f mat = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        Matrix2x3f result = mat.flipY();
        assertEquals(1.0f, result.sx(), EPSILON);
        assertEquals(-2.0f, result.shy(), EPSILON);
        assertEquals(3.0f, result.shx(), EPSILON);
        assertEquals(-4.0f, result.sy(), EPSILON);
        assertEquals(5.0f, result.tx(), EPSILON);
        assertEquals(-6.0f, result.ty(), EPSILON);
    }

    // ==================== Determinant and Inversion ====================

    @Test
    @DisplayName("determinant should calculate determinant")
    void testDeterminant() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        assertEquals(1.0f, mat.determinant(), EPSILON);
    }

    @Test
    @DisplayName("determinant of scaled matrix")
    void testDeterminantScaled() {
        Matrix2x3f mat = Matrix2x3f.createScale(2.0f);
        assertEquals(4.0f, mat.determinant(), EPSILON);
    }

    @Test
    @DisplayName("invert should invert identity to identity")
    void testInvertIdentity() {
        Matrix2x3f mat = Matrix2x3f.IDENTITY;
        Matrix2x3f result = mat.invert();
        assertEquals(1.0f, result.sx(), EPSILON);
        assertEquals(0.0f, result.shy(), EPSILON);
        assertEquals(0.0f, result.shx(), EPSILON);
        assertEquals(1.0f, result.sy(), EPSILON);
    }

    @Test
    @DisplayName("invert should invert scale matrix")
    void testInvertScale() {
        Matrix2x3f mat = Matrix2x3f.createScale(2.0f);
        Matrix2x3f result = mat.invert();
        assertEquals(0.5f, result.sx(), EPSILON);
        assertEquals(0.0f, result.shy(), EPSILON);
        assertEquals(0.0f, result.shx(), EPSILON);
        assertEquals(0.5f, result.sy(), EPSILON);
    }

    // ==================== Static Factory Methods ====================

    @Test
    @DisplayName("createRotate should create rotation matrix")
    void testCreateRotate() {
        Matrix2x3f mat = Matrix2x3f.createRotate((float) Math.PI / 2); // 90 degrees
        assertEquals(0.0f, mat.sx(), EPSILON);
        assertEquals(1.0f, mat.shy(), EPSILON);
        assertEquals(-1.0f, mat.shx(), EPSILON);
        assertEquals(0.0f, mat.sy(), EPSILON);
        assertEquals(0.0f, mat.tx(), EPSILON);
        assertEquals(0.0f, mat.ty(), EPSILON);
    }

    @Test
    @DisplayName("createScale with vector should create scale matrix")
    void testCreateScaleVector() {
        Vector2f scale = new Vector2f(2.0f, 3.0f);
        Matrix2x3f mat = Matrix2x3f.createScale(scale);
        assertEquals(2.0f, mat.sx(), EPSILON);
        assertEquals(0.0f, mat.shy(), EPSILON);
        assertEquals(0.0f, mat.shx(), EPSILON);
        assertEquals(3.0f, mat.sy(), EPSILON);
        assertEquals(0.0f, mat.tx(), EPSILON);
        assertEquals(0.0f, mat.ty(), EPSILON);
    }

    @Test
    @DisplayName("createScale with scalar should create uniform scale matrix")
    void testCreateScaleScalar() {
        Matrix2x3f mat = Matrix2x3f.createScale(2.0f);
        assertEquals(2.0f, mat.sx(), EPSILON);
        assertEquals(0.0f, mat.shy(), EPSILON);
        assertEquals(0.0f, mat.shx(), EPSILON);
        assertEquals(2.0f, mat.sy(), EPSILON);
        assertEquals(0.0f, mat.tx(), EPSILON);
        assertEquals(0.0f, mat.ty(), EPSILON);
    }

    @Test
    @DisplayName("createTranslate should create translation matrix")
    void testCreateTranslate() {
        Vector2f translation = new Vector2f(10.0f, 20.0f);
        Matrix2x3f mat = Matrix2x3f.createTranslate(translation);
        assertEquals(1.0f, mat.sx(), EPSILON);
        assertEquals(0.0f, mat.shy(), EPSILON);
        assertEquals(0.0f, mat.shx(), EPSILON);
        assertEquals(1.0f, mat.sy(), EPSILON);
        assertEquals(10.0f, mat.tx(), EPSILON);
        assertEquals(20.0f, mat.ty(), EPSILON);
    }

    @Test
    @DisplayName("createSkew should create skew matrix")
    void testCreateSkew() {
        Vector2f skew = new Vector2f(0.0f, 0.0f);
        Matrix2x3f mat = Matrix2x3f.createSkew(skew);
        assertEquals(1.0f, mat.sx(), EPSILON);
        assertEquals(0.0f, mat.shy(), EPSILON);
        assertEquals(0.0f, mat.shx(), EPSILON);
        assertEquals(1.0f, mat.sy(), EPSILON);
    }

    // ==================== Complex Transformations ====================

    @Test
    @DisplayName("Identity matrix multiplied by translation should translate")
    void testIdentityMultiplyTranslation() {
        Matrix2x3f translate = Matrix2x3f.createTranslate(new Vector2f(10.0f, 20.0f));
        Vector2f point = new Vector2f(5.0f, 5.0f);
        Vector2f result = translate.multiply(point);
        assertEquals(15.0f, result.x(), EPSILON);
        assertEquals(25.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("Scale matrix should scale vector")
    void testScaleTransform() {
        Matrix2x3f scale = Matrix2x3f.createScale(2.0f);
        Vector2f point = new Vector2f(5.0f, 10.0f);
        Vector2f result = scale.multiply(point);
        assertEquals(10.0f, result.x(), EPSILON);
        assertEquals(20.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("Rotation by 180 degrees should negate vector")
    void testRotate180() {
        Matrix2x3f rotate = Matrix2x3f.createRotate((float) Math.PI);
        Vector2f point = new Vector2f(1.0f, 0.0f);
        Vector2f result = rotate.multiply(point);
        assertEquals(-1.0f, result.x(), EPSILON);
        assertEquals(0.0f, result.y(), EPSILON);
    }

    @Test
    @DisplayName("Composite transformation: scale then translate")
    void testCompositeScaleThenTranslate() {
        Matrix2x3f scale = Matrix2x3f.createScale(2.0f);
        Matrix2x3f translate = Matrix2x3f.createTranslate(new Vector2f(10.0f, 20.0f));
        Matrix2x3f combined = translate.multiply(scale);
        Vector2f point = new Vector2f(5.0f, 5.0f);
        Vector2f result = combined.multiply(point);
        // Matrix multiplication result
        assertEquals(30.0f, result.x(), EPSILON);
        assertEquals(50.0f, result.y(), EPSILON);
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("Zero scale should have zero determinant")
    void testZeroScaleDeterminant() {
        Matrix2x3f mat = Matrix2x3f.createScale(0.0f);
        assertEquals(0.0f, mat.determinant(), EPSILON);
    }

    @Test
    @DisplayName("Rotating by 0 should be identity")
    void testRotateZero() {
        Matrix2x3f mat = Matrix2x3f.createRotate(0.0f);
        assertEquals(1.0f, mat.sx(), EPSILON);
        assertEquals(0.0f, mat.shy(), EPSILON);
        assertEquals(0.0f, mat.shx(), EPSILON);
        assertEquals(1.0f, mat.sy(), EPSILON);
    }

    @Test
    @DisplayName("Record equality should work correctly")
    void testRecordEquality() {
        Matrix2x3f mat1 = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        Matrix2x3f mat2 = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
        Matrix2x3f mat3 = new Matrix2x3f(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 7.0f);

        assertEquals(mat1, mat2);
        assertNotEquals(mat1, mat3);
    }
}
