package ua.lady.scm.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.lady.scm.domain.Product;

@FeignClient(name = "product-client", url = "${origindb.endpoint.base_url}")
public interface OriginProductRestClient {
    @RequestMapping(method = RequestMethod.GET, value = "/products")
    PagedResources<Product> findAll(@RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size);

    @RequestMapping(method = RequestMethod.GET, path = "/products/search/latest")
    Resources<Product> findAllByIdGreaterThan(@RequestParam("lastId") int id);
}
