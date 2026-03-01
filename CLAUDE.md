# OsgiTestBundle — OSGi Integration Test Bundle

## Purpose
A purpose-built test bundle for validating Water Framework component lifecycle, bundle activation, service registration, and inter-bundle communication in a real OSGi container (Apache Karaf via Pax Exam). Not a production module — used only during framework development to verify OSGi-specific behaviors that cannot be tested with the standard `WaterTestExtension`.

## Sub-modules

| Sub-module | Role |
|---|---|
| `OsgiTestBundle-entity` | Defines test JPA entities used during OSGi integration tests |
| `OsgiTestBundle-service` | Implements test services and verifies OSGi lifecycle behaviors |

## What It Tests

### Bundle Lifecycle Validation
- `WaterBundleActivator.start()` correctly bootstraps the framework
- `@FrameworkComponent` services are registered in the OSGi `ServiceRegistry`
- `@OnActivate` and `@OnDeactivate` hooks fire at the correct bundle lifecycle events
- Services are discoverable via `OsgiComponentRegistry.findComponent()`

### Dependency Injection in OSGi
- `@Inject` resolution works across bundle boundaries
- Services from different bundles are correctly wired
- Circular dependency detection functions correctly

### JPA in OSGi
- `OsgiJpaRepositoryManager` creates repositories for test entities
- JTA transactions managed by Aries work correctly with test entities
- Hibernate entity scanning finds entities in the test bundle

### REST in OSGi
- REST controllers registered via `@FrameworkRestApi` appear in the CXF endpoint list
- JWT authentication filter is active on all protected endpoints
- HTTP responses return correct status codes and JSON structures

## OsgiTestBundle-entity

Contains simple test entities that mirror real module patterns:

```java
@Entity
@Table(name = "osgi_test_entity")
@FrameworkComponent
public class OsgiTestEntity extends AbstractJpaEntity implements ProtectedEntity {
    @NotNull @NoMalitiusCode
    private String name;
}
```

Also defines:
- `OsgiTestEntityApi`
- `OsgiTestEntitySystemApi`
- `OsgiTestEntityRepository`

## OsgiTestBundle-service

Contains:
- `OsgiTestEntityServiceImpl` (implements `OsgiTestEntityApi`)
- `OsgiTestEntitySystemServiceImpl` (implements `OsgiTestEntitySystemApi`)
- `OsgiTestEntityRepositoryImpl` (JPA-backed)
- A REST controller (`OsgiTestEntityRestController`) for validating HTTP endpoint registration
- `OsgiTestBundleActivator` — custom `BundleActivator` for test-specific setup

## Pax Exam Test Pattern

```java
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
public class OsgiIntegrationTest {

    @Configuration
    public Option[] config() {
        return options(
            // Karaf container
            karafDistributionConfiguration()
                .frameworkUrl(maven("org.apache.karaf", "apache-karaf").type("zip").version("4.4.6"))
                .unpackDirectory(new File("target/exam")),

            // Water features
            features(maven("it.water.distribution", "Distribution-karaf")
                .type("xml").classifier("features").version("3.0.0"),
                "water-core", "water-persistence", "water-rest"),

            // Test bundle
            bundle("file:OsgiTestBundle-service/build/libs/OsgiTestBundle-service-3.0.0.jar")
        );
    }

    @Inject
    private BundleContext bundleContext;

    @Test
    public void testServiceRegistration() {
        ServiceReference<OsgiTestEntityApi> ref =
            bundleContext.getServiceReference(OsgiTestEntityApi.class);
        assertNotNull("OsgiTestEntityApi should be registered", ref);
    }

    @Test
    public void testLifecycleHooks() {
        // Verify @OnActivate was called (check some side effect)
    }
}
```

## Dependencies
- `it.water.core:Core-api` — all framework interfaces
- `it.water.implementation:Implementation-osgi` — `OsgiComponentRegistry`, lifecycle
- `it.water.jparepository:JpaRepository-osgi` — `OsgiJpaRepositoryManager`
- `it.water.rest:Rest-jaxrs-api` — `@FrameworkRestApi` for REST controller testing
- `org.ops4j.pax.exam:pax-exam-container-karaf` — Karaf integration test container
- `org.ops4j.pax.exam:pax-exam-junit4` — JUnit 4 runner (Pax Exam uses JUnit 4)

## Usage
```bash
# Run OSGi integration tests
./gradlew OsgiTestBundle:integrationTest

# Or via Spring profile (if Spring integration tests exist)
./gradlew OsgiTestBundle-service:test
```

## Code Generation Rules
- This module is for **framework development only** — never add it as a dependency to business modules
- OSGi integration tests must use **Pax Exam** (JUnit 4) — `WaterTestExtension` (JUnit 5) does not start a real OSGi container
- Keep test entities minimal — `OsgiTestEntity` only needs enough fields to exercise the framework machinery
- When adding new OSGi-specific behaviors to `Implementation-osgi`, add corresponding Pax Exam tests here
- REST endpoint tests in this bundle use **Karate** feature files — never JUnit direct controller calls
- Clean up `target/exam/` directory between test runs to avoid stale Karaf container state
