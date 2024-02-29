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
import it.water.core.bundle.ApplicationInitializer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class TestBundleActivator<T> extends ApplicationInitializer<T, ServiceRegistration<T>> implements org.osgi.framework.BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        this.initializeFrameworkComponents();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        //method not needed
    }

    @Override
    protected ComponentRegistry getComponentRegistry() {
        BundleContext ctx = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference<ComponentRegistry> sr = ctx.getServiceReference(ComponentRegistry.class);
        return ctx.getService(sr);
    }

}
