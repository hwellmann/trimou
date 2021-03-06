/*
 * Copyright 2013 Martin Kouba
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trimou.engine.context;

import static org.trimou.engine.context.ExecutionContext.TargetStack.CONTEXT;

import org.trimou.annotations.Internal;
import org.trimou.engine.MustacheEngine;

/**
 *
 * @author Martin Kouba
 */
@Internal
public class ExecutionContextBuilder {

    private final MustacheEngine engine;

    private Object data;

    /**
     *
     * @param engine
     */
    public ExecutionContextBuilder(MustacheEngine engine) {
        this.engine = engine;
    }

    /**
     *
     * @param data
     * @return self
     */
    public ExecutionContextBuilder withData(Object data) {
        this.data = data;
        return this;
    }

    /**
     *
     * @param debugMode
     * @return the built execution context
     * @deprecated The builder pattern does not make much sense here. Use {@link #build(MustacheEngine, Object, boolean)} instead.
     */
    @Deprecated
    public ExecutionContext build(boolean debugMode) {
        return build(engine, data, debugMode);
    }

    /**
     *
     * @param engine
     * @param data
     * @param debugMode
     * @return the execution context
     */
    public static ExecutionContext build(MustacheEngine engine, Object data,
            boolean debugMode) {

        ExecutionContext context = null;

        if (debugMode) {
            context = new DebugExecutionContext(engine.getConfiguration());
        } else {
            context = new DefaultExecutionContext(engine.getConfiguration());
        }

        if (engine.getConfiguration().getGlobalData() != null) {
            context.push(CONTEXT, engine.getConfiguration().getGlobalData());
        }
        if (data != null) {
            context.push(CONTEXT, data);
        }
        return context;
    }

}
