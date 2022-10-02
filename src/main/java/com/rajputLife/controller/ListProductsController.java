package com.rajputLife.controller;

import com.rajputLife.model.Product;
import com.rajputLife.repository.ProductRepository;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Scope (value = "session")
@Component (value = "listProducts")
@ELBeanName(value = "listProducts")
@Join(path = "/list", to = "/product/product-list.jsf")
public class ListProductsController {

	private ProductRepository productRepository;

	@Inject
	public ListProductsController(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

//	@Inject
//	public ListProductsController(ProductRepository productRepository, List<Product> products){
//		this.productRepository = productRepository;
//		this.products = products;
//	}

	private List<Product> products;


	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		products = productRepository.findAll();
	}

	public List<Product> getProducts() {
		return products;
	}

	public String delete(Product product) {
		productRepository.delete(product);
		loadData();
		return null;
	}
}
