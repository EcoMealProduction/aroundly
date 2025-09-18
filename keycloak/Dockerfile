FROM quay.io/keycloak/keycloak:23.0.7

# ENV KC_FEATURES=token-exchange
# ENV KC_BOOTSTRAP_ADMIN_PASSWORD_FILE=/run/secrets/kc-admin-pass

# COPY realm.json /opt/keycloak/data/import/realm.json
ENV KC_FEATURES=token-exchange
ENV KC_CACHE=local
ENV KC_DB=postgres
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start", "--optimized", "--hostname-strict=false", "--http-enabled=true", "--http-port=8080"]
