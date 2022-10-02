package com.rajputLife.controller;

import com.rajputLife.model.Product;
import com.rajputLife.repository.ProductRepository;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Scope(value = "session")
@Component(value = "singleProductController")
@ELBeanName(value = "singleProductController")
//http://localhost:8080/single?value=2 will work
@Join(path = "/single", to = "/product/product.jsf")
public class SingleProductController {

	private ProductRepository productRepository;

	@Inject
	public SingleProductController(ProductRepository productRepository){
		this.productRepository = productRepository;
	}

//	@PathPattern("//W{1}")
	@Parameter
	@Deferred
	private String value;

	private Product product;

	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		product = productRepository.findById((long) Integer.parseInt(value)).get();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		System.out.println(origRequest.getQueryString());
		this.value = value;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
