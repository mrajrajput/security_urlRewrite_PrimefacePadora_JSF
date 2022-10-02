### Primefaces-Pandora is added as template
```aidl
http://localhost:8080/account will take you to dashboard.xhtml page after login with google oauth
pom.xml is skinned a lot and lots of unwanted things are commented out.
'repository' directory contains the repository for primefaces-pandora.

don't remove folders under 'resources' folder
there are ton's of xhtml added in 'webapp' folder from primefaces-pandora
webapp/s/* and webapp/ns/* are there but don't work since they are not mapped to the right bean.

working urls:
http://localhost:8080 will take you to http://localhost:8800/account url with help from index.html. 
Other urls are: http://localhost:8080/single?value=2, http://localhost:8080/list, http://localhost:8080/product

For other URLs, you have to type manually
http://localhost:8080/s/account/index.jsf

for your working start replacing files under webapp/s/* folder and put your files related to your things.
```

<br/>


```aidl
"to" page will load when "path" is put in browser and pressed enter.
ElBeanName will be used in page(/product/product-list.jsf). 

@ELBeanName(value = "listProducts")
@Join(path = "/list", to = "/product/product-list.jsf")
```

### Basic Working Project with 
#### 1. SpringBoot, PrimeFaces, SpringSecurity, OCP URL ReWrite, Postgres
#### 2. login with username and password, and make sure that both username and password are there in DB along with Roles
#### 3. http://localhost:8080 will take you to http://localhost:8800/account url with help from index.html. Other urls are: http://localhost:8080/single?value=2, http://localhost:8080/list, http://localhost:8080/product

<br/>

#### Add this in command line during startup

```
spring-boot:run -f pom.xml
```
#### Add this for stopping at debug point
```
in VM option under 'Runner' configuration
-Dspring-boot.run.fork=false
```

<br/>

### pom.xml
````
This is good for Start/run of SpringBoot project -  Maven plugin

<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
````

```
Note: Primefaces-Pandora has it in-built, so we dont need to have jsf-imp included like mentioned below.

mojarra Implementation of JSF

<dependency>
    <groupId>com.sun.faces</groupId>
    <artifactId>jsf-api</artifactId>
    <version>2.2.12</version>
</dependency>
<dependency>
    <groupId>com.sun.faces</groupId>
    <artifactId>jsf-impl</artifactId>
    <version>2.2.12</version>
</dependency>

Note:
Following dependencies are coming as default with 'jsf-impl':
javax.servlet            » javax.servlet-api
javax.servlet.jsp        » javax.servlet.jsp-api
javax.servlet.jsp.jstl   » jstl-api

Note: 
Another imlpementation of JSF is myFaces,
which also needs JavaEE depenendency as well other than jsf-impl.
I decide to use Moharra now since regular jsf-impl is enough.
```

```
From above: javax.servlet » javax.servlet-api

It needs to be 'provided'(meaning we are adding externally on top of our server, which is tomcat)

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
```
<br/>
<br/>

### application.properties
```
#For Postgres vendor
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

#For Logs enable:
logging.config=classpath:logback.xml
logging.level.org.springframework.web=ALL

#Not sure what it does 
server.error.include-stacktrace=always

#This will show error on web page, and not show whitelable page.
server.error.whitelabel.enabled=false

#this fixed 1 null pointer during startup
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false

#I think this is printing logs twice for queries
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.show-sql=true

#Create DB first in PgAdmin as 'productOAuth0
spring.datasource.url=jdbc:postgresql://localhost:5432/productOAuth0?default_database=productOAuth0


```

### Follow this project for Joint 
/Users/Manjul/IdeaProjects/control-route-optimizer

# For URL Rewrite


```
ReWrite - annotation works as well.
This is not JSF related ReWrite but JavaEE related 

<dependency>
    <groupId>org.ocpsoft.rewrite</groupId>
    <artifactId>rewrite-servlet</artifactId>
    <version>${rewrite-servlet.version}</version>
</dependency>
<dependency>
    <groupId>org.ocpsoft.rewrite</groupId>
    <artifactId>rewrite-integration-faces</artifactId>
    <version>${rewrite-servlet.version}</version>
</dependency>
<dependency>
    <groupId>org.ocpsoft.rewrite</groupId>
    <artifactId>rewrite-config-prettyfaces</artifactId>
    <version>${rewrite-servlet.version}</version>
</dependency>
```
<br/>

