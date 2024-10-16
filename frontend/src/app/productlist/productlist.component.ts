import { Component } from '@angular/core';
import { Product, ProductService } from '../services/product.service';

@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrl: './productlist.component.css'
})
export class ProductlistComponent {

  products: Product[] = [];
  //selectedProduct?: Product;
  pageNumber: string = '0';
  pageSize: string = '1000';
  sortBy: string = 'name';

  constructor(private productService: ProductService){}

  ngOnInit(): void {
    this.productService.getProductList('?pagenumber=' + this.pageNumber + '&pagesize=' + this.pageSize + '&sortby=' + this.sortBy)
    .subscribe((products: Product[]) =>{
      this.products = products;
    })
  }

  isBuyerLogged(): boolean{
    return localStorage.getItem('is_buyer') == 'true';
  }

  addToCart(id: number){
    this.productService.addToCart(id).subscribe({
      next: (response:any) =>{
        console.log(response);
      }
    });
  }

}
