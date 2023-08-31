package ru.job4j.assertj;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere")
                .startsWith("Sph")
                .containsIgnoringCase("sphere")
                .isNotEmpty();
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 8);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube")
                .startsWith("Cu")
                .containsIgnoringCase("cube")
                .isNotEmpty();
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 4);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .startsWith("Tetra")
                .containsIgnoringCase("tetrahedron")
                .isNotEmpty();
    }

    @Test
    void isNotExist() {
        Box box = new Box(0, -1);
        boolean rsl = box.isExist();
        assertThat(rsl).isFalse();
    }

    @Test
    void isExist() {
        Box box = new Box(0, 4);
        boolean rsl = box.isExist();
        assertThat(rsl).isTrue();
    }

    @Test
    void whenNumberOfVerticesThen0() {
        Box box = new Box(0, 4);
        int rsl = box.getNumberOfVertices();
        assertThat(rsl).isGreaterThan(-1)
                .isLessThan(1)
                .isNotNull()
                .isEqualTo(0);
    }

    @Test
    void whenNumberOfVerticesThenMinus1() {
        Box box = new Box(-1, 4);
        int rsl = box.getNumberOfVertices();
        assertThat(rsl).isGreaterThan(-2)
                .isLessThan(0)
                .isNotNull()
                .isEqualTo(-1);
    }

    @Test
    void whenGetAreaFromCube() {
        Box box = new Box(4, 3);
        double rsl = box.getArea();
        assertThat(rsl).isCloseTo(15.58d, withPrecision(0.01d))
                .isCloseTo(15.48d, Percentage.withPercentage(1.0d))
                .isGreaterThan(15.57d)
                .isLessThan(15.59d);
    }

    @Test
    void whenGetAreaFromUnknown() {
        Box box = new Box(-20, 3);
        double rsl = box.getArea();
        assertThat(rsl).isEqualTo(0D)
                .isNotNull()
                .isGreaterThanOrEqualTo(-1D);
    }


}