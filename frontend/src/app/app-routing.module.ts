import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProvaComponent } from './prova/prova.component';
import { HomeComponent } from './home/home.component';
import { ProductdetailsComponent } from './productdetails/productdetails.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ProductlistComponent } from './productlist/productlist.component';
import { LoginComponent } from './login/login.component';
import { CartComponent } from './cart/cart.component';
import { MypurchasesComponent } from './mypurchases/mypurchases.component';
import { MyproductsComponent } from './myproducts/myproducts.component';
import { AddproductComponent } from './addproduct/addproduct.component';

const routes: Routes = [
  {path: 'prova', component: ProvaComponent},
  {path: '', component: HomeComponent},
  {path: 'productdetails/:productId', component: ProductdetailsComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'productlist', component: ProductlistComponent},
  {path: 'login', component: LoginComponent},
  {path: 'cart', component: CartComponent},
  {path: 'mypurchases', component: MypurchasesComponent},
  {path: 'myproducts', component: MyproductsComponent},
  {path: 'addproduct', component: AddproductComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
