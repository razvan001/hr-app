import {environment} from "../../environments/environment";
import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class ApiPrefixInterceptor implements HttpInterceptor {
  readonly headers = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set('Accept', ['application/json']);

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // assured that this interceptor does not prefix requests to our assets folder with our server url
    if (!/^(http|https|blob):/i.test(request.url) && !/^assets\//.test(request.url)) {
      request = request.clone({ url: environment.apiBaseUrl + request.url });
    }
    return next.handle(request);
  }
}
