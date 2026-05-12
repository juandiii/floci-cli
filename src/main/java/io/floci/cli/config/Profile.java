package io.floci.cli.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
    public String name;
    public String endpoint;
    public String container;
    public String image;
    public Integer port;
    public String persistDir;
    public String services;
    public String output;

    public Profile() {}

    public Profile(String name) {
        this.name = name;
        this.endpoint = "http://localhost:4566";
        this.container = "floci";
    }
}
