
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

import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;

import it.water.repository.jpa.osgi.OsgiBaseJpaRepository;

@SingleComponent
@Bean
@Service(value = TestEntityRepository.class)
public class TestEntityRepositoryImpl extends OsgiBaseJpaRepository<TestEntity> implements TestEntityRepository {

    public TestEntityRepositoryImpl() {
        super(TestEntity.class, "water-default-persistence-unit");
    }

    @Override
    protected String getPersistenceUnitName() {
        return "water-default-persistence-unit";
    }
}
