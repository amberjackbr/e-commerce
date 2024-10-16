import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface User{
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  password: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class SingUpService {

  constructor(http: HttpClient) {
    this.http = http;
  }

  http: HttpClient;


  requestSignUp(user: User){
    const jUser = JSON.stringify(user);
    console.log(jUser);
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    }
    //const prova = '?role=' + user.role;
    //const p2 = '?firstName='+user.firstName+'&lastName='+user.lastName+'&email='+user.email+'&phoneNumber='+user.phoneNumber+'&password='+user.password+'&role='+user.role
    //const body = new HttpParams()
    this.http.post('http://localhost:8080/keycloak/createbuyer', jUser, httpOptions).subscribe();
  }
}
