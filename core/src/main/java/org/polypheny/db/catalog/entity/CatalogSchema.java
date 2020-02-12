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
 */

package org.polypheny.db.catalog.entity;


import org.polypheny.db.catalog.Catalog.SchemaType;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 *
 */
@EqualsAndHashCode
public final class CatalogSchema implements CatalogEntity {

    private static final long serialVersionUID = 6130781950959616712L;

    public final long id;
    public final String name;
    public final long databaseId;
    public final String databaseName;
    public final int ownerId;
    public final String ownerName;
    public final SchemaType schemaType;


    public CatalogSchema(
            final long id,
            @NonNull final String name,
            final long databaseId,
            @NonNull final String databaseName,
            final int ownerId,
            @NonNull final String ownerName,
            @NonNull final SchemaType schemaType ) {
        this.id = id;
        this.name = name;
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.schemaType = schemaType;
    }


    // Used for creating ResultSets
    @Override
    public Serializable[] getParameterArray() {
        return new Serializable[]{ name, databaseName, ownerName, CatalogEntity.getEnumNameOrNull( schemaType ) };
    }


    @RequiredArgsConstructor
    public class PrimitiveCatalogSchema {

        public final String tableSchem;
        public final String tableCatalog;
        public final String owner;
        public final String schemaType;
    }
}