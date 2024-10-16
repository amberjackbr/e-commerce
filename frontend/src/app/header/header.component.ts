import { Component, OnInit } from '@angular/core';
import { LoggedService } from '../services/logged.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  title = 'OnlineShop';
  logged: any = this.isLogged;

  /*OnInit(){
    this.logged =this.isLogged;
  }*/


  isLogged(){
    return LoggedService.isLogged();
  }

  isBuyerLogged(): boolean{
    return localStorage.getItem('is_buyer') == 'true';
  }

  isSellerLogged(): boolean{
    return localStorage.getItem('is_seller') == 'true';
  }

}
