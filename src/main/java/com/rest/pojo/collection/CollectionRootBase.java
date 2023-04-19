package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CollectionRootBase {
    CollectionBase collection;

    public CollectionRootBase(){

    }

    public CollectionRootBase(CollectionBase collection) {
        this.collection = collection;
    }

    public CollectionBase getCollection() {
        return collection;
    }

    public void setCollection(CollectionBase collection) {
        this.collection = collection;
    }

}
