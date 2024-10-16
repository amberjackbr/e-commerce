import { Component } from '@angular/core';
import { BuyerService, ProductList, Purchase } from '../services/buyer.service';

@Component({
  selector: 'app-mypurchases',
  templateUrl: './mypurchases.component.html',
  styleUrl: './mypurchases.component.css'
})
export class MypurchasesComponent {

  purchases: Purchase[] = [];
  productList: ProductList[] = [];


  constructor(private buyerservice: BuyerService){}



  ngOnInit(): void {
    this.buyerservice.getPurchases().subscribe((purchases: Purchase[]) =>{
      this.purchases = purchases;
    })
  }



}
