package com.second.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.second.backend.dto.ProductSellerDTO;
import com.second.backend.model.Product;
import com.second.backend.model.ProductSizes;
import com.second.backend.model.Users;
import com.second.backend.repository.ProductRepository;
import com.second.backend.repository.ProductSizesRepository;
import com.second.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSellerService {
    private final ProductRepository productRepository;
    private final ProductSizesRepository productSizesRepository;
    private final UsersRepository usersRepository;
    private final CommonService commonService;

    //1.판매자는 자신이 현재 판매중인 전체 물품 조회
    public List<ProductSellerDTO> findProductsBySellerId(Integer sellerId) {
        //step1. sellerid와 연결된 Product id를 Product에서 찾습니다.
        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = productRepository.findByUsersId(sellerId, pageable);


        //step2.Product id를 리스트 형태로 수집합니다.
        List<Integer> productIds =  productPage.stream()
                .map(Product::getId)
                .toList();

        //step3.리스트 형태의 Product id들과 일치하는 값을 ProductSizes에서 찾습니다.
        List<ProductSizes> sizesStock = productSizesRepository.findByProductIdIn(productIds);

        //step4.ProductSizes에서 찾은 Size와 Stock을  Product id기준으로 매핑합니다.
        Map<Integer, List<ProductSizes>> productSizesMap = sizesStock.stream()
                .collect(Collectors.groupingBy(ps -> ps.getProduct().getId()));

        //step4. Product와 sizesStock를 결합하여 DTO/ProductSellerResponse로 변환합니다.
        return  productPage.stream()
                .map(product -> {
                    List<ProductSizes> sizesForProduct = productSizesMap.getOrDefault(product.getId(), List.of());
                    // 사이즈와 재고 정보를 맵으로 변환
                    Map<String, Integer> sizesStockMap = sizesForProduct.stream()
                            .collect(Collectors.toMap(ProductSizes::getSize, ProductSizes::getStock));

                    return ProductSellerDTO.builder()
                            .productid(product.getId())
                            .sellerId(product.getUsers().getId())
                            .gender(product.getGender())
                            .kind(product.getKind())
                            .name(product.getName())
                            .color(product.getColor())
                            .fullname(product.getFullName())
                            .code(product.getCode())
                            .fileurl(product.getFileUrl())
                            .delisteddate(product.getDelistedDate())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .sizesStock(sizesStockMap) // 사이즈별 재고 정보 설정
                            .build();
                })
                .collect(Collectors.toList());
    }

    //2. 판매자는 자신이 판매할 물품 등록
    public Product saveProductWithSizes(Integer sellerId,MultipartFile file, ProductSellerDTO productSellerDTO) {

        Users seller;
        try {
            seller = usersRepository.findById(sellerId).orElseThrow();
        } catch (NoSuchElementException e) {
            // 사용자 객체가 존재하지 않으면 null을 반환합니다.
            return null;
        }
        String fileUrl = commonService.saveProductImage(file);

        // Product 엔티티를 생성합니다.
        Product product = Product.builder()
                .users(seller)
                .gender(productSellerDTO.getGender())
                .kind(productSellerDTO.getKind())
                .name(productSellerDTO.getName())
                .color(productSellerDTO.getColor())
                .fullName(productSellerDTO.getFullname())
                .code(productSellerDTO.getCode())
                .price(productSellerDTO.getPrice())
                .fileUrl(fileUrl)
                .description(productSellerDTO.getDescription())
                .DelistedDate(productSellerDTO.getDelisteddate())
                .listedDate(LocalDate.now()) // 현재 날짜를 listedDate에 설정합니다.
                .build();

        // Product를 저장합니다.
        Product savedProduct = productRepository.save(product);

        // 사이즈를 저장합니다.
        Map<String, Integer> sizesStock = productSellerDTO.getSizesStock();
        List<ProductSizes> sizes = sizesStock.entrySet().stream()
                .map(entry -> ProductSizes.builder()
                        .product(savedProduct)
                        .size(entry.getKey())
                        .stock(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        productSizesRepository.saveAll(sizes);
        return savedProduct;
    }

    //3. 판매자 자신이 등록한 물품 삭제
    @Transactional
    public String removeProductAndSizes(ProductSellerDTO productSellerDTO) {
        // Step 1:클라이언트로부터 전달받은 product ID값을 변수로 선언한다.
        Integer productid = productSellerDTO.getProductid();

        // Step 2: product에 product ID가 존재하는지 확인한다.
        if (productid == null || !productRepository.existsById(productid)) {
            return "물품 목록 중에 선택하신 물품이 존재하지 않습니다.";
        } else if (!productSizesRepository.existsByProductId(productid)) {
            // Step 3: ProductSizes에 product ID가 존재하는지 확인한다.
            return "ProductSizes 테이블에 해당 productid 값이 존재하지 않습니다.";
        } else {
            //Step 4:클라이언트로부터 전달받은 sizes,Stock값을 변수로 선언한다.
            Map<String, Integer> sizesStock = productSellerDTO.getSizesStock();

            // Step 5: ProductSizes에 size가 삭제되었는지 여부를 변수로 선언한다(초기:삭제안됨)
            boolean sizeExists = false;
            for (String size : sizesStock.keySet()) {
                List<ProductSizes> productSizes = productSizesRepository.findByProductIdAndSize(productid, size);
                if (!productSizes.isEmpty()) {
                    sizeExists = true;
                    break;
                }
            }

            if (!sizeExists) {
                return "ProductSizes 테이블에 해당 productid 값의 사이즈가 존재하지 않습니다.";
            } else {

                sizesStock.forEach((size, stock) -> {
                    List<ProductSizes> productSizes = productSizesRepository.findByProductIdAndSize(productid, size);
                    productSizesRepository.deleteAll(productSizes);
                });


                boolean productSizesExist = productSizesRepository.existsByProductId(productid);


                if (!productSizesExist) {
                    try {
                        productRepository.deleteById(productid);
                    } catch (EmptyResultDataAccessException e) {

                        return "선택하신 물품을 찾을 수 없습니다";
                    }
                    return "선택하신 물품의 사이즈가 삭제되었습니다.";
                } else {
                    return "선택하신 물품의 사이즈가 삭제되었습니다.";
                }
            }
        }



    }

    //4. 판매자가 등록된 물품 수정
    @Transactional
    public String updateProductAndSizes(ProductSellerDTO productSellerDTO) {
        Integer productid = productSellerDTO.getProductid();

        // Step 2: Check if the product exists

        Optional<Product> optionalProduct = productRepository.findById(productid);
        if (optionalProduct.isEmpty()) {
            return "물품목록 중에 선택하신 물품이 존재하지 않습니다.";
        }

        Product product = optionalProduct.get();

        // Update Product entity
        if (productSellerDTO.getGender() != null) product.setGender(productSellerDTO.getGender());
        if (productSellerDTO.getKind() != null) product.setKind(productSellerDTO.getKind());
        if (productSellerDTO.getName() != null) product.setName(productSellerDTO.getName());
        if (productSellerDTO.getColor() != null) product.setColor(productSellerDTO.getColor());
        if (productSellerDTO.getFullname() != null) product.setFullName(productSellerDTO.getFullname());
        if (productSellerDTO.getCode() != null) product.setCode(productSellerDTO.getCode());
        if (productSellerDTO.getFileurl() != null) product.setFileUrl(productSellerDTO.getFileurl());
        if (productSellerDTO.getDescription() != null) product.setDescription(productSellerDTO.getDescription());
        if (productSellerDTO.getPrice() != null) product.setPrice(productSellerDTO.getPrice());
        if (productSellerDTO.getDelisteddate() != null) product.setDelistedDate(productSellerDTO.getDelisteddate());

        productRepository.save(product);

        // Update ProductSizes entities
        productSizesRepository.deleteByProductId(productid);

        Map<String, Integer> sizesStock = productSellerDTO.getSizesStock();
        List<ProductSizes> sizes = sizesStock.entrySet().stream()
                .map(entry -> ProductSizes.builder()
                        .product(product)
                        .size(entry.getKey())
                        .stock(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        productSizesRepository.saveAll(sizes);

        return "물품과 사이즈가 성공적으로 업데이트되었습니다.";
    }
}
