/*
 * Copyright 2019-2020 The Polypheny Project
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
 *
 * This file incorporates code covered by the following terms:
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.polypheny.db.sql.type;


import org.polypheny.db.rel.type.RelDataType;
import org.polypheny.db.sql.SqlOperatorBinding;
import com.google.common.collect.ImmutableList;


/**
 * Strategy to infer the type of an operator call from the type of the operands by using a series of {@link SqlReturnTypeInference} rules in a given order.
 * If a rule fails to find a return type (by returning NULL), next rule is tried until there are no more rules in which case NULL will be returned.
 */
public class SqlReturnTypeInferenceChain implements SqlReturnTypeInference {

    private final ImmutableList<SqlReturnTypeInference> rules;


    /**
     * Creates a SqlReturnTypeInferenceChain from an array of rules.
     *
     * Package-protected.
     * Use {@link ReturnTypes#chain}.</p>
     */
    SqlReturnTypeInferenceChain( SqlReturnTypeInference... rules ) {
        assert rules != null;
        assert rules.length > 1;
        for ( SqlReturnTypeInference rule : rules ) {
            assert rule != null;
        }
        this.rules = ImmutableList.copyOf( rules );
    }


    @Override
    public RelDataType inferReturnType( SqlOperatorBinding opBinding ) {
        for ( SqlReturnTypeInference rule : rules ) {
            RelDataType ret = rule.inferReturnType( opBinding );
            if ( ret != null ) {
                return ret;
            }
        }
        return null;
    }
}
