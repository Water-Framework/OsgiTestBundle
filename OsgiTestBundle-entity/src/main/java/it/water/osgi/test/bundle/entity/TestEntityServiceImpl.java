
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

import it.water.core.api.permission.SecurityContext;
import it.water.core.api.registry.ComponentRegistry;
import it.water.core.api.service.BaseEntitySystemApi;
import it.water.repository.service.BaseEntityServiceImpl;
import org.osgi.service.cdi.annotations.Bean;
import org.osgi.service.cdi.annotations.Reference;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.cdi.annotations.SingleComponent;

import javax.inject.Inject;

@SingleComponent
@Bean
@Service(value = TestEntityApi.class)
public class TestEntityServiceImpl extends BaseEntityServiceImpl<TestEntity> implements TestEntityApi {

    @Inject
    @Reference
    private TestEntitySystemApi systemService;

    @Inject
    @Reference
    private ComponentRegistry componentRegistry;

    public TestEntityServiceImpl() {
        super(TestEntity.class);
    }

    @Override
    protected SecurityContext getSecurityContext() {
        return null;
    }

    @Override
    protected BaseEntitySystemApi<TestEntity> getSystemService() {
        return systemService;
    }

    @Override
    protected ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    public void setSystemService(TestEntitySystemApi systemService) {
        this.systemService = systemService;
    }

    public void setComponentRegistry(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }
}
