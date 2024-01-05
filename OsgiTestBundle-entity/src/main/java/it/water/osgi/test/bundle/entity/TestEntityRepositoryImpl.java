
/*
 * Copyright 2024 Aristide Cittadino
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.water.osgi.test.bundle.entity;

import it.water.repository.jpa.BaseJpaRepositoryImpl;
import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.Reference;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

@SingleComponent
@Bean
@Service(value = TestEntityRepository.class)
@Transactional
public class TestEntityRepositoryImpl extends BaseJpaRepositoryImpl<TestEntity> implements TestEntityRepository {
    @Inject
    @Reference
    @PersistenceUnit(unitName = "water-persistence-unit")
    EntityManagerFactory entityManagerFactory;

    public TestEntityRepositoryImpl() {
        super(TestEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
