import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu/menu.component';
import { DepasserComponent } from './depasser/depasser.component';
import { ListComponent } from './list/list.component';
import { CreateComponent } from './create/create.component';

const routes: Routes = [
  { path: 'depasser', component: DepasserComponent },
  { path: 'list', component: ListComponent },
  { path: 'create', component: CreateComponent },

  { path: '', redirectTo:'menu', pathMatch: 'full' },
	{ path: 'menu', component: MenuComponent },

  { path: '**', redirectTo: 'menu', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
