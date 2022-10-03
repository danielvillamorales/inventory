package com.company.inventory.inventory.services;

import com.company.inventory.inventory.config.Util;
import com.company.inventory.inventory.model.entity.Category;
import com.company.inventory.inventory.model.entity.Product;
import com.company.inventory.inventory.model.repository.CategoryDao;
import com.company.inventory.inventory.model.repository.ProductoDao;
import com.company.inventory.inventory.response.ProductResponseRest;
import com.company.inventory.inventory.web.dto.CategoryDto;
import com.company.inventory.inventory.web.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private static final String ERROR_CONSULTA = "error al consultar por id ";
    private static final String NOK = "respuesta nok";

    private CategoryDao categoryDao;

    private ProductoDao productoDao;
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<ProductResponseRest> save(ProductDto productDto, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<ProductDto> list = new ArrayList<>();
        try {
            Optional<Category> category = categoryDao.findById(categoryId);
            // buscar categoria
            if (category.isPresent()) {
                productDto.setCategory(modelMapper.map(category.get(), CategoryDto.class));

            } else {
                response.setMetadata("Respuesta nok", "-1", "categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            // save the product
            ProductDto productoDtoSave = modelMapper.map(productoDao.save(modelMapper.map(productDto, Product.class)),
                    ProductDto.class);
            if (productoDtoSave != null) {
                list.add(productoDtoSave);
                response.getProductResponse().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "producto guardado");
            } else {
                response.setMetadata("Respuesta nok", "-1", "no se pudo guardar");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "error al guardar producto: " + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductResponseRest> findOne(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<ProductDto> list = new ArrayList<>();
        try {
            ProductDto productDto = productoDao.findById(id)
                    .map(p -> modelMapper.map(p, ProductDto.class))
                    .orElseThrow(() -> new RuntimeException("error al consultar producto"));
            productDto.setPicture(Util.decompressZLib(productDto.getPicture()));
            list.add(productDto);
            response.getProductResponse().setProducts(list);
            response.setMetadata("Respuesta ok", "00", "producto encontrado");
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "error: " + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> findName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> productos = productoDao.findByNameContainingIgnoreCase(name);
        if (productos.isEmpty()) {
            response.setMetadata("Respuesta nok", "-1", "no se encontraron coincidencias");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            response.getProductResponse().setProducts(
                    productos.stream().map(p -> {
                                        p.setPicture(Util.decompressZLib(p.getPicture()));
                                        return modelMapper.map(p, ProductDto.class);
                                    }
                            )
                            .collect(Collectors.toList()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> delete(Long id) {
        ProductResponseRest response = new ProductResponseRest();

        try {
            if (productoDao.findById(id).isPresent()) {
                productoDao.deleteById(id);
                response.setMetadata("OK", "00", "categoria eliminada");
            } else {
                response.setMetadata(NOK, "-1", "categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (
                Exception e) {
            response.setMetadata(NOK, "-1", ERROR_CONSULTA);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> findAll() {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> productos = productoDao.findAll();
        if (productos.isEmpty()) {
            response.setMetadata("Respuesta nok", "-1", "no se encontraron coincidencias");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            response.getProductResponse().setProducts(
                    productos.stream().map(p -> {
                                        p.setPicture(Util.decompressZLib(p.getPicture()));
                                        return modelMapper.map(p, ProductDto.class);
                                    }
                            )
                            .collect(Collectors.toList()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponseRest> updateOne(ProductDto productDto, Long id, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<ProductDto> list = new ArrayList<>();
        try {
            Optional<Category> category = categoryDao.findById(categoryId);
            // buscar categoria
            if (category.isPresent()) {
                productDto.setCategory(modelMapper.map(category.get(), CategoryDto.class));

            } else {
                response.setMetadata("Respuesta nok", "-1", "categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            productDto.setId(id);
            // save the product
            if (productoDao.findById(id).isPresent()) {
                ProductDto productoDtoSave = modelMapper.map(productoDao.save(modelMapper.map(productDto, Product.class)),
                        ProductDto.class);
                if (productoDtoSave != null) {
                    list.add(productoDtoSave);
                    response.getProductResponse().setProducts(list);
                    response.setMetadata("Respuesta ok", "00", "producto actulizado");
                } else {
                    response.setMetadata("Respuesta nok", "-1", "no se pudo actulizar");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                response.setMetadata("Respuesta nok", "-1", "no se encuentra el producto");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "error al actualizar producto: " + e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

