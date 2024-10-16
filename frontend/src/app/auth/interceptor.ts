import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    var token = localStorage.getItem("access_token");
    console.log('intercettato');

    /*if ( token == null) {
      console.log('notokenprova');
      return next.handle(request);
    }*/

    /*const authorizedRequest = request.clone({headers: request.headers.set('Authorization', `Bearer ${token}`),});
    console.log('prova');*/

    return next.handle(request);
  }


}
