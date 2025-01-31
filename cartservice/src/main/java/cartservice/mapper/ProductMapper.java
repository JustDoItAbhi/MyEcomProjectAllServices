package cartservice.mapper;

import cartservice.client.dto.ProductResponseDto;
import cartservice.entity.Products;
import cartservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {// product entity to response dto convertion

    public static Products fromProductResponseDto(ProductResponseDto dto){
        Products products=new Products();
        products.setId(dto.getId());
        products.setName(dto.getName());
        products.setPrice(dto.getPrice());
        products.setImage(dto.getImage());
        products.setDescription(dto.getDescription());
        products.setStock(dto.getStock());
        products.setBrand(dto.getBrand());
        return products;
    }
}
