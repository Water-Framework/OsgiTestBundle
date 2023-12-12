
/*
 Copyright 2019-2023 ACSoftware

 Licensed under the Apache License, Version 2.0 (the "License")
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

package it.water.osgi.test.bundle.entity;

import it.water.core.api.registry.filter.ComponentFilterBuilder;
import it.water.repository.service.jakarta.EntitySystemServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.Reference;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;

import javax.inject.Inject;

@SingleComponent
@Bean
@Service(value = TestEntitySystemApi.class)
public class TestEntitySystemServiceImpl extends EntitySystemServiceImpl<TestEntity> implements TestEntitySystemApi {
    @Inject
    @Reference
    @Getter
    private TestEntityRepository repository;

    //used for testing interceptors and injectors
    @it.water.core.interceptors.annotations.Inject
    @Setter
    @Getter
    private ComponentFilterBuilder componentFilterBuilder;

    public TestEntitySystemServiceImpl() {
        super(TestEntity.class);
    }

}
