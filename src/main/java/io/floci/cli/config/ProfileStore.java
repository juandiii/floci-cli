package io.floci.cli.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileStore {

    private static final ObjectMapper YAML = new ObjectMapper(new YAMLFactory());

    private final Path profilesDir;

    public ProfileStore() {
        this(Path.of(System.getProperty("user.home"), ".floci", "profiles"));
    }

    public ProfileStore(Path profilesDir) {
        this.profilesDir = profilesDir;
    }

    public List<Profile> list() throws IOException {
        if (!Files.exists(profilesDir)) return List.of();
        List<Profile> profiles = new ArrayList<>();
        try (var stream = Files.list(profilesDir)) {
            stream.filter(p -> p.toString().endsWith(".yaml") || p.toString().endsWith(".yml"))
                    .forEach(p -> {
                        try {
                            Profile profile = YAML.readValue(p.toFile(), Profile.class);
                            profiles.add(profile);
                        } catch (IOException ignored) {}
                    });
        }
        return profiles;
    }

    public Optional<Profile> get(String name) throws IOException {
        Path file = profileFile(name);
        if (!Files.exists(file)) return Optional.empty();
        return Optional.of(YAML.readValue(file.toFile(), Profile.class));
    }

    public void save(Profile profile) throws IOException {
        Files.createDirectories(profilesDir);
        YAML.writeValue(profileFile(profile.name).toFile(), profile);
    }

    public boolean delete(String name) throws IOException {
        Path file = profileFile(name);
        return Files.deleteIfExists(file);
    }

    public Path profileFile(String name) {
        return profilesDir.resolve(name + ".yaml");
    }
}
