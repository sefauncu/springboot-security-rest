# **Spring Boot, Spring Security ve REST API Kullanımı**
- # Customer API servisininin uç metotlarına erişim Spring Security ile kısıtlanması.
 
> **ADIM 1 -> Spring Security Olmadan Servis Metotlarına Erişim**
> 
Öncelikle controller için hiyerarşi mantığında controller paketimi oluşturuyorum. Sonrasında rest controller sınıfımı tanımlayıp @Controller annotation’ı class düzeyde ekliyorum. @RequestMapping yani benzersiz bir url tanımı için hem class hem metot seviyesinde bu annotation’ı kullanıyorum. @Autowired ile service beanimi buraya inject ediyorum. Id ile işlem yaptığım metotlarımda yani DELETE ve GETID metolarım burada @PathVariable kullanarak id alma işlemini gerçekleştiriyorum.

**Olmayan bir ID için exception fırlatması içinde gerekli throw blogunuda ekledim.**

- Verileri aldığım zaman @GetMapping
- Veri kaydettiğim zaman @PostMapping
- Veri güncellediğim zaman @PutMapping
- Veri sildiğim zaman @DeleteMapping kullanıyorum.

```javascript
@RestController
@RequestMapping("/api")
public class CustomerRestController {
 
	@Autowired
	private CustomerService customerService;
	
 
	@GetMapping("/customerAll")
	public List<Customer> getCustomers() {
 
		return customerService.getCustomers();
 
	}	
		
	@GetMapping("/customerGet/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
 
		Customer theCustomer = customerService.getCustomer(customerId);
 
		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
 
		return theCustomer;
	}
```

```javascript
 @DeleteMapping("/customerDelete/{customerId}")
	public void deleteCustomer(@PathVariable int customerId) {
		
		customerService.deleteCustomer(customerId);
	}
	
	
	@PostMapping("/customerPost")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
 
		// aynı zamanda JSON'da bir kimliği geçmesi durumunda ... kimliği 0
		// olarak ayarla
		// bu, güncelleme yerine yeni öğenin kaydedilmesini zorlar.
 
		theCustomer.setId(0);
 
		customerService.saveCustomer(theCustomer);
 
		return theCustomer;
	}
	
 
	@PutMapping("/customerPut")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
 
		customerService.saveCustomer(theCustomer);
 
		return theCustomer;
 
	}
```
> Son olarak projemizi POSTMAN uygulamasında test edip çıktılarına göz atalım.

![REST](https://cdn-images-1.medium.com/max/800/1*CxXqO_AMpskHsMLlH1aspQ.png "REST")

http://locahost:8080/api/customers adresimize gidiyorum. JSON formatında datamızı ekleyip send butonuna bastıktan sonra aşağıda vertiabanına kaydımızın eklendiğini görmüş olacağız. Diğer metotlarıda aynı şekilde deneyebilirsiniz.

> **ADIM 1 -> Spring Security Devredeyken Servis Metotlarına Erişim**

Burada önemli nokta spring security devreye girip hangi url’e hangi role sahip kullanıcının gideceği ayarlamalar mevcut.

Eğer kullanıcı admin ile giriş yapmış ise tüm yetkilere sahiptir. Fakat user ile giriş yapmış ise ekleme slime ve gücelleme işlemlerini yapamaz. Fakat customer listesini görebilmektedir.

>Önce admin ile giriş yapalım;

![REST](https://cdn-images-1.medium.com/max/800/1*4ZW0rkxX3gCDc6ZQfty_nQ.png "REST")

> Sonra databasedeki tüm kullanıcılara erişelim ve sonrasında parametre göndererek erişmeye çalışalım. Sonuçları her ikisinde de alacağımızı söyleyebilirim.

![REST](https://cdn-images-1.medium.com/max/800/1*OvtLs5zx2ZQxbbR98FjaZg.png "REST")


![REST](https://cdn-images-1.medium.com/max/800/1*WIH74UsG-zjQBvUtgmIarw.png "REST")

> Şimdi ise user ile sisteme giriş yapalım;

![REST](https://cdn-images-1.medium.com/max/800/1*J5dpHxHnyuHE5GkPqDM2pQ.png "REST")

> Tüm kullanıcılara erişebilme yetkisi vardır. Fakat parametre göndererek deneyelim ve sonuca bakalım.
>
![REST](https://cdn-images-1.medium.com/max/800/1*FQVpi9-amZDNkrAoj6eYrA.png "REST")