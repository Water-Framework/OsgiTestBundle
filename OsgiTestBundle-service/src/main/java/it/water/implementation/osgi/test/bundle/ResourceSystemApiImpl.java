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
package it.water.implementation.osgi.test.bundle;

import it.water.core.api.registry.ComponentRegistry;
import it.water.core.api.validation.WaterValidator;
import it.water.core.interceptors.annotations.FrameworkComponent;
import it.water.core.interceptors.annotations.Inject;
import it.water.core.service.GenericSystemServiceImpl;
import it.water.core.validation.validators.WaterJakartaValidator;
import lombok.Getter;
import lombok.Setter;

@FrameworkComponent(services = ResourceSystemApi.class)
public class ResourceSystemApiImpl extends GenericSystemServiceImpl implements ResourceSystemApi {

    @Inject
    @Setter
    @Getter
    private ComponentRegistry componentRegistry;

    @Override
    public void validateResource(TestResource resource) {
        validate(resource);
    }

    @Override
    protected WaterValidator getValidatorInstance() {
        return new WaterJakartaValidator();
    }
}
