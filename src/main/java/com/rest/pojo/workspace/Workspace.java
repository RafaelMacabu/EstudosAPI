package com.rest.pojo.workspace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonIgnoreProperties(value = "id",allowSetters = true)
public class Workspace {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String id;
    private String name;
    private String type;
    private String description;

    public Workspace(){

    }

    public Workspace(String name,String type,String description){
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
