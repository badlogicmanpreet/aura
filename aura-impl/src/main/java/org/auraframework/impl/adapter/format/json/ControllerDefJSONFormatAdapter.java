/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.impl.adapter.format.json;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.concurrent.ThreadSafe;

import org.auraframework.Aura;
import org.auraframework.def.ControllerDef;
import org.auraframework.ds.serviceloader.AuraServiceProvider;
import org.auraframework.system.AuraContext;
import org.auraframework.throwable.quickfix.QuickFixException;
import org.auraframework.util.json.JsonEncoder;

import aQute.bnd.annotation.component.Component;

/**
 * Controller JSON format adapter.
 * 
 * @since 0.0.116
 */
@ThreadSafe
@Component (provide=AuraServiceProvider.class)
public class ControllerDefJSONFormatAdapter extends JSONFormatAdapter<ControllerDef> {

    @Override
    public Class<ControllerDef> getType() {
        return ControllerDef.class;
    }

    @Override
    public void writeCollection(Collection<? extends ControllerDef> values, Appendable out) throws IOException,
            QuickFixException {
        AuraContext context = Aura.getContextService().getCurrentContext();
        JsonEncoder.serialize(values, out, context.getJsonSerializationContext());
    }

}
