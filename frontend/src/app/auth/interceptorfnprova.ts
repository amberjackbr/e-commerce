import { HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";



export const authenticationInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next:
  HttpHandlerFn) => {
    //console.log('intercepted');
     const token = localStorage.getItem('access_token');
     if(token == null){//request does not get modified
      //console.log('notoken');
      return next(req);
     }
      const modifiedReq = req.clone({
       headers: req.headers.set('Authorization', `Bearer ${token}`),
     });
     //console.log('gottoken');

     return next(modifiedReq);
  };
