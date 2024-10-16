import { ProductService } from './../services/product.service';
import { Component } from '@angular/core';
import { Product } from '../services/product.service';

@Component({
  selector: 'app-myproducts',
  templateUrl: './myproducts.component.html',
  styleUrl: './myproducts.component.css'
})
export class MyproductsComponent {

  products: Product[] = [];
  array: number[] = [];

  constructor(private productService: ProductService){}

  ngOnInit(): void {
    this.productService.getSellerProductList()
    .subscribe((products: Product[]) =>{
      this.products = products;
      this.array = new Array(products.length);
      for(let i = 0; i < this.array.length; i++){
        this.array[i] = 0;
      }
    })
  }

  modifyQuantity(product: Product, newQuantity: number){
    let mProduct = {id: product.id, quantity: newQuantity} as Product;
    return this.productService.modifyQuantity(mProduct).subscribe({
      next: (request:any) =>{
        location.reload();
      }
    })
  }

}
