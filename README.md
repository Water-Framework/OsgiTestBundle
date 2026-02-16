# OsgiTestBundle Module

The **OsgiTestBundle** module is a test-only module that provides sample entities, services, and REST resources used to validate the Water Framework's OSGi runtime behavior. It serves as a reference implementation for testing component lifecycle, service registry, entity persistence, and multi-implementation patterns.

> **Note:** This module is not intended for production use. It exists solely to support framework integration tests.

## Sub-modules

| Sub-module | Description |
|---|---|
| **OsgiTestBundle-entity** | Test entity (`TestEntity`), API interfaces, repository, and service implementations |
| **OsgiTestBundle-service** | Test service interfaces with multiple implementations, REST resources |

## Test Entity

```java
@Entity
public class TestEntity extends AbstractJpaEntity {
    @NoMalitiusCode
    private String field1;

    @NoMalitiusCode
    private String field2;
}
```

A minimal JPA entity used to test:
- Entity persistence via `AbstractJpaEntity`
- Validation annotations (`@NoMalitiusCode`)
- Repository pattern (`TestEntityRepository` / `TestEntityRepositoryImpl`)
- Service layer (`TestEntityServiceImpl` / `TestEntitySystemServiceImpl`)

## Test Service Interfaces

### ServiceInterface

```java
public interface ServiceInterface extends Service {
    String doThing();
}
```

Four separate implementations (`ServiceInterfaceImpl1` through `ServiceInterfaceImpl4`) are provided to test:
- **Multi-implementation discovery** — The `ComponentRegistry` correctly handles multiple implementations of the same interface
- **Priority/ranking** — Service selection when multiple implementations compete
- **Dynamic registration/deregistration** — OSGi service lifecycle

### ResourceSystemApi

A test system API with its implementation (`ResourceSystemApiImpl`) used to validate system-level service registration and injection.

### TestResource

A test REST resource to validate REST controller registration in the OSGi runtime.

## What This Module Tests

| Concern | What Is Tested |
|---|---|
| **Component Lifecycle** | `@OnActivate` / `@OnDeactivate` invocation on OSGi bundle start/stop |
| **Service Registry** | Registration and lookup of `@FrameworkComponent` services |
| **Multi-Implementation** | Multiple implementations of `ServiceInterface` coexisting |
| **Entity Persistence** | CRUD operations on `TestEntity` via repository and service layers |
| **REST Registration** | REST resource discovery and endpoint exposure in OSGi |
| **Dependency Injection** | `@Inject` resolution across bundle boundaries |

## Dependencies

- **Core-api** — `Service`, `BaseEntityApi`, `BaseEntitySystemApi`
- **Core-model** — `AbstractJpaEntity`
- **Repository** — `BaseRepository`
- **JpaRepository** — JPA persistence implementation
- **Implementation-osgi** — OSGi runtime support
