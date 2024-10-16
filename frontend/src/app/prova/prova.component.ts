import { Component } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-prova',
  templateUrl: './prova.component.html',
  styleUrl: './prova.component.css'
})
export class ProvaComponent {

  constructor(
    private http : HttpClient
  ){}

  email: any = '';
  password: any = '';
  islogged: any = '';


  login(username: string, password: string) {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password')
      .set('client_id', 'store-angular')
      .set('client_secret', 'JieiaA6uu7hf8tqjicZiMRmBzwbUBJZo');

    this.http.post('http://localhost:8081/realms/onlineShop/protocol/openid-connect/token', body.toString(), {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded',)
    }).subscribe({
      next: (response: any) =>{
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('refresh_token', response.refresh_token);
      }
    });
    this.email = localStorage.getItem('access_token');
    this.password = localStorage.getItem('refresh_token');
    return
  }


  logout() {
    const refresh_token = localStorage.getItem('refresh_token');
    const body = new HttpParams()
      .set('client_id', 'store-angular')
      .set('client_secret', 'JieiaA6uu7hf8tqjicZiMRmBzwbUBJZo')
      .set('refresh_token', refresh_token as string);

    this.http.post('http://localhost:8081/realms/onlineShop/protocol/openid-connect/logout', body.toString(), {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded',)
    }).subscribe({
      next: value =>{
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        localStorage.removeItem('is_buyer');
        localStorage.removeItem('is_seller');
      }
    });
    this.email = localStorage.getItem('access_token');
    this.password = localStorage.getItem('refresh_token');
    return
  }


  getUser(){
    this.http.get('http://localhost:8080/buyer/13').subscribe({
      next: (response: any) => {
        this.email = response;
      }
    })
    return;
  }

}
