package buildingblocks.keycloak;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.*;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // 1. Resource Roles (from resource_access)
        extractResourceRoles(jwt, authorities);

        // 2. Realm Roles (from realm_access)
        extractRealmRoles(jwt, authorities);

        // 3. Groups (from groups claim if present)
        extractGroups(jwt, authorities);

        return authorities;
    }

    private void extractResourceRoles(Jwt jwt, Set<GrantedAuthority> authorities) {
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null) {
            resourceAccess.values().forEach(clientAccess -> {
                if (clientAccess instanceof Map) {
                    Map<String, Object> clientAccessMap = (Map<String, Object>) clientAccess;
                    Object rolesObj = clientAccessMap.get("roles");

                    if (rolesObj instanceof Collection) {
                        Collection<String> roles = (Collection<String>) rolesObj;
                        roles.forEach(role -> {
                            authorities.add(new SimpleGrantedAuthority(role));
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                        });
                    }
                }
            });
        }
    }

    private void extractRealmRoles(Jwt jwt, Set<GrantedAuthority> authorities) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            Collection<String> realmRoles = (Collection<String>) realmAccess.get("roles");
            realmRoles.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("REALM_" + role));
                authorities.add(new SimpleGrantedAuthority(role));
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            });
        }
    }

    private void extractGroups(Jwt jwt, Set<GrantedAuthority> authorities) {
        Object groupsObject = jwt.getClaim("groups");
        if (groupsObject instanceof Collection) {
            Collection<String> groups = (Collection<String>) groupsObject;
            groups.forEach(group -> {
                // Remove leading '/' if present
                String groupName = group.startsWith("/") ? group.substring(1) : group;
                authorities.add(new SimpleGrantedAuthority("GROUP_" + groupName));
                authorities.add(new SimpleGrantedAuthority(groupName));
            });
        }
    }
}

