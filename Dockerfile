# floci/cli Docker image
# Usage: docker run --rm floci/cli:latest version
#
# The binary expects the Docker socket to be mounted if running lifecycle commands:
#   docker run --rm -v /var/run/docker.sock:/var/run/docker.sock floci/cli status

FROM scratch

ARG VERSION=dev
LABEL org.opencontainers.image.title="Floci CLI"
LABEL org.opencontainers.image.description="Official CLI for the Floci local AWS emulator"
LABEL org.opencontainers.image.version="${VERSION}"
LABEL org.opencontainers.image.source="https://github.com/floci-io/floci-cli"
LABEL org.opencontainers.image.licenses="MIT"

# Multi-arch: TARGETARCH is set by buildx (amd64 or arm64)
ARG TARGETARCH

COPY docker/${TARGETARCH}/floci-linux-${TARGETARCH} /floci

ENTRYPOINT ["/floci"]
