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

import it.water.core.interceptors.annotations.FrameworkComponent;
import it.water.repository.jpa.WaterJpaRepositoryImpl;

@FrameworkComponent
public class WaterTestEntityRepositoryImpl extends WaterJpaRepositoryImpl<TestEntity> implements WaterTestEntityRepository {

    public WaterTestEntityRepositoryImpl() {
        super(TestEntity.class, "water-default-persistence-unit");
    }

    @Override
    public boolean isEntityManagerNotNull() {
        return this.getEntityManager() != null;
    }

    @Override
    public String getClassTypeName() {
        return this.getType().getName();
    }

    @Override
    public String getPersistenceUnit() {
        return this.getPersistenceUnitName();
    }
}
