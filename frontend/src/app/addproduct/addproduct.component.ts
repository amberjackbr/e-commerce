import { Component } from '@angular/core';
import { Product, ProductService } from '../services/product.service';

@Component({
  selector: 'app-addproduct',
  templateUrl: './addproduct.component.html',
  styleUrl: './addproduct.component.css'
})
export class AddproductComponent {

  name: string = '';
  description: string = '';
  price: number = 0;
  quantity: number = 0;



  constructor(private productService: ProductService){}

  add(){
    let product = {name: this.name, description: this.description, price: this.price, quantity: this.quantity} as Product;
    return this.productService.addProduct(product).subscribe({
      next: (request:any) =>{
        location.reload();
      }
    });
  }

}
