package com.rajputLife.controller;

import com.rajputLife.model.Product;
import com.rajputLife.repository.ProductRepository;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

//@Service
@Controller(value = "productController")
@Scope(value = "request")
@Component(value = "productController")
@ELBeanName(value = "productController")
@Join(path = "/product", to = "/product/product-form.jsf")
public class ProductController {

	private ProductRepository productRepository;

	@Inject
	public ProductController(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

	private Product product = new Product();

	public String save() {
		productRepository.save(product);
		product = new Product();
		return "/product/product-list.xhtml?faces-redirect=true";
	}

//	@RequestMapping(value = "/list")
//	@PreAuthorize("hasAuthority('User')")
	public String getSave() {
//		productRepository.save(product);
//		product = new Product();
		return "/product/product-list.xhtml?faces-redirect=true";
	}

	public String getLetsDoIt(){
		return "/logout";
	}


	public Product getProduct() {
		return product;
	}
}
