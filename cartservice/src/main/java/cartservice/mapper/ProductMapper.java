package cartservice.mapper;

import cartservice.dtos.ProductResponseDto;
import cartservice.entity.Products;
import cartservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    private static ProductRepository productRepository;

    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static Products fromProductResponseDto(ProductResponseDto dto){
        Products products=new Products();

        products.setId(dto.getId());
        products.setName(dto.getName());
        products.setPrice(dto.getPrice());
        products.setImage(dto.getImage());
        products.setDescription(dto.getDescription());
        products.setStock(dto.getStock());
        products.setBrand(dto.getBrand());
//        productRepository.save(products);
        return products;
    }
}
