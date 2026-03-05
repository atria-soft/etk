# Tests Unitaires ETK

Ce répertoire contient les tests unitaires JUnit 5 pour le projet ETK (Engineering Toolkit).

## Structure

```
test/
├── org/atriasoft/etk/
│   ├── ColorTest.java                    # Tests de la classe Color
│   ├── math/
│   │   ├── ConstantTest.java            # Tests des constantes mathématiques
│   │   ├── FMathTest.java               # Tests des fonctions mathématiques
│   │   ├── Vector2bTest.java            # Tests Vector2b (boolean)
│   │   ├── Vector2fTest.java            # Tests Vector2f (float)
│   │   ├── Vector2iTest.java            # Tests Vector2i (int)
│   │   ├── Vector3bTest.java            # Tests Vector3b (boolean)
│   │   ├── Vector3fTest.java            # Tests Vector3f (float)
│   │   ├── Vector3iTest.java            # Tests Vector3i (int)
│   │   └── Vector4fTest.java            # Tests Vector4f (float)
│   └── util/
│       └── PairTest.java                # Tests de la classe Pair
└── README.md                             # Ce fichier
```

## Statistiques

- **Fichiers de test**: 11
- **Méthodes @Test**: 392
- **Lignes de code**: 3,778
- **Couverture**: 31% des classes (11/35)

## Exécution

### Tous les tests
```bash
mvn test
```

### Un package spécifique
```bash
mvn test -Dtest=org.atriasoft.etk.math.*Test
```

### Une classe spécifique
```bash
mvn test -Dtest=Vector2fTest
```

## Documentation

Consultez les fichiers à la racine du projet ETK:
- `TEST_SUMMARY.md` - Résumé détaillé
- `TESTS_CREATED.md` - Liste exhaustive
- `QUICKSTART_TESTS.md` - Guide rapide

## Technologies

- **JUnit 5** (org.junit.jupiter.api)
- **Java 17+**
- **Maven**

## Qualité

Tous les tests suivent les meilleures pratiques:
- ✓ Noms descriptifs avec @DisplayName
- ✓ Tests isolés et indépendants
- ✓ Assertions précises (EPSILON pour floats)
- ✓ Couverture des cas normaux et limites
- ✓ Gestion appropriée des exceptions
