import { inject } from '@angular/core';
import { Router, CanActivateFn, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {getUsername, keycloak} from './keycloak-init';


export const authGuard: CanActivateFn = async ( route: ActivatedRouteSnapshot,
                                             state: RouterStateSnapshot) => {
  if (!keycloak.authenticated) {
    await keycloak.login({redirectUri: globalThis.location.origin + state.url});
    return false; // wait until login completes
  }

  return true;
};
