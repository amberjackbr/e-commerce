import { Component } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(
    private http : HttpClient
  ){}

  email: any = '';
  password: any = '';
  islogged: any = '';
  isABuyer:any = '';
  isASeller:any = '';



  /*async login(username: string, password: string): Promise<any> {
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
        this.update();
      }
    });
    //this.email = localStorage.getItem('access_token');
    //this.password = localStorage.getItem('refresh_token');
    //if(isbuyer()){localStorage.setItem('is_buyer', 'true')};
    //if(isSeller()){localStorage.setItem('is_seller', 'true')};
    return
  }*/


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
        this.isABuyer = '';
        this.isASeller = '';
      }
    });
    //this.email = localStorage.getItem('access_token');
    //this.password = localStorage.getItem('refresh_token');
    this.email = '';
    this.password = '';
    return
  }

  /*async log(email: string, password: string){
    await this.login(email, password);
  }*/

  update(){
    this.email = localStorage.getItem('access_token');
    this.password = localStorage.getItem('refresh_token');
  }

  /*isBuyer():boolean{
    this.http.get('http://localhost:8080/buyer/myself').subscribe({
      next: (response: any) => {
        if(response.status == 200){
          return true;
        }
        return false;
      }
    })
    return true;
  }*/

  async log(email: string, password: string):Promise<any>{
    await this.login(email, password).then(
      r =>{
        return;
      },
      error => {
        return;
      }
    );
  }

  async login(username: string, password: string):Promise<void>{
    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password')
      .set('client_id', 'store-angular')
      .set('client_secret', 'JieiaA6uu7hf8tqjicZiMRmBzwbUBJZo');
    return new Promise((resolve, reject) => {
      this.http.post('http://localhost:8081/realms/onlineShop/protocol/openid-connect/token', body.toString(), {
        headers: new HttpHeaders()
          .set('Content-Type', 'application/x-www-form-urlencoded',)
      }).subscribe({
        next: async (response: any) =>{
          localStorage.setItem('access_token', response.access_token);
          localStorage.setItem('refresh_token', response.refresh_token);
          //this.email = localStorage.getItem('access_token');
          this.password = localStorage.getItem('refresh_token');
          await this.verifyBuyer().then(
            r =>{},
            error => {}
          );
          /*console.log('provapassaawait');
          if(this.isABuyer != null){
            console.log('provainsideiflogin');
            localStorage.setItem('is_buyer', 'true');
            this.email = localStorage.getItem('is_buyer');
          }*/
          /*this.verifySeller();
          if(this.isASeller != ''){
            localStorage.setItem('is_seller', 'true');
            this.email = localStorage.getItem('is_seller');
          }*/
          //this.email = this.isABuyer;
          resolve();
        },
        error: err => {
          reject(err);
        }
      });
    })
  }

  async isBuyer(): Promise<void>{
    return new Promise((resolve, reject)=>{
      this.http.get('http://localhost:8080/buyer/myself').subscribe({
        next: (response: any) =>{
          console.log('aprosibuyerok');
          this.isABuyer = 'true';
          console.log(this.isABuyer);
          localStorage.setItem('is_buyer', 'true');
          this.email = localStorage.getItem('is_buyer');
          console.log('chiudoisbuyerok');
        },
        error: err => {
          console.log('preavvioisseller');
          localStorage.removeItem('is_buyer');
          this.isABuyer = '';
          console.log('resettomodifichebuyer');
          this.isSeller();
          //reject(err);
        }
      });
    });
  }

  async verifyBuyer(): Promise<any>{
    await this.isBuyer().then(
      r =>{
        //this.isABuyer='true';
        //console.log('prova');
      },
      error => {
      }
    );
  }

  async verifySeller(): Promise<any>{
    await this.isSeller().then(
      r =>{},
      error => {}
    );
  }

  async isSeller(): Promise<void>{
    return new Promise((resolve, reject)=>{
      this.http.get('http://localhost:8080/seller/myself').subscribe({
        next: (response: any) =>{
          console.log('avvioisseller');
          localStorage.setItem('is_seller', 'true');
          this.password = localStorage.getItem('is_seller');
          this.email = localStorage.getItem('is_buyer');
          console.log('chiudoisseller');
        },
        error: err => {
          localStorage.removeItem('is_seller');
          this.isASeller = '';
          console.log('resettomodifichebuyer');
          reject(err);
        }
      });
    });
  }


  isLogged(){
    var token = localStorage.getItem('access_token');
    if(token == null){return false;}
    return true;
  }


}
