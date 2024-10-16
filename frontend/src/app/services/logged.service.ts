import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoggedService {

  constructor() { }

  static isLogged(){
    var token = localStorage.getItem('access_token');
    if(token == null){return false;}
    return true;
  }
}
