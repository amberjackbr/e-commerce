import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from './product.service';
import { Observable } from 'rxjs';


export interface Buyer{
  id: number;
  firstname: string;
  lastname: string;
  telephonenumber: string;
  email: string;
}

export interface Purchase{
  id: number;
  purchaseTime: number;
  buyer: Buyer;
  productList: ProductList[];
}

export interface ProductList{
  quantity: number;
  product: Product;
}

@Injectable({
  providedIn: 'root'
})
export class BuyerService {


  constructor(private http: HttpClient) { }


  getCart(): Observable<Product[]>{
    return this.http.get<Product[]>('http://localhost:8080/buyer/cart');
  }


  getPurchases(): Observable<Purchase[]>{
    return this.http.get<Purchase[]>('http://localhost:8080/buyer/mypurchases');
  }




}
