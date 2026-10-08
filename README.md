# ETK - Edouard's ToolKit

[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)

ETK (Edouard's ToolKit) est une bibliothèque Java fournissant des outils mathématiques et utilitaires pour le développement d'applications graphiques et de jeux.

## Table des Matières

- [Fonctionnalités](#fonctionnalités)
- [Installation](#installation)
- [Guide de Démarrage Rapide](#guide-de-démarrage-rapide)
- [Documentation](#documentation)
- [Exemples d'Utilisation](#exemples-dutilisation)
- [Tests](#tests)
- [Contribution](#contribution)
- [Licence](#licence)

## Fonctionnalités

### 📐 Mathématiques
- **Vecteurs**: Vector2f, Vector2i, Vector2b, Vector3f, Vector3i, Vector3b, Vector4f
- **Matrices**: Matrix2x3f, Matrix3f, Matrix4f
- **Transformations**: Quaternion, Transform3D
- **Utilitaires**: FMath (fonctions mathématiques étendues), constantes

### 🎨 Graphiques
- **Couleurs**: Classe Color avec support de 140+ couleurs nommées (CSS)
- **Bordures**: BorderRadius pour coins arrondis
- **Espacement**: Insets pour marges et padding
- **Dimensions**: Dimension1f, Dimension2f, Dimension3f avec support d'unités (px, %, cm, mm, m, km, in, ft)

### 🔧 Utilitaires
- **Collections**: Pair, Dynamic (conteneur générique)
- **Tableaux**: ArraysTools (manipulation de tableaux primitifs)
- **Fichiers**: FilePos (suivi de position dans les fichiers)
- **Threads**: ThreadAbstract (gestion de threads simplifiée)
- **URI**: Parsing et manipulation d'URIs

## Installation

### Maven

etk n'est publié sur aucun dépôt Maven distant : installez-le dans votre dépôt local (voir
[Build depuis les Sources](#build-depuis-les-sources)), puis ajoutez la dépendance:

```xml
<dependency>
  <groupId>org.atriasoft</groupId>
  <artifactId>etk</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Build depuis les Sources

```bash
git clone <repository-url>
cd etk
mvn clean install
```

## Guide de Démarrage Rapide

### Utilisation des Vecteurs

```java
import org.atriasoft.etk.math.Vector2f;

// Création d'un vecteur
Vector2f v1 = new Vector2f(3.0f, 4.0f);
Vector2f v2 = new Vector2f(1.0f, 2.0f);

// Opérations vectorielles
Vector2f sum = v1.add(v2);           // (4.0, 6.0)
Vector2f scaled = v1.multiply(2.0f); // (6.0, 8.0)
float length = v1.length();          // 5.0
float dot = v1.dot(v2);              // 11.0

// Normalisation
Vector2f normalized = v1.normalize(); // (0.6, 0.8)

// Parsing depuis string
Vector2f v3 = Vector2f.valueOf("10,20"); // (10.0, 20.0)
```

### Utilisation des Couleurs

```java
import org.atriasoft.etk.Color;

// Couleurs prédéfinies
Color red = Color.RED;
Color blue = Color.BLUE;

// Création depuis composants RGB
Color custom = new Color(0.5f, 0.3f, 0.8f);

// Parsing depuis différents formats
Color c1 = Color.valueOf("red");           // Nom de couleur
Color c2 = Color.valueOf("#FF0000");       // Hexadécimal
Color c3 = Color.valueOf("rgb(255,0,0)");  // Notation RGB
Color c4 = Color.valueOf("0.5,0.3,0.8");   // Valeurs flottantes

// Conversion en hexadécimal
String hex = red.toStringSharp(); // "#FF0000"

// Modification de composants (immutable)
Color darker = red.withR(0.5f);   // Rouge plus sombre
Color transparent = red.withA(0.5f); // Rouge semi-transparent
```

### Utilisation des Bordures Arrondies

```java
import org.atriasoft.etk.BorderRadius;

// Création avec rayon uniforme
BorderRadius uniform = new BorderRadius(10.0f);

// Création avec rayons différents
BorderRadius custom = new BorderRadius(5.0f, 10.0f, 15.0f, 20.0f);

// Parsing depuis string (format CSS)
BorderRadius r1 = BorderRadius.valueOf("10");        // Tous les coins = 10
BorderRadius r2 = BorderRadius.valueOf("5 10");      // TL/BR=5, TR/BL=10
BorderRadius r3 = BorderRadius.valueOf("5 10 15");   // TL=5, TR/BL=10, BR=15
BorderRadius r4 = BorderRadius.valueOf("5 10 15 20"); // Chaque coin différent

// Opérations
BorderRadius doubled = custom.multiply(2.0f);
BorderRadius combined = r1.add(r2);
```

### Utilisation des Dimensions avec Unités

```java
import org.atriasoft.etk.Dimension1f;
import org.atriasoft.etk.Distance;

// Création avec unité
Dimension1f pixels = new Dimension1f(100.0f, Distance.PIXEL);
Dimension1f percent = new Dimension1f(50.0f, Distance.POURCENT);
Dimension1f cm = new Dimension1f(5.0f, Distance.CENTIMETER);

// Parsing
Dimension1f d1 = Dimension1f.valueOf("100px");
Dimension1f d2 = Dimension1f.valueOf("50%");
Dimension1f d3 = Dimension1f.valueOf("5cm");

// Conversion en pixels (avec contexte)
float pixelValue = d1.getPixel(100.0f); // 100.0 pixels
float percentPixels = d2.getPixel(200.0f); // 100.0 pixels (50% de 200)
```

### Utilisation des Matrices

```java
import org.atriasoft.etk.math.Matrix2x3f;
import org.atriasoft.etk.math.Vector2f;

// Création de matrices de transformation
Matrix2x3f identity = Matrix2x3f.IDENTITY;
Matrix2x3f translation = Matrix2x3f.createTranslate(10.0f, 20.0f);
Matrix2x3f rotation = Matrix2x3f.createRotate((float)Math.PI / 4); // 45°
Matrix2x3f scale = Matrix2x3f.createScale(2.0f, 2.0f);

// Combinaison de transformations
Matrix2x3f transform = translation.multiply(rotation).multiply(scale);

// Application à un vecteur
Vector2f point = new Vector2f(1.0f, 0.0f);
Vector2f transformed = transform.multiply(point);
```

### Utilisation des Utilitaires

```java
import org.atriasoft.etk.util.Pair;
import org.atriasoft.etk.util.ArraysTools;

// Paires
Pair<String, Integer> pair = Pair.of("key", 42);
String first = pair.first();   // "key"
Integer second = pair.second(); // 42

// Modification (immutable)
Pair<String, Integer> newPair = pair.withSecond(100);

// Tableaux
Integer[] array = {1, 2, 3, 4, 5};
int[] primitives = ArraysTools.toPrimitive(array);

// Remplissage
int[] filled = new int[10];
ArraysTools.fill(filled, 5); // [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]
```

## Documentation

### JavaDoc

Générez la documentation JavaDoc:

```bash
mvn javadoc:javadoc
```

La documentation sera générée dans `target/site/apidocs/`.

Ou pour créer un JAR avec la JavaDoc:

```bash
mvn javadoc:jar
```

### Rapports de Tests

Consultez les rapports de tests dans `target/surefire-reports/` après avoir exécuté:

```bash
mvn test
```

### Couverture de Code

Générez le rapport de couverture avec JaCoCo:

```bash
mvn clean test jacoco:report
```

Le rapport sera disponible dans `target/site/jacoco/index.html`.

## Exemples d'Utilisation

### Exemple: Calcul de Distance

```java
import org.atriasoft.etk.math.Vector2f;

public class DistanceExample {
    public static void main(String[] args) {
        Vector2f pointA = new Vector2f(0.0f, 0.0f);
        Vector2f pointB = new Vector2f(3.0f, 4.0f);

        float distance = pointA.distance(pointB);
        System.out.println("Distance: " + distance); // 5.0
    }
}
```

### Exemple: Palette de Couleurs

```java
import org.atriasoft.etk.Color;

public class ColorPaletteExample {
    public static Color[] createPalette(Color base, int count) {
        Color[] palette = new Color[count];
        for (int i = 0; i < count; i++) {
            float brightness = 0.5f + (0.5f * i / count);
            palette[i] = base.withR(base.r() * brightness)
                            .withG(base.g() * brightness)
                            .withB(base.b() * brightness);
        }
        return palette;
    }

    public static void main(String[] args) {
        Color[] palette = createPalette(Color.BLUE, 5);
        for (Color c : palette) {
            System.out.println(c.toStringSharp());
        }
    }
}
```

### Exemple: Transformation 2D

```java
import org.atriasoft.etk.math.Matrix2x3f;
import org.atriasoft.etk.math.Vector2f;

public class TransformExample {
    public static void main(String[] args) {
        // Crée une transformation qui translate, puis tourne, puis scale
        Matrix2x3f transform = Matrix2x3f.createTranslate(100.0f, 50.0f)
            .multiply(Matrix2x3f.createRotate((float)Math.toRadians(45)))
            .multiply(Matrix2x3f.createScale(2.0f, 2.0f));

        // Applique la transformation à plusieurs points
        Vector2f[] points = {
            new Vector2f(0.0f, 0.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(1.0f, 1.0f),
            new Vector2f(0.0f, 1.0f)
        };

        for (Vector2f point : points) {
            Vector2f transformed = transform.multiply(point);
            System.out.println(point + " -> " + transformed);
        }
    }
}
```

## Tests

ETK dispose d'une suite complète de tests unitaires JUnit 5.

### Exécution des Tests

```bash
# Tous les tests
mvn test

# Tests spécifiques
mvn test -Dtest=Vector2fTest
mvn test -Dtest=ColorTest
mvn test -Dtest=org.atriasoft.etk.math.*Test

# Avec rapport de couverture
mvn clean test jacoco:report
```

### Statistiques des Tests

- **18 fichiers de tests**
- **680+ méthodes de test**
- **Couverture**: ~50% des classes principales
- **Tous les tests passent** ✅

### Tests Créés

**Package math:**
- Vector2fTest (71 tests)
- Vector3fTest (66 tests)
- Vector2iTest (57 tests)
- FMathTest (42 tests)
- Vector4fTest (32 tests)
- Matrix2x3fTest (31 tests)
- Vector3iTest (24 tests)
- Vector2bTest (22 tests)
- Vector3bTest (14 tests)
- ConstantTest (6 tests)

**Package principal:**
- BorderRadiusTest (58 tests)
- InsetsTest (45 tests)
- DistanceTest (57 tests)
- Dimension1fTest (43 tests)
- ColorTest (40 tests)

**Package util:**
- ArraysToolsTest (32 tests)
- FilePosTest (22 tests)
- PairTest (18 tests)

## Structure du Projet

```
etk/
├── src/
│   ├── main/
│   │   └── org/atriasoft/etk/
│   │       ├── math/           # Classes mathématiques
│   │       │   ├── Vector2f.java
│   │       │   ├── Vector3f.java
│   │       │   ├── Matrix4f.java
│   │       │   └── ...
│   │       ├── util/           # Utilitaires
│   │       │   ├── Pair.java
│   │       │   ├── ArraysTools.java
│   │       │   └── ...
│   │       ├── theme/          # Thèmes
│   │       ├── Color.java      # Gestion des couleurs
│   │       ├── BorderRadius.java
│   │       ├── Dimension1f.java
│   │       └── ...
│   └── test/
│       └── org/atriasoft/etk/  # Tests unitaires (structure identique)
├── pom.xml                      # Configuration Maven
├── README.md                    # Ce fichier
├── JAVADOC_AUDIT_REPORT.md     # Rapport d'audit JavaDoc
└── TESTS_CREATED.md            # Documentation des tests
```

## Contribution

### Prérequis
- Java 25+
- Maven 3.8+

### Workflow
1. Forkez le projet
2. Créez une branche feature (`git checkout -b feature/amazing-feature`)
3. Commitez vos changements (`git commit -m 'Add amazing feature'`)
4. Pushez sur la branche (`git push origin feature/amazing-feature`)
5. Ouvrez une Pull Request

### Standards de Code
- Utilisez des JavaDoc pour toutes les classes et méthodes publiques
- Écrivez des tests unitaires pour les nouvelles fonctionnalités
- Suivez les conventions de nommage Java
- Utilisez l'annotation `@CheckReturnValue` pour les méthodes fonctionnelles

### Tests
Assurez-vous que tous les tests passent avant de soumettre:

```bash
mvn clean test
```

## Build

### Build Local

```bash
# Compilation
mvn clean compile

# Package (JAR)
mvn clean package

# Installation locale
mvn clean install
```

### Génération des Artefacts

```bash
# JAR avec sources
mvn source:jar

# JAR avec JavaDoc
mvn javadoc:jar

# Tout générer
mvn clean package source:jar javadoc:jar
```

## Dépendances

- **SLF4J** (2.1.0-alpha1) - Logging
- **SpotBugs Annotations** (4.9.8) - Annotations pour analyse statique
- **JUnit Jupiter** (6.1.0-M1) - Tests unitaires (scope: test)

## Licence

Ce projet est sous licence [Mozilla Public License 2.0](https://opensource.org/licenses/MPL-2.0).

```
Copyright © 2024-2026 Edouard DUPIN
Licensed under the Mozilla Public License 2.0
```

## Auteur

**Edouard DUPIN**
- Email: edouard.dupin@proton.me
- Organisation: Atria Soft

## Changelog

### Version 0.1.0 (2026-03-05)
- Version initiale
- Classes mathématiques complètes (vecteurs, matrices, quaternions)
- Gestion des couleurs avec 140+ couleurs nommées
- Support des dimensions avec unités
- Utilitaires pour tableaux et collections
- Suite de tests complète (680+ tests)
- Documentation JavaDoc complète

## Support

Pour toute question ou problème:
1. Consultez la JavaDoc (`mvn javadoc:javadoc`)
2. Vérifiez les exemples ci-dessus
3. Ouvrez une issue sur le dépôt Git

## Ressources

- [Documentation JavaDoc](target/site/apidocs/)
- [Rapport de Couverture](target/site/jacoco/)
- [Rapport d'Audit JavaDoc](JAVADOC_AUDIT_REPORT.md)
- [Documentation des Tests](TESTS_CREATED.md)
