import { ProductService, ProductToBuy } from './../services/product.service';
import { Buyer, BuyerService } from './../services/buyer.service';
import { Component } from '@angular/core';
import { Product } from '../services/product.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  products: Product[] = [];
  productsToBuy: Product[] = [];
  //selectedProduct?: Product;
  private apiurl = 'localhost:8080/'
  array: number[] = [];


  constructor(private buyerService: BuyerService, private productservice: ProductService){}

  ngOnInit(): void {
    this.buyerService.getCart()
    .subscribe((products: Product[]) =>{
      this.products = products;
      this.array = new Array(products.length);
      for(let i = 0; i < this.array.length; i++){
        this.array[i] = 0;
      }
    })
  }

  public metodo(){
    for(let i = 0; i < this.array?.length; i++){
      console.log(this.array[i]);
    }
  }

  removeFromCart(id: number){
    this.productservice.removeFromCart(id).subscribe({
      next: (request:any) =>{
        location.reload();
      }
    });
  }

  buy(){
    let b = {} as Buyer;
    let list: ProductToBuy[] = new Array();
    for(let i = 0; i < this.products.length; i++){
      if(this.array[i] > 0){
        let newProductToBuy: Product = this.products[i];
        let newProductToBuyWithQt: ProductToBuy = {quantity: this.array[i], product: newProductToBuy};
        list.push(newProductToBuyWithQt);
      }
    }
    //console.log(list);
    return this.productservice.buy(list).subscribe({
      next: (request:any) =>{
        location.reload();
      }
    });
  }

}