### Information realted to ReWrite
```
@Join(path="/path", to="toJsf")
Query variable will have @Deferred and @Parameter
SetVariable(variable) will be called first.

@ElResolver, @Scope(""), @Component are required field.
Constructor injection is the only way for injection.
```


# For Spring Security


## What actually worked for me?
```
@PreAuthorize("hasAuthority('User')")
where 'User' is the role name in Role table as 'name' column.

Notes:
1) We don't have to add ==> .antMatchers("/list").hasAnyRole("User") <==
in configure method and only addition of role name without(ROLE_) as is
mentioned in DB in Role table.

	@RequestMapping(value = "/list")
	@PreAuthorize("hasAuthority('User')")
	public String viewHomePage(Model model) {
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);
		
		return "products";
	}
```

### You need at-least 1 role in Role table to start with.
```
You would need to run these queries - during table creation for fist time.

INSERT INTO role (pk_roles_id, name) VALUES (1, 'User');
INSERT INTO role (pk_roles_id, name) VALUES (2, 'Admin');

INSERT INTO public.users(pk_users_id, enabled, password, provider, username)
VALUES (2, true, 'Password123', 'form', 'test@gmail.com');
	
	
INSERT INTO public.users_roles(pk_users_roles, role_id, user_id)
VALUES (2, 2, 2);
```
## We need to configure
#### 1. AuthenticationManagerBuilder bean
#### 2. HttpSecurity bean
### 1. How to configure 'AuthenticationManagerBuilder'
```
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
}

authenticationProvider: can be DAO/LDAP/OAuth we are using DB
this provider needs 2 things:
1. UserDetailsService
2. PasswordEncoder


For 1)
We override these methods(a & b):

(a)CustomOAuth2UserService.java
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) { }
&& 
(b)UserDetailsServiceImpl.java
    @Override
    public UserDetails loadUserByUsername(String username) { }

&&
We have to load `getAuthorities()` using  UserDetailsServiceImpl for both.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { }

Note: 
For OAuth providers, we have to get information from OAuth first, and later
we can some information from DB(for example Roles), and than we can use that later 
in bean 'oauthUserService' which gets set in userService(here).

.oauth2Login()
.loginPage("/account")
.userInfoEndpoint()
.userService(oauthUserService)

```

### 2. How to configure HttpSecurity
```
@Override
protected void configure(HttpSecurity http) throws Exception { }

```


### Security
```
This project may not be enough
/Users/Manjul/Downloads/ProductManagerGoogleLogin 

```

# Errors
```aidl
OcpSoft: No qualifying bean of type:

Failed to handle PhaseOperation [org.ocpsoft.rewrite.faces.config.PhaseOperation$DeferredOperation@5604e199]
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: 
Error creating bean with name 'myloginController' defined in file [/Users/Manjul/IdeaProjects/security_urlRewrite_JSF/src/main/webapp/WEB-INF/classes/com/rajputLife/controller/MyLoginController.class]: 
Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.rajputLife.persistence.ProductRepository' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}

1. index.html contains "/logmein"
        <meta http-equiv="Refresh"
                  content="0; URL=/logmein"/>
                  
2. at src/main/webapp/registered/product-list.xhtml, we are referencing bean of "listProducts"
    <p:dataTable id="table" var="product" value="#{listProducts.products}">
    
3. MyLoginController.java
    @ELBeanName(value = "myloginController")
    @Join(path = "/logmein", to = "/registered/product-list.jsf")

4. webapp/registered/product-list.xhtml
    <p:dataTable id="table" var="product" value="#{listProducts.products}">

Info and reason for failure:
Contoller's path("/logmein") is typed in browser and it will take you to the toPath("/registered/product-list.jsf")
the bean name referenced in jsf file should match bean's name in controller's ElBeanName.

```
<br/>

```aidl
response.sendRedirect("/list") in OAuth showing Dependency bean issue

We needed to change "/account" from "/list" since http://localhost:8080 ==> index.html ==> to "/account"
==> thus Spring was told to take to "/account"(from index.html) and "/list"(from OAuth) and thus it was 
showing Spring dependency issues. 

Solution:
make it compatible with URL of index.html
```
<br/>

```aidl


```
