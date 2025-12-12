import Keycloak from 'keycloak-js';

export const keycloak = new Keycloak({
  url: 'https://localhost:8443/',
  realm: 'hr-app',
  clientId: 'angular-spa'
});

export function initializeKeycloak() {
  return async () => {
    await keycloak.init({
      onLoad: 'login-required',
      checkLoginIframe: false
    });
    //const roles = keycloak.realmAccess?.roles || [];

    // if (roles.includes('manager')) {
    //   globalThis.location.replace('/profiles');
    // } else if (roles.includes('employee')) {
    //   globalThis.location.replace('/profile');
    //   //todo add profile-view component
    // }
  };
}

export function getUsername(): string  {
  return keycloak.tokenParsed?.['preferred_username'];
}

export function isManager(): boolean {
  const roles = keycloak.tokenParsed?.['realm_access']?.roles || [];
  return roles.includes('ROLE_MANAGER');
}
