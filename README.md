# floci-cli

Official command-line interface for [Floci](https://floci.io) — the free, open-source local AWS emulator.

```
floci start
floci doctor
export AWS_ENDPOINT_URL=http://localhost:4566
aws s3 mb s3://my-bucket
```

## Installation

### Homebrew (macOS / Linux)

```sh
brew install floci-io/floci/floci
```

### Install script (Linux / macOS)

```sh
curl -fsSL https://floci.io/install.sh | sh
```

### Windows (PowerShell)

```powershell
iwr https://floci.io/install.ps1 | iex
```

### Scoop (Windows)

```powershell
scoop bucket add floci https://github.com/floci-io/scoop-floci
scoop install floci
```

### Docker

```sh
docker run --rm floci/cli:latest version
# With Docker socket for lifecycle commands:
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock floci/cli status
```

### JVM fallback

Download `floci.jar` from the [latest release](https://github.com/floci-io/floci-cli/releases/latest) and run:

```sh
java -jar floci.jar version
```

---

## Quick Start

```sh
# Start Floci
floci start

# Check environment
floci doctor

# Point the AWS CLI at Floci
export AWS_ENDPOINT_URL=http://localhost:4566

# Use AWS services normally
aws s3 mb s3://my-bucket
aws dynamodb create-table --table-name users \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST

# Stop Floci
floci stop
```

---

## Command Reference

| Command | Description |
|---------|-------------|
| `floci start` | Launch the Floci container |
| `floci stop` | Stop (and optionally remove) the container |
| `floci restart` | Stop then start |
| `floci status` | Show container state and server health |
| `floci logs` | Stream container logs |
| `floci wait` | Poll until Floci is ready (CI-friendly) |
| `floci version` | Show CLI and server versions |
| `floci services` | List enabled AWS services |
| `floci doctor` | Run environment diagnostics |
| `floci config show` | Show active configuration |
| `floci config validate` | Validate a docker-compose.yml |
| `floci config profile` | Manage named profiles |
| `floci snapshot save/load/list/delete` | Manage state snapshots |
| `floci completion bash\|zsh` | Generate shell completion |

All commands support `--help`. Global flags:

```
--endpoint <url>       Floci server URL  (default: http://localhost:4566, env: FLOCI_ENDPOINT)
--container <name>     Container name    (default: floci, env: FLOCI_CONTAINER)
--output text|json|yaml  Output format   (default: text)
--quiet, -q            Suppress non-error output
--verbose, -v          Debug logging to stderr
--no-color             Disable ANSI colors
```

---

## CI Usage

```sh
floci start --detach
floci wait --timeout 60s
pytest  # or your test command
floci stop --remove
```

With Docker Compose:

```yaml
services:
  floci:
    image: floci/floci:latest
    ports:
      - "4566:4566"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```

---

## Scope

`floci-cli` manages Floci's lifecycle, config, state, and diagnostics.
It does **not** wrap the AWS CLI or manage AWS resources.
Use `aws` with `AWS_ENDPOINT_URL=http://localhost:4566` for resource operations.

---

## License

MIT — see [LICENSE](LICENSE).
