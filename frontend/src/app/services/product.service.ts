import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Seller } from './seller.service';
//import { PRODUCTS } from '../mockProduct';
import { Buyer } from './buyer.service';

export interface Product{
  id: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
  seller: Seller;
}

export interface ProductToBuy{
  quantity: number;
  product: Product;
}

export interface BuyRequest{
  buyer: Buyer;
  productList: ProductToBuy[];
}




@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiurl = "http://localhost:8080/"

  products: Product[] = [];

  constructor(private http: HttpClient) {}


  getProducts():Observable<Product[]>{
    return of(this.products);
  }

  getMostSold(): Observable<Product[]>{
    return this.http.get<Product[]>(this.apiurl);
  }

  getBuyerInfo(list: ProductToBuy[]): Observable<Buyer>{
    return this.http.get<Buyer>(this.apiurl+"buyer/13");
  }

  getProductList(param: string){
    return this.http.get<Product[]>(this.apiurl+'products'+param);
  }

  getSellerProductList(){
    return this.http.get<Product[]>(this.apiurl+'seller/myproducts');
  }

  addToCart(id: number){
    //return this.http.post(this.apiurl+'buyer/addtocartnew2', {"id": id});
    //const pr: Product = new Product();
    let p ={id: id, price: 2} as Product;
    let pr = JSON.stringify(p);
    console.log(p);
    return this.http.post(this.apiurl+'buyer/addtocartnew2', pr, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json',)
        .set('Accept', 'application/json')
    });
  }

  removeFromCart(id: number){
    return this.http.post(this.apiurl+'buyer/removefromcartnew2', {"id": id});
  }

  buy(list: Array<ProductToBuy>){
    let b = {} as Buyer;
    let req = {buyer: b, productList: list} as BuyRequest
    let jReq = JSON.stringify(req);
    //console.log(jReq);
    return this.http.post(this.apiurl+'buyer/buy', jReq, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json',)
        .set('Accept', 'application/json')
    });
  }

  addProduct(product: Product){
    let jReq = JSON.stringify(product);
    //console.log(jReq);
    return this.http.post(this.apiurl+'seller/addproduct', jReq, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json',)
        .set('Accept', 'application/json')
    });
  }

  modifyQuantity(product: Product){
    let jReq = JSON.stringify(product);
    //console.log(jReq);
    return this.http.post(this.apiurl+'seller/setproductqt', jReq, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json',)
        .set('Accept', 'application/json')
    });
  }


}
