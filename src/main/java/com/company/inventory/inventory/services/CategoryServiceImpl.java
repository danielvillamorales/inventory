package com.company.inventory.inventory.services;

import com.company.inventory.inventory.model.entity.Category;
import com.company.inventory.inventory.model.repository.CategoryDao;
import com.company.inventory.inventory.response.CategoryReponseRest;
import com.company.inventory.inventory.web.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String ERROR_CONSULTA = "error al consultar por id ";
    private static final String NOK = "respuesta nok";

    private final CategoryDao categoryDao;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryReponseRest> search() {
        CategoryReponseRest response = new CategoryReponseRest();
        try {
            List<CategoryDto> categorias = categoryDao.findAll().stream()
                    .map(categoria -> modelMapper.map(categoria, CategoryDto.class))
                    .collect(Collectors.toList());
            response.getCategoryResponse().setCategoryDto(categorias);
            response.setMetadata("respuesta ok", "oo", "Respuesta Exitosa");
        } catch (Exception e) {
            response.setMetadata(NOK, "-1", "error al consultar");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryReponseRest> searchById(Long id) {
        CategoryReponseRest response = new CategoryReponseRest();

        try {
            Optional<Category> category = categoryDao.findById(id);
            if (category.isPresent()) {
                List<CategoryDto> lista = new ArrayList<>();
                lista.add(modelMapper.map(category.get(), CategoryDto.class));
                response.getCategoryResponse().setCategoryDto(lista);
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
    @Transactional
    public ResponseEntity<CategoryReponseRest> save(CategoryDto categoryDto) {
        CategoryReponseRest response = new CategoryReponseRest();
        List<CategoryDto> lista = new ArrayList<>();
        try {
            CategoryDto categoryDtoGuardada = modelMapper.map(
                    categoryDao.save(modelMapper.map(categoryDto, Category.class)), CategoryDto.class);
            if (categoryDtoGuardada != null) {
                lista.add(modelMapper.map(categoryDtoGuardada, CategoryDto.class));
                response.getCategoryResponse().setCategoryDto(lista);
            } else {
                response.setMetadata(NOK, "-1", "error al guardar categoria ");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata(NOK, "-1", ERROR_CONSULTA);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryReponseRest> update(Long id, CategoryDto categoryDto) {
        CategoryReponseRest response = new CategoryReponseRest();
        List<CategoryDto> lista = new ArrayList<>();
        try {
            Optional<Category> category = categoryDao.findById(id);
            if (category.isPresent()) {
                categoryDto.setId(id);
                CategoryDto categoryDtoGuardada = modelMapper.map(
                        categoryDao.save(modelMapper.map(categoryDto, Category.class)), CategoryDto.class);
                if (categoryDtoGuardada != null) {
                    lista.add(modelMapper.map(categoryDtoGuardada, CategoryDto.class));
                    response.getCategoryResponse().setCategoryDto(lista);
                } else {
                    response.setMetadata(NOK, "-1", "error al guardar categoria ");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception e) {
            response.setMetadata(NOK, "-1", ERROR_CONSULTA);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryReponseRest> delete(Long id) {
        CategoryReponseRest response = new CategoryReponseRest();

        try {
            if (categoryDao.findById(id).isPresent()) {
                categoryDao.deleteById(id);
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
}
