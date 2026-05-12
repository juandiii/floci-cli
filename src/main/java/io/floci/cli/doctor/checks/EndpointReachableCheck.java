package io.floci.cli.doctor.checks;

import io.floci.cli.doctor.Check;
import io.floci.cli.doctor.CheckResult;
import io.floci.cli.http.FlociHttpClient;

public class EndpointReachableCheck implements Check {

    @Override
    public CheckResult run(String endpoint, String container) {
        FlociHttpClient client = new FlociHttpClient(endpoint);
        if (client.isReachable()) {
            try {
                var health = client.health();
                return CheckResult.ok("endpoint.reachable",
                        endpoint + " is reachable (server v" + health.version() + ")");
            } catch (Exception e) {
                return CheckResult.ok("endpoint.reachable", endpoint + " is reachable");
            }
        }
        return CheckResult.fail("endpoint.reachable",
                "GET /_floci/health at " + endpoint + " did not return 200",
                "Is Floci running? Try 'floci start'.");
    }
}
